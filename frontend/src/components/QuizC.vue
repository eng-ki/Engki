<template>
  <div>
    <div class="left">
      <div class="quiz-img">
        <img :src="url" id="test" />
      </div>
    </div>
    <div class="right">
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
</template>
<script>
import http from "../utils/http-common.js";
export default {
  props: {
    isDone: false,
  },
  data: () => {
    return {
      quiz: null,
      selectedIndex: -1,
      showKorean: false,
      url: "/img/etc/twoanimals1.png",
    };
  },
  created() {
    this.quizapipath = "/edu/" + this.$store.state.quiz.id + "/seg";
    console.log("퀴즈3패스 : " + this.quizapipath);
    http
      .get(this.quizapipath, {
        headers: { "X-AUTH-TOKEN": this.$store.state.token },
      })
      .then((data) => {
        this.datas = data.data;
        console.log(data);
        this.quiz = {
          url1: "http://j3a510.p.ssafy.io/images/" + data.data.filePath,
          url2: "http://j3a510.p.ssafy.io/images/" + data.data.segFilePath,
          word: this.$store.state.quiz.word,
          words: [this.$store.state.quiz.word, "random"],
        };
        this.url = this.quiz.url1;
      });
  },
  mounted() {
    this.url = this.quiz.url2;
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) this.$emit("correct");
      else this.$emit("wrong");
    },
    url: function (val) {
      if (this.url == this.quiz.url1) {
        setTimeout(() => {
          this.url = this.quiz.url2;
        }, 500);
      } else if (this.url == this.quiz.url2) {
        setTimeout(() => {
          this.url = this.quiz.url1;
        }, 500);
      }
    },
  },
  methods: {
    isCorrect() {
      // 다했어요 버튼이 클릭됐을때 호출되는 함수, 정답이 맞으면 true, 틀리면 false 리턴
      if (this.quiz.words[this.selectedIndex] == this.quiz.word) return true;
      else {
        return false;
      }
    },
    select(index) {
      this.selectedIndex = index;
    },
  },
};
</script>

<style lang="scss" scoped>
.left {
  position: absolute;
  top: 30vh;
  left: 25vw;
  width: 60vw;
  transform: translate(-50%, -50%);
  img {
    position: relative;
    width: 55vh;
    border-radius: 5vw;
    border: 1vw solid #d5d5d5;
  }
}
.right {
  position: absolute;
  top: 30vh;
  left: 60vw;
  width: 30vw;
  transform: translate(-50%, -50%);
  .quiz-button {
    display: block;
    /* 사이즈 설정 */
    width: 20vw;
    height: 14vh;
    margin: 5vh;
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
}
</style>
