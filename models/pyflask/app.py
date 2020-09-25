from flask import Flask, request
import Emotion_Recognition
import cv2
import numpy
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


if __name__ == '__main__':
    app.run(debug=True)
