<template>
  <div>
    <!-- 문장 영역 -->
    <div class="quiz-sentence" @click="soundAndTranslation(quiz.sentence)">
      <img src="../../public/img/icon/speaker.png" />
      <span>{{ quiz.sentence_kr }}</span>
    </div>
    <!-- 문장 영역 끝 -->
    <!-- 답변 영역 -->
    <div class="quiz-answer">
      <!-- 사진 영역 -->
      <img :src="quiz.url" />
      <!-- 사진 영역 끝-->

      <!-- 같은 단어 두개 나왔을 때 순서 달라도 맞게 처리해야함 -->
      <div class="quiz-lines">
        <draggable v-model="answers" :options="{ group: 'answers' }">
          <div
            class="quiz-button"
            v-for="(answer, index) in answers"
            v-bind:key="index"
            @click="returnAnswer(answer)"
          >
            {{ answer.token }}
          </div>
        </draggable>
        <div class="quiz-line"></div>
        <div class="quiz-line"></div>
      </div>
    </div>
    <!-- 답변 영역 끝 -->
    <!-- 버튼 영역 -->
    <div class="quiz-buttons">
      <div
        class="quiz-button"
        v-for="(answer, index) in quiz.answers"
        v-bind:key="index"
        @click="goAnswer(answer)"
        :class="{ blank: !answer.token }"
      >
        <span v-if="answer.token">{{ answer.token }}</span>
      </div>
    </div>
    <!-- 버튼 영역 끝 -->
  </div>
</template>
<script>
import draggable from 'vuedraggable'
import http from '../utils/http-common.js'
export default {
  props: {
    isDone: false,
  },
  components: { draggable },
  data: () => {
    return {
      quiz: null,
      selectedIndex: -1,
      answers: [],
      answer: [],
    }
  },
  created() {
    this.quiz = {
      url:
        'http://j3a510.p.ssafy.io/images/' +
        this.$store.state.quiz_adv.filePath,
      sentence: this.$store.state.quiz_adv.caption,
      sentence_kr: this.$store.state.quiz_adv.captionKor,
      answers: this.$store.state.quiz_adv.tokens,
    }
    for (var i = 0; i < this.quiz.answers.length; i++) {
      this.answer[this.quiz.answers[i].order] = this.quiz.answers[i].token
    }
    this.answer = this.answer.join(' ')

    // console.log(this.$store.state.quiz_adv.tokens);
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) {
        this.$store.commit('setExp', 6)
        this.$emit('correct')
      } else this.$emit('wrong')
    },
  },
  methods: {
    isCorrect() {
      var compare = []
      for (var i = 0; i < this.answers.length; i++) {
        compare.splice(i, 0, this.answers[i].token)
      }
      compare = compare.join(' ')
      // console.log(compare);
      // console.log(this.answer);

      if (compare == this.answer) return true
      else {
        this.$swal({
          title:
            '<div><span style="font-weight:100; font-size:2vw;">정답이 아닙니다.</span><br><span  style="font-weight:100; font-size:2vw;">다시 한번 생각해보세요.</span></div>',
          showCancelButton: false,
          confirmButtonText: '확인',
          timer: 1000,
        }).then((result) => {
          return true
        })
        return false
      }
    },
    soundAndTranslation(sentence) {
      speech(sentence)
    },
    goAnswer(answer) {
      if (answer[0] != false) {
        // 퀴즈 리스트에서 없애기
        const idx = this.quiz.answers.indexOf(answer)
        if (idx > -1) this.quiz.answers.splice(idx, 1)
        this.quiz.answers.splice(idx, 0, [false, answer])
        // 답변 리스트에 추가하기
        this.answers.push(answer)
      }
    },
    returnAnswer(answer) {
      const idx = this.answers.indexOf(answer)
      if (idx > -1) this.answers.splice(idx, 1)
      for (var i = 0; i < this.quiz.answers.length; i++) {
        if (this.quiz.answers[i][1] == answer) {
          this.quiz.answers.splice(i, 1)
          this.quiz.answers.splice(i, 0, answer)
        }
      }
    },
  },
}

var voices = []
function setVoiceList() {
  voices = window.speechSynthesis.getVoices()
}
setVoiceList()
if (window.speechSynthesis.onvoiceschanged !== undefined) {
  window.speechSynthesis.onvoiceschanged = setVoiceList
}
function speech(txt) {
  if (!window.speechSynthesis) {
    alert(
      '음성 재생을 지원하지 않는 브라우저입니다. 크롬, 파이어폭스 등의 최신 브라우저를 이용하세요'
    )
    return
  }
  var lang = 'en-US'
  var utterThis = new SpeechSynthesisUtterance(txt)
  utterThis.onend = function (event) {
    // console.log('end');
  }
  utterThis.onerror = function (event) {
    // console.log('error', event);
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
  utterThis.rate = 0.6 //속도
  window.speechSynthesis.speak(utterThis)
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.quiz-sentence {
  position: absolute;
  top: 7%;
  left: 50%;
  margin-left: 10vw;
  transform: translate(-50%, -50%);
  font-size: 2.8vw;
  width: 100%;
  display: inline-block;
  &:hover {
    opacity: 0.8;
  }
}
.quiz-sentence img {
  width: 4vw;
  float: left;
  margin-right: 2vw;
}
.quiz-sentence span {
  float: left;
  color: #393939;
}

.quiz-answer {
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80vw;
  display: inline-block;
  padding: 0 3vw 0 10vw;
}

.quiz-answer img {
  width: 13vw;
  border-radius: 5vw;
  border: 0.7vw solid #d5d5d5;
  float: left;
  margin-right: 3vw;
}

.quiz-answer .quiz-lines {
  width: 40vw;
  float: left;
  margin-top: 3vh;
}

.quiz-line {
  margin-top: 9vh;
  border-bottom: 2px solid gray;
}
.quiz-buttons {
  position: absolute;
  top: 77%;
  left: 48%;
  transform: translate(-50%, -50%);
  width: 75vw;
  display: inline-block;
  padding: 0 3vw 0 10vw;
}
.quiz-button {
  /* 사이즈 설정 */
  width: 7vw;
  height: 7vh;
  margin: 0.5vw;

  /* 디자인 */
  border-radius: 7vw;
  background-color: white;
  /* 폰트 */
  font-size: 3vh;
  color: black;
  float: left;
  vertical-align: middle;
  text-align: center;
  padding-top: 1.5vh;
  &:hover {
    opacity: 0.8;
  }
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
}

.blank {
  background-color: #f2f2f2;
}
</style>
