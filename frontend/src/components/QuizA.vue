<template>
  <div>
    <div class="quiz-img">
      <img :src="quiz.url" @click="soundAndTranslation(word)" />
      <div class="quiz-text">{{ word }}</div>
    </div>
  </div>
</template>
<script>
export default {
  props: ['isDone','answer'],
  data: () => {
    return {
      quiz: null,
      selectedIndex: -1,
      showKorean: false,
      word: '',
    }
  },
  created() {
    //api 호출
    this.quiz = {
      url: '/img/etc/puppy.jpg',
      word_eng: 'dog',
      word_kor: '강아지',
    }
    this.word = this.quiz.word_eng
    this.answer = this.quiz.word_eng
    setVoiceList()
  },
  watch: {
    answer:function(val){
      this.$emit('set-answer',this.answer)
    },
    isDone: function (val) {
      this.$emit('correct')
      // if (this.isCorrect()) this.$emit('correct')
      // else this.$emit('wrong')
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
    // isCorrect() {
    //   // 다했어요 버튼이 클릭됐을때 호출되는 함수, 정답이 맞으면 true, 틀리면 false 리턴
    //   return true
    // },
    
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
  utterThis.rate = 1 //속도
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
}
.quiz-img .quiz-text {
  position: relative;
  margin-top: 2vh;
  width: 45vh;
  font-size: 10vh;
}
</style>
