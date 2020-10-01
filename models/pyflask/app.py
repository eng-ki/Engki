from flask import Flask, request
import emotion.Emotion_Recognition
import cv2
import numpy
import json
# from yolact2 import yolact2.eval
import yolact2.eval as segmetation

app = Flask(__name__)


# @app.route('/')
# def index():
#     text = 'Welcome to EngKi AI-MODEL-SERVER'
#     return text


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
    in_path = request.form['in_path']
    order = request.form['order']
    out_path, word = segmetation.custom_segmentation(in_path, order)
    value = {
        'out_path': out_path,
        'word': word,
    }
    return value


if __name__ == '__main__':
    app.run(debug=True)
