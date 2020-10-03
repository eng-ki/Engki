from flask import Flask, request
from emotion import Emotion_Recognition
import cv2
import numpy
from io import StringIO
from image_captioning import predict
from image_captioning.build_vocab import Vocabulary
from image_captioning import *
import json
import os
# from yolact2 import yolact2.eval
import yolact2.eval as segmetation
from werkzeug import secure_filename  # 파일 고유이름 확인
from flask import Flask, jsonify, request
from sqlalchemy import create_engine, text

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

    # er_model = Emotion_Recognition

    # encode_str = request.files['files'].read()
    # # convert string data to numpy array
    # face_image = StringIO(encode_str)

    # npimg = numpy.fromstring(face_image, numpy.uint8)
    # # convert numpy array to image
    # img = cv2.imdecode(npimg, cv2.IMREAD_COLOR)

    # return er_model.emotion_recognition(img)

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


@app.route('/caption', methods=['GET', 'POST'])
def caption():

    image_path = request.form['image_path']
    # image_path = '/home/team1/engki/pyflask/image_captioning/png/train5.jpg'
    sentence, words = predict.main(image_path)
    value = {
        'sentence': sentence,
        'words': words
    }
    # return sentence and words
    return value


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


@app.route('/seg', methods=['POST'])
def seg():
    filestr = request.files['files'].read()
    parents_id = request.files['parents_id'].read()
    uploads_dir = "/home/ubuntu/engki/data/custom/" + parents_id + "/inputs"
    if not os.path.exists(uploads_dir):
        os.makedirs(uploads_dir)
    filestr.save(os.path.join(uploads_dir, secure_filename(filestr.filename)))
    in_path = uploads_dir+"/" + filestr.filename
    # input: ~~~~/userid/input/~~~.jpg or png
    # output: save_path+"/output/"+root_list[-1][:-4]+"_"+_class+".png"
    out_path, word = segmetation.custom_segmentation(in_path)
    value = {
        'out_path': out_path,
        'word': word,
    }
    return value


@app.route('/custom', methods=['POST'])
def custom():
    filestr = request.files['files']
    parents_id = request.form['parents_id']
    in_path, out_path, seg_word = seg(filestr, parents_id)
    sentence, caption_word = captions(in_path)
    value = {
        'file_path': in_path,
        'boundary': out_path,
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


if __name__ == '__main__':
    app.run(
        # host="70.12.130.106",
        port=5000,
        debug=True
    )
