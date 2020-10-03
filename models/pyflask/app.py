from flask import Flask, request
from emotion import Emotion_Recognition
import cv2
import numpy

from image_captioning import predict
from image_captioning.build_vocab import Vocabulary
from image_captioning import *

import os
import yolact2.eval as segmetation
from werkzeug.utils import secure_filename  # 파일 고유이름 확인
from datetime import datetime
from sqlalchemy import create_engine, text

app = Flask(__name__)
app.config.from_pyfile('config.py')

database = create_engine(app.config['DB_URL'], encoding='utf-8')
app.database = database


@app.route('/')
def index():
    text = 'Welcome to EngKi AI-MODEL-SERVER'
    return text


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
    for word, boundary in seg_word, boundaries:
        data['word'] = word
        data['boundary'] = boundary
        app.database.execute(text("""
                                            INSERT INTO custom_image_caption (image_id, word, boundary)
                                            VALUES (:image_id, :word, :boundary)
                                            """), data)

    return 'success'


@app.route('/emotion', methods=['POST'])
def emotion():

    er_model = Emotion_Recognition

    face_image = request.files['files'].read()

    # convert string data to numpy array
    npimg = numpy.fromstring(face_image, numpy.uint8)
    # convert numpy array to image
    img = cv2.imdecode(npimg, cv2.IMREAD_COLOR)

    return er_model.emotion_recognition(img)


def captions(image_path):

    sentence, words = predict.main(image_path)
    # value = {
    #     'sentence': sentence,
    #     'words': words
    # }
    # return sentence and words
    return sentence, words


def seg(filestr, parents_id):

    uploads_dir = "custom/" + parents_id + "/inputs"
    if not os.path.exists(uploads_dir):
        os.makedirs(uploads_dir)

    filename = filestr.filename[:-4] + '_'+datetime.today().strftime("%m%d%H%M%S") + '.jpg'
    filestr.save(os.path.join(uploads_dir, secure_filename(filename)))
    in_path = uploads_dir+"/" + filename

    # input: ~~~~/userid/input/~~~.jpg or png
    # output: save_path+"/output/"+root_list[-1][:-4]+"_"+_class+".png"
    out_path, word = segmetation.custom_segmentation(in_path)
    # value = {
    #     'out_path': out_path,
    #     'word': word,
    # }
    return in_path, out_path, word


if __name__ == '__main__':
    app.run(
        host="70.12.130.106",
        debug=True
    )
