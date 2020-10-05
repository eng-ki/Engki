<template>
  <div>
    <div class="quiz-img">
      <img :src="quiz.url" @click="soundAndTranslation(word)" />
      <div class="quiz-text">{{ word }}</div>
    </div>
  </div>
</template>
<script>
import http from '../utils/http-common.js'
export default {
  props: ['isDone', 'answer'],
  data: () => {
    return {
      quizapipath: '',
      quiz: null,
      selectedIndex: -1,
      showKorean: false,
      word: '',
    }
  },
  created() {
    this.quizapipath =
      '/edu/' + this.$store.state.quiz + '/with/' + this.$store.state.kid.id
    console.log('퀴즈A패스 : ' + this.quizapipath)
    console.log('토큰 : ' + this.$store.state.token)
    http
      .get(this.quizapipath, {
        headers: { 'X-AUTH-TOKEN': this.$store.state.token },
      })
      .then((data) => {
        this.$store.commit('setQuiz', {
          id: data.data.id,
          word: data.data.word,
        })
        console.log('http://j3a510.p.ssafy.io/images/' + data.data.filePath)
        this.quiz = {
          url: 'http://j3a510.p.ssafy.io/images/' + data.data.filePath,
          word_eng: data.data.word,
          word_kor: data.data.wordKor,
        }
        this.word = this.quiz.word_eng
        this.answer = this.quiz.word_eng
      })
    setVoiceList()
  },
  watch: {
    isDone: function (val) {
      this.$store.commit('setExp', 1)
      this.$emit('correct')
    },
    showKorean: function (val) {
      if (this.showKorean) {
        this.word = this.quiz.word_kor
      } else {
        this.word = this.quiz.word_eng
      }
    },
  },
  methods: {
    soundAndTranslation(word) {
      setVoiceList()
      this.showKorean = true
      speech(word)
      setTimeout(() => {
        this.showKorean = false
      }, 1000)
    },
  },
}

var voices = []
function setVoiceList() {
  voices = window.speechSynthesis.getVoices()
}
// 왜 여기서 실행하면 어쩔땐 되고 어쩔땐 안되는거지
// setVoiceList()
if (window.speechSynthesis.onvoiceschanged !== undefined) {
  window.speechSynthesis.onvoiceschanged = setVoiceList
  // alert(setVoiceList)
}
function speech(txt) {
  // https://stackoverflow.com/questions/49506716/speechsynthesis-getvoices-returns-empty-array-on-windows
  // window.speechSynthesis.onvoiceschanged 불러오는데 시간이 좀 걸리는데 이걸 미리 불러올 수 있을까

  if (!window.speechSynthesis) {
    alert(
      '음성 재생을 지원하지 않는 브라우저입니다. 크롬, 파이어폭스 등의 최신 브라우저를 이용하세요'
    )
    return
  }
  var lang = 'en-US'
  var utterThis = new SpeechSynthesisUtterance(txt)
  utterThis.onend = function (event) {
    console.log('end')
  }
  utterThis.onerror = function (event) {
    console.log('error', event)
  }
  var voiceFound = false
  for (var i = 0; i < voices.length; i++) {
    if (
      voices[i].lang.indexOf(lang) >= 0 ||
      voices[i].lang.indexOf(lang.replace('-', '_')) >= 0
    ) {
      utterThis.voice = voices[i]
      voiceFound = true
    }
  }
  if (!voiceFound) {
    alert('voice not found')
    return
  }
  utterThis.lang = lang
  utterThis.pitch = 1
  utterThis.rate = 0.7 //속도
  window.speechSynthesis.speak(utterThis)
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
  width: 45vh;
  font-size: 10vh;
}
</style>
