from emotion import Emotion_Recognition
import cv2
import numpy

from image_captioning import predict
from image_captioning.build_vocab import Vocabulary
from image_captioning import *

import json
import os
import yolact.eval as segmetation
from werkzeug.utils import secure_filename  # 파일 고유이름 확인
from flask import Flask, jsonify, request
from sqlalchemy import create_engine, text
from datetime import datetime

app = Flask(__name__)
app.config.from_pyfile('config.py')

database = create_engine(app.config['DB_URL'], encoding='utf-8')
app.database = database


@app.route('/')
def index():
    text = 'Welcome to EngKi AI-MODEL-SERVER'
    return text


@app.route('/emotion', methods=['POST'])
def emotion():

    er_model = Emotion_Recognition

    filestr = request.files['files'].read()

    kid_id = request.form['kid_id']
    # convert string data to numpy array
    npimg = numpy.fromstring(filestr, numpy.uint8)
    # convert numpy array to image
    img = cv2.imdecode(npimg, cv2.IMREAD_COLOR)

    emotion_dict = er_model.emotion_recognition(img)

    emotion_data = {
        'kid_id': kid_id,
        'angry': emotion_dict['Angry'],
        'disgusting': emotion_dict['Disgusting'],
        'fearful': emotion_dict['Fearful'],
        'happy': emotion_dict['Happy'],
        'sad': emotion_dict['Sad'],
        'surprising': emotion_dict['Surpring'],
        'neutral': emotion_dict['Neutral'],
    }

    emotion_id = app.database.execute(text("""
                                            INSERT INTO kid_emotion (
                                            kid_id,angry,disgusting,fearful,happy,sad,surprising,neutral
                                           ) VALUES (
                                            :kid_id,:angry,:disgusting,:fearful,:happy,:sad,:surprising,:neutral)
                                            """), emotion_data).lastrowid
    # 아이의 최근 5분간의 상태 조회하기
    last_emotions = app.database.execute(text("""
                                                SELECT * FROM kid_emotion 
                                                WHERE evaluate_time BETWEEN(SELECT DATE_ADD(NOW(), INTERVAL -5 MINUTE)) AND NOW() 
                                                AND kid_id = :kid_id"""), emotion_data)
    study_continue = emotion_cal_minute(last_emotions)
    if (study_continue):
        return "GO"
    else:
        return "STOP"


def emotion_cal_minute(emotions):
    good = 0
    bad = 0
    for moment in emotions:
        happy = moment['happy']
        sad = moment['sad'] + moment['angry'] + moment['disgusting']
        if (happy < sad):
            bad = bad + 1
        else:
            good = good + 1
    if (good >= bad):
        # print("gogo")
        return True
    else:
        # print("stop")
        return False


@app.route('/week_emotion', methods=['POST'])
def week_emotion():
    kid_id = request.form['kid_id']
    data = {
        'kid_id': kid_id
    }
    week_emotions = app.database.execute(text("""
                                                SELECT * FROM kid_emotion 
                                                WHERE evaluate_time BETWEEN(SELECT DATE_ADD(NOW(), INTERVAL -7 DAY)) AND NOW() 
                                                AND kid_id = :kid_id"""), data)


@app.route('/custom', methods=['POST'])
def custom():
    filestr = request.files['files']
    parent_id = request.form['parent_id']
    in_path, out_path, seg_word = seg(filestr, parent_id)
    sentence, caption_word = captions(in_path)
    value = {
        'file_path': in_path,
        'boundaries': out_path,
        'caption': sentence,
        'seg_word': seg_word,
        'caption_word': caption_word
    }
    return value


@app.route('/custom/save', methods=['POST'])
def custom_save():
    data = request.json
    boundaries = data['boundaries']
    seg_word = data['seg_word']
    caption_word = data['caption_word']

    try:
        image_id = app.database.execute(text("""
                                            INSERT INTO custom_image (file_path, parent_id)
                                            VALUES (:file_path,:parent_id)
                                            """), data).lastrowid

        data['image_id'] = image_id
        for word in caption_word:
            data['word'] = word
            app.database.execute(text("""
                                        INSERT INTO custom_image_caption (image_id, word, caption)
                                        VALUES (:image_id, :word, :caption)
                                        """), data)
        for i in range(0, len(seg_word), 1):
            data['word'] = seg_word[i]
            data['boundary'] = boundaries[i]
            app.database.execute(text("""
                                                INSERT INTO custom_image_word (image_id, word, boundary)
                                                VALUES (:image_id, :word, :boundary)
                                                """), data)

        return 'success'
    except:
        return 'fail'


def captions(image_path):
    sentence, words = predict.main(image_path)

    return sentence, words


def seg(filestr, parents_id):
    uploads_dir = "custom/" + parents_id + "/inputs"
    if not os.path.exists(uploads_dir):
        os.makedirs(uploads_dir)

    filename = filestr.filename[:-4] + '_' + \
        datetime.today().strftime("%m%d%H%M%S") + '.jpg'
    filestr.save(os.path.join(uploads_dir, secure_filename(filename)))
    in_path = uploads_dir + "/" + filename

    out_path, word = segmetation.custom_segmentation(in_path)

    return in_path, out_path, word


if __name__ == '__main__':
    app.run(
        # host="0.0.0.0",
        debug=True
    )
