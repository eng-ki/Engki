from flask import Flask, request
from emotion import Emotion_Recognition
import cv2
import numpy
from io import StringIO
from image_captioning import predict
from image_captioning.build_vocab import Vocabulary
from image_captioning import *
import json
# from yolact2 import yolact2.eval
import yolact2.eval as segmetation

app = Flask(__name__)


@app.route('/')
def index():
    text = 'Welcome to EngKi AI-MODEL-SERVER'
    return text


@app.route('/emotion', methods=['POST'])
def emotion():

    er_model = Emotion_Recognition

    encode_str = request.files['files'].read()
    # convert string data to numpy array
    face_image = StringIO(encode_str)

    npimg = numpy.fromstring(face_image, numpy.uint8)
    # convert numpy array to image
    img = cv2.imdecode(npimg, cv2.IMREAD_COLOR)

    return er_model.emotion_recognition(img)


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


@app.route('/seg', methods=['POST'])
def seg():
    in_path = request.form['in_path']
    # input: ~~~~/userid/input/~~~.jpg or png
    # output: save_path+"/output/"+root_list[-1][:-4]+"_"+_class+".png"
    out_path, word = segmetation.custom_segmentation(in_path)
    value = {
        'out_path': out_path,
        'word': word,
    }
    return value


if __name__ == '__main__':
    app.run(
        host="70.12.130.106",
        port=5000,
        debug=True
    )


