<template>
  <div>
    <!-- 사진 영역 -->
    <div class="quiz-img">
      <img :src="quiz.url" />
    </div>
    <!-- 사진 영역 끝-->
    <!-- 답변 영역 -->
    <div class="quiz-text">
      <div class="quiz-answer">
        <button
          v-for="(answer, index) in quiz.answers"
          v-bind:key="index"
          class="quiz-button"
          :class="{ selected: selectedIndex == index }"
          @click="select(index)"
        >
          {{ answer }}
        </button>
      </div>
    </div>
    <!-- 답변 영역 끝 -->
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
      url: '/img/etc/puppy2.jpg',
      sentence: 'A dog is running',
      answers: [
        'A dog is running',
        'I always eat cookie',
        'There is a sky',
        'Cat is really cute',
      ],
    }
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) this.$emit('correct')
      else this.$emit('wrong')
    },
  },
  methods: {
    isCorrect() {
      if (this.quiz.answers[this.selectedIndex] == this.quiz.sentence)
        return true
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
  position: absolute;
  top: 30%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 26%;
  border-radius: 5vw;
  border: 1vw solid #d5d5d5;
}

.quiz-answer {
  top: 80%;
  left: 50%;
  transform: translate(-50%, -50%);
  position: absolute;
  width: 70vw;
}

.quiz-button {
  /* 사이즈 설정 */
  width: 28vw;
  height: 8vh;
  margin: 1vw;

  /* 디자인 */
  border-radius: 15vw;
  background-color: #dfdfdf;

  /* 폰트 */
  font-size: 3vh;
  text-align: center;
  color: black;
}

.selected {
  border: 0.7vw solid #f4b759;
}
</style>
