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

import sys
import urllib.request
import flask_cors CORS, cross_origin


app = Flask(__name__)
app.config.from_pyfile('config.py')
CORS(app)

database = create_engine(app.config['DB_URL'], encoding='utf-8')
app.database = database

@app.route('/')
def index():
    text = 'Welcome to EngKi AI-MODEL-SERVER'
    return text


@app.route('/custom/emotion', methods=['POST'])
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
        return "GO", 200
    else:
        return "STOP", 204


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


@app.route('/custom/quiz/make', methods=['POST'])
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

    caption_kor = get_sentence(sentence)
    seg_word_kor = []
    caption_word_kor = []

    for i in range(0, len(seg_word)):
        seg_word_kor.append(get_sentence(seg_word[i]))

    for i in range(0, len(caption_word)):
        caption_word_kor.append(get_sentence(caption_word[i]))

    value['caption_kor'] = caption_kor
    value['seg_word_kor'] = seg_word_kor
    value['caption_word_kor'] = caption_word_kor
    return value, 404


@app.route('/custom/quiz/save', methods=['POST'])
def custom_save():
    data = request.json
    boundaries = data['boundaries']
    seg_word = data['seg_word']
    caption_word = data['caption_word']
    seg_word_kor = data['seg_word_kor']
    caption_word_kor = data['caption_word_kor']
    try:
        image_id = app.database.execute(text("""
                                            INSERT INTO custom_image (file_path, parent_id)
                                            VALUES (:file_path,:parent_id)
                                            """), data).lastrowid

        data['image_id'] = image_id
        for i in range(0, len(caption_word), 1):
            data['word'] = caption_word[i]
            data['word_kor'] = caption_word_kor[i]
            app.database.execute(text("""
                                        INSERT INTO custom_image_caption (image_id, word, word_kor, caption, caption_kor)
                                        VALUES (:image_id, :word, :word_kor, :caption, :caption_kor)
                                        """), data)
        for i in range(0, len(seg_word), 1):
            data['word'] = seg_word[i]
            data['word_kor'] = seg_word_kor[i]
            data['boundary'] = boundaries[i]
            app.database.execute(text("""
                                                INSERT INTO custom_image_word (image_id, word, boundary, word_kor)
                                                VALUES (:image_id, :word, :boundary, :word_kor)
                                                """), data)

        return 'success', 201
    except:
        return 'fail', 409


def get_sentence(caption):
    client_id = "zF8150uzkTAnsjoUn0_b"  # 개발자센터에서 발급받은 Client ID 값
    client_secret = "DDscISyf7u"  # 개발자센터에서 발급받은 Client Secret 값
    encText = urllib.parse.quote(caption)
    data = "source=en&target=ko&text=" + encText
    url = "https://openapi.naver.com/v1/papago/n2mt"
    request = urllib.request.Request(url)
    request.add_header("X-Naver-Client-Id", client_id)
    request.add_header("X-Naver-Client-Secret", client_secret)
    response = urllib.request.urlopen(request, data=data.encode("utf-8"))
    rescode = response.getcode()
    # print(rescode)
    if(rescode == 200):
        response_body = response.read()
        cap = response_body.decode('utf-8')
        c = json.loads(cap)
        kcap = c['message']['result']['translatedText']
        return kcap


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
        host = "70.12.130.106",
        debug=True
    )
