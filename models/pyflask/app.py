from flask import Flask, request
import emotion.Emotion_Recognition as Emotion_Recognition
import cv2
import numpy
import json
import os
# from yolact2 import yolact2.eval
import yolact2.eval as segmetation
from werkzeug import secure_filename  # 파일 고유이름 확인

app = Flask(__name__)


@app.route('/')
def index():
    text = 'Welcome to EngKi AI-MODEL-SERVER'
    return text


@app.route('/emotion', methods=['POST'])
def emotion():

    er = Emotion_Recognition

    filestr = request.files['files'].read()
    # convert string data to numpy array
    npimg = numpy.fromstring(filestr, numpy.uint8)
    # convert numpy array to image
    img = cv2.imdecode(npimg, cv2.IMREAD_COLOR)

    emotions = er.emotion_recognition(img)
    return emotions


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


if __name__ == '__main__':
    app.run(debug=True)
