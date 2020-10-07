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
import http from '../utils/http-common.js';
export default {
  props: {
    isDone: false,
  },
  data: () => {
    return {
      quiz: {
        url1: '',
        url2: '',
        word: '',
        words: '',
      },
      selectedIndex: -1,
      showKorean: false,
      url: '/img/etc/twoanimals1.png',
    };
  },
  created() {
    if (this.$store.state.is_test) {
      // 부모 테스트 - 커스텀 퀴즈일 때
    } else {
      if (this.$store.state.theme == 1) {
        this.quizapipath =
          '/custom/' +
          this.$store.state.quiz.word +
          '/seg/by/' +
          this.$store.state.parent.id;
      } else {
        this.quizapipath = '/edu/' + this.$store.state.quiz.id + '/seg';
      }
      http
        .get(this.quizapipath, {
          headers: { 'X-AUTH-TOKEN': this.$store.state.token },
        })
        .then((data) => {
          this.$store.commit('setQuizC', data.data);
        });
    }
    this.datas = this.$store.state.quiz_c;
    this.quiz = {
      url1:
        'http://j3a510.p.ssafy.io/images/' + this.$store.state.quiz_c.filePath,
      url2:
        'http://j3a510.p.ssafy.io/images/' +
        this.$store.state.quiz_c.segFilePath,
      word: this.$store.state.quiz.word,
      words: [this.$store.state.quiz.word, this.$store.state.quiz_c.randomWord],
    };
    this.url = this.quiz.url1;
  },
  mounted() {
    this.url = this.quiz.url2;
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) {
        this.$store.commit('setExp', 3);
        this.$emit('correct');
      } else this.$emit('wrong');
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
