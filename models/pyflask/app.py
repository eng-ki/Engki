from flask import Flask

app = Flask(__name__)


@app.route('/')
def index():
    text = 'Welcome to EngKi AI-MODEL-SERVER'
    return text


@app.route('/emotion', methods=['POST'])
def emotion():
    emotions = 'Happy'
    return emotions


if __name__ == '__main__':
    app.run(debug=True)
