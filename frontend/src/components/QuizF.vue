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

      <div class="quiz-lines">
        <draggable v-model="answers" :options="{ group: 'answers' }">
          <div
            class="quiz-button"
            v-for="(answer, index) in answers"
            v-bind:key="index"
          >
            {{ answer }}
          </div>
        </draggable>
        <div class="quiz-line"></div>
        <div class="quiz-line"></div>
      </div>
    </div>
    <!-- 답변 영역 끝 -->
    <!-- 버튼 영역 -->
    <div class="quiz-buttons">
      <draggable v-model="quiz.answers" :options="{ group: 'answers' }">
        <div
          class="quiz-button"
          v-for="(answer, index) in quiz.answers"
          v-bind:key="index"
        >
          {{ answer }}
        </div>
      </draggable>
    </div>
    <!-- 버튼 영역 끝 -->
  </div>
</template>
<script>
import draggable from "vuedraggable";
export default {
  components: { draggable },
  props: ['isDone','answer'],
  data: () => {
    return {
      quiz: null,
      selectedIndex: -1,
      answers: [],
    };
  },
  created() {
    //api 호출
    this.quiz = {
      url: "/img/etc/puppy2.jpg",
      sentence: "A small dog is running on the grass",
      sentence_kr: "작은 강아지가 풀밭에서 달리고 있습니다",
      answers: ["running", "A", "dog", "is", "on", "the", "grass", "small"],
    };
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) this.$emit("correct");
      else this.$emit("wrong");
    },
  },
  methods: {
    isCorrect() {
      // if (this.answers.join(' ') == this.quiz.sentence) return true
      // else return false
      return true;
    },
    soundAndTranslation(sentence) {
      speech(sentence);
    },
    go(answer) {
      this.quiz.answers.splice(answer, 1);
      this.answers.push(answer);
    },
  },
};

// var voices = []
// function setVoiceList() {
//   voices = window.speechSynthesis.getVoices()
// }
// setVoiceList()
// if (window.speechSynthesis.onvoiceschanged !== undefined) {
//   window.speechSynthesis.onvoiceschanged = setVoiceList
// }
// function speech(txt) {
//   if (!window.speechSynthesis) {
//     alert(
//       '음성 재생을 지원하지 않는 브라우저입니다. 크롬, 파이어폭스 등의 최신 브라우저를 이용하세요'
//     )
//     return
//   }
//   var lang = 'en-US'
//   var utterThis = new SpeechSynthesisUtterance(txt)
//   utterThis.onend = function (event) {
//     console.log('end')
//   }
//   utterThis.onerror = function (event) {
//     console.log('error', event)
//   }
//   var voiceFound = false
//   for (var i = 0; i < voices.length; i++) {
//     if (
//       voices[i].lang.indexOf(lang) >= 0 ||
//       voices[i].lang.indexOf(lang.replace('-', '_')) >= 0
//     ) {
//       utterThis.voice = voices[i]
//       voiceFound = true
//     }
//   }
//   if (!voiceFound) {
//     alert('voice not found')
//     return
//   }
//   utterThis.lang = lang
//   utterThis.pitch = 1
//   utterThis.rate = 1 //속도
//   window.speechSynthesis.speak(utterThis)
// }
</script>
<style lang="scss">
@import "../assets/sass/base.scss";
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
  top: 80%;
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
  background-color: #dfdfdf;

  /* 폰트 */
  font-size: 3vh;
  color: black;
  float: left;
  vertical-align: middle;
  text-align: center;
  padding-top: 1.5vh;
  &:hover {
    opacity: 0.5;
  }
}
</style>
