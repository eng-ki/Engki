<template>
  <div>
    <div class="pointer"><img src="../../public/img/icon/tap.png" /></div>
    <div class="quiz-img">
      <img :src="quiz.url" @click="soundAndTranslation(word)" />
      <div class="quiz-text">{{ word }}</div>
    </div>
  </div>
</template>
<script>
import http from '../utils/http-common.js';
export default {
  props: ['isDone'],
  data: () => {
    return {
      quizapipath: '',
      quiz: {
        url: '',
        word_eng: '',
        word_kor: '',
      },
      selectedIndex: -1,
      showKorean: false,
      word: '',
    };
  },
  created() {
    // imageID,
    if (this.$store.state.test_customizing) {
      this.setLocalVariable();
    } else {
      if (this.$store.state.theme == 1) {
        this.quizapipath = '/custom/by/' + this.$store.state.parent.id;
      } else {
        this.quizapipath =
          '/edu/' +
          this.$store.state.theme +
          '/with/' +
          this.$store.state.kid.id;
      }
      http
        .get(this.quizapipath, {
          headers: { 'X-AUTH-TOKEN': this.$store.state.token },
        })
        .then((data) => {
          // console.log(data);
          this.$store.commit('setQuiz', {
            id: data.data.id,
            word: data.data.word,
            url: 'http://j3a510.p.ssafy.io/images/' + data.data.filePath,
            word_eng: data.data.word,
            word_kor: data.data.wordKor,
          });
          this.setLocalVariable();
        });
    }
    setVoiceList();
  },
  watch: {
    isDone: function (val) {
      this.$store.commit('setExp', 1);
      this.$emit('correct');
    },
    showKorean: function (val) {
      if (this.showKorean) {
        this.word = this.quiz.word_kor;
      } else {
        this.word = this.quiz.word_eng;
      }
    },
  },
  methods: {
    setLocalVariable() {
      // console.log('들어옴2');
      this.quiz = {
        url: this.$store.state.quiz.url,
        word_eng: this.$store.state.quiz.word,
        word_kor: this.$store.state.quiz.word_kor,
      };
      this.word = this.$store.state.quiz.word;
      this.answer = this.$store.state.quiz.word;
    },
    soundAndTranslation(word) {
      setVoiceList();
      this.showKorean = true;
      speech(word);
      setTimeout(() => {
        this.showKorean = false;
      }, 500);
    },
  },
};

var voices = [];
function setVoiceList() {
  voices = window.speechSynthesis.getVoices();
}
if (window.speechSynthesis.onvoiceschanged !== undefined) {
  window.speechSynthesis.onvoiceschanged = setVoiceList;
}
function speech(txt) {
  if (!window.speechSynthesis) {
    // this.$swal({
    //   title:
    //     '<div style="font-weight:100; font-size:2vw;"> ' +
    //     '개를 선택해주세요</div>',
    //   showCancelButton: false,
    //   confirmButtonText: '확인',
    // }).then((result) => {
    //   return;
    // });
    alert(
      '음성 재생을 지원하지 않는 브라우저입니다. 크롬, 파이어폭스 등의 최신 브라우저를 이용하세요'
    );
  }
  var lang = 'en-US';
  var utterThis = new SpeechSynthesisUtterance(txt);
  utterThis.onend = function (event) {
    // console.log('end')
  };
  utterThis.onerror = function (event) {
    // console.log('error', event)
  };
  var voiceFound = false;
  for (var i = 0; i < voices.length; i++) {
    if (
      voices[i].lang.indexOf(lang) >= 0 ||
      voices[i].lang.indexOf(lang.replace('-', '_')) >= 0
    ) {
      utterThis.voice = voices[i];
      voiceFound = true;
    }
  }
  if (!voiceFound) {
    alert('voice not found');
    return;
    // this.$swal({
    //   title:
    //     '<div style="font-weight:100; font-size:2vw;"> ' +
    //     '개를 선택해주세요</div>',
    //   showCancelButton: false,
    //   confirmButtonText: '확인',
    // }).then((result) => {
    //   return;
    // });
  }
  utterThis.lang = lang;
  utterThis.pitch = 1;
  utterThis.rate = 0.7; //속도
  window.speechSynthesis.speak(utterThis);
}
</script>

<style lang="scss" scoped>
.quiz-img {
  position: absolute;
  top: 30vh;
  left: 50%;
  transform: translate(-50%, -50%);
}
.quiz-img img {
  position: relative;
  width: 45vh;
  border-radius: 5vw;
  border: 1vw solid #d5d5d5;
  opacity: 80;
}
.quiz-img .quiz-text {
  position: relative;
  margin-top: 2vh;
  // width: 45vh;
  font-size: 10vh;
}
.pointer {
  position: absolute;
  width: 15%;
  height: 20%;
  bottom: 20%;
  right: 25%;
  z-index: 10;
}
.pointer img {
  width: 70%;
  animation: pointers 1.5s linear 1.5s infinite alternate running;
}

@keyframes pointers {
  from {
    opacity: 1;
  }
  20% {
    opacity: 1;
    transform: translateX(0);
  }
  to {
    opacity: 1;
    transform: translate(-50px, -50px);
  }
}
</style>
