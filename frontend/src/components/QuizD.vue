<template>
  <div>
    <!-- 왼쪽 영역 -->
    <div class="quiz-img">
      <img :src="quiz.url" />
    </div>
    <!-- 왼쪽 영역 끝-->
    <!-- 오른쪽 영역 -->
    <div class="quiz-text">
      <!-- 질문 영역 -->
      <div class="quiz-question">
        <span v-for="(word, index) in quiz.sentence" v-bind:key="index">
          <!-- 질문 빈칸 영역 -->
          <span class="quiz-blank" v-if="word == quiz.word">
            <span v-if="selectedIndex == -1"
              ><span v-for="i in word.length" v-bind:key="i">?</span></span
            >
            <span v-else>{{ quiz.words[selectedIndex] }}</span>
          </span>
          <span v-else>&nbsp;{{ word }}&nbsp;</span>
          <!-- 질문 빈칸 영역 끝 -->
        </span>
      </div>
      <!-- 질문 영역 끝 -->
      <!-- 답변 영역 -->
      <div class="quiz-answer">
        <button
          v-for="(word, index) in quiz.words"
          v-bind:key="index"
          class="quiz-button"
          :class="{ selected: selectedIndex == index }"
          @click="select(index)"
        >
          {{ word }}
        </button>
      </div>
      <!-- 답변 영역 끝 -->
    </div>
    <!-- 오른쪽 영역 끝 -->
  </div>
</template>
<script>
export default {
  props: {
    isDone: false,
  },
  data: () => {
    return {
      quiz: null,
      selectedIndex: -1,
    }
  },
  mounted() {
    //api 호출
    this.quiz = {
      url: '/img/etc/puppy.jpg',
      sentence: 'A dog is running',
      word: 'dog',
      words: ['dog', 'cat', 'bird', 'cow'],
    }
    this.quiz.sentence = this.quiz.sentence.split(' ')
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) this.$emit('correct')
      else this.$emit('wrong')
    },
  },
  methods: {
    isCorrect() {
      if (this.quiz.words[this.selectedIndex] == this.quiz.word) return true
      else {
        return false
      }
    },
    select(index) {
      this.selectedIndex = index
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.quiz-img img {
  float: left;
  margin-top: 3vh;
  width: 40%;
  height: 40%;
  border-radius: 8vw;
  border: 1vw solid #d5d5d5;
}

.quiz-text {
  padding-left: 3vw;
  float: left;
  // background-color: black;
  height: 100%;
  width: 57%;
  color: #393939;
}

.quiz-text .quiz-question {
  margin-top: 3vh;
  height: 15vh;
  word-break: break-all;
  display: inline-block;
  font-size: 4vw;
}

.quiz-answer {
  margin-top: 1vh;
}

.quiz-button {
  /* 사이즈 설정 */
  width: 14.5vw;
  height: 14vh;
  margin: 1vw;
  /* 디자인 */
  border-radius: 15vw;
  background-color: #dfdfdf;

  /* 폰트 */
  line-height: 9vh;
  font-size: 6vh;
  font-display: inherit;
  color: black;
}
.quiz-blank {
  width: 13vw;
  height: 8vh;
  border-bottom: 0.5vw solid #393939;
  color: #393939;
}
.selected {
  border: 1vw solid #f4b759;
}
</style>
