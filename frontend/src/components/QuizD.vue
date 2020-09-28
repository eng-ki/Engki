<template>
  <div>
    <div class="quiz-img">
      <img :src="quiz.url" />
    </div>
    <div class="quiz-text">
      <div class="quiz-question">
        <span>{{ quiz.sentence }}</span>
      </div>
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
    </div>
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
      sentence: 'The dog is running',
      word: 'dog',
      words: ['dog', 'cat', 'bird', 'cow'],
    }
    // this.quiz.sentence = this.quiz.sentence.replace(this.quiz.word, '\t\t\t')
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
        alert('WRONG')
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

.selected {
  border: 1vw solid #f4b759;
}
</style>
