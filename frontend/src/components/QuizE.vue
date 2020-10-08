<template>
  <div>
    <!-- 오른쪽 영역 -->
    <div class="right-side">
      <!-- 사진 -->
      <div class="quiz-img">
        <img :src="quiz.url" />
      </div>
      <!-- 사진 끝-->
    </div>
    <!-- 오른쪽 영역 끝-->

    <!-- 왼쪽 영역 -->
    <div class="left-side">
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
    <!--왼쪽 영역 끝-->
  </div>
</template>
<script>
import http from '../utils/http-common.js';
export default {
  props: {
    isDone: false,
  },
  data: () => {
    return {
      quiz: null,
      selectedIndex: -1,
    };
  },
  created() {
    this.quiz = {
      url:
        'https://j3a510.p.ssafy.io/images/' +
        this.$store.state.quiz_adv.filePath,
      sentence: this.$store.state.quiz_adv.caption,
      answers: this.$store.state.quiz_adv.randomCaptions,
    };
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) {
        this.$store.commit('setExp', 5);
        this.$emit('correct');
      } else this.$emit('wrong');
    },
  },
  methods: {
    isCorrect() {
      if (this.quiz.answers[this.selectedIndex] == this.quiz.sentence)
        return true;
      else {
        this.$swal({
          title:
            '<div><span style="font-weight:100; font-size:2vw;">정답이 아닙니다.</span><br><span  style="font-weight:100; font-size:2vw;">다시 한번 생각해보세요.</span></div>',
          showCancelButton: false,
          confirmButtonText: '확인',
          timer: 1000,
        }).then((result) => {
          return true;
        });
        return false;
      }
    },
    select(index) {
      this.selectedIndex = index;
    },
  },
};
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.right-side {
  position: absolute;
  // display: inline-block;
  top: 10vh;
  left: 0px;
  width: 30%;
  height: 100%;
  // background-color: yellow;
  .quiz-img {
    line-height: 100%;
    img {
      // vertical-align: middle;
      display: inline-block;
      position: relative;
      // left: 1vw;
      // top: 3vh;
      // float: left;
      // margin-top: 5vh;
      // margin-top: auto;
      width: 100%;
      border-radius: 5vw;
      border: 1vw solid #d5d5d5;
    }
  }
}
.left-side {
  position: absolute;
  display: inline-block;
  top: 10vh;
  right: 0px;
  width: 70%;
  height: 100%;
  // background-color: red;

  .quiz-answer {
    display: inline-block;
    position: relative;
    // top: 80%;
    // left: 50%;
    // right: 1vw;
    // transform: translate(-50%, -50%);
    // top: 6vh;
    .quiz-button {
      display: block;
      /* 사이즈 설정 */
      width: 100%;
      height: 8vh;
      margin: 1vw;

      /* 디자인 */
      border-radius: 15vw;
      background-color: #dfdfdf;

      /* 폰트 */
      font-size: 3vh;
      font-weight: 100;
      text-align: center;
      color: black;
    }
  }
}

.selected {
  border: 0.7vw solid #f4b759;
}
</style>
