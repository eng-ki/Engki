<template>
  <div>
    <!-- 질문 영역 -->
    <div class="quiz-question">
      <span v-for="(word, index) in quiz.sentence" v-bind:key="index">
        <!-- 질문 빈칸 영역 -->
        <span
          class="quiz-blank"
          v-if="
            word == quiz.word ||
            word == quiz.word + 's' ||
            word == quiz.word + 'es'
          "
        >
          <span
            ><span v-for="i in word.length" v-bind:key="i" v-html="blank"></span
          ></span>
        </span>
        <span v-else>&nbsp;{{ word }}&nbsp;</span>
        <!-- 질문 빈칸 영역 끝 -->
      </span>
    </div>
    <!-- 질문 영역 끝 -->
    <!-- 왼쪽 영역 -->
    <div class="quiz-img">
      <img :src="quiz.url" />
    </div>
    <!-- 왼쪽 영역 끝-->
    <!-- 오른쪽 영역 -->
    <div class="quiz-text">
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
import http from '../utils/http-common.js';
export default {
  props: {
    isDone: false,
  },
  data: () => {
    return {
      quiz: {
        url: '',
        sentence: '',
        word: '',
        words: '',
      },
      selectedIndex: -1,
      blank: '<span>&nbsp;&nbsp;</span>',
    };
  },
  created() {
    this.quizapipath = '/edu/' + this.$store.state.quiz.id + '/captions';
    // console.log('퀴즈4패스 : ' + this.quizapipath);
    http
      .get(this.quizapipath, {
        headers: { 'X-AUTH-TOKEN': this.$store.state.token },
      })
      .then((data) => {
        // console.log(data.data);
        this.$store.commit('setQuizAdv', {
          caption: data.data.caption,
          captionKor: data.data.captionKor,
          filePath: data.data.filePath,
          randomCaptions: data.data.randomCaptions,
          tokens: data.data.tokens,
        });

        const idx = data.data.randomWords.indexOf(this.$store.state.quiz.word);
        data.data.randomWords.splice(idx, 1);

        this.quiz = {
          url:
            'http://j3a510.p.ssafy.io/images/' +
            this.$store.state.quiz_adv.filePath,
          sentence: this.$store.state.quiz_adv.caption,
          word: this.$store.state.quiz.word,
          words: [
            this.$store.state.quiz.word,
            data.data.randomWords[0],
            data.data.randomWords[1],
            data.data.randomWords[2],
          ],
        };
        this.quiz.sentence = this.quiz.sentence.split(' ');
      });
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) {
        this.$store.commit('setExp', 4);
        this.$emit('correct');
      } else this.$emit('wrong');
    },
  },
  methods: {
    isCorrect() {
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
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.quiz-img img {
  position: absolute;
  top: 8.5vh;
  left: 2vw;
  float: left;
  margin-top: 3vh;
  width: 50vh;
  border-radius: 2vw;
  border: 1.5vh solid #d5d5d5;
}

.quiz-text {
  padding-left: 3vw;
  float: left;
  // background-color: black;
  height: 100%;
  width: 57%;
  color: #393939;
}
.quiz-question {
  position: absolute;
  top: 0px;
  right: 0px;
  width: 100%;
  // margin-top: 3vh;
  // margin-left: 1vw;
  // height: 15vh;
  // text-align: left;
  // line-height: 10vh;
  word-break: break-all;
  display: inline-block;
  span {
    font-size: 2.5vw;
  }
}

.quiz-blank {
  width: 13vw;
  height: 8vh;
  border-bottom: 0.3vw solid #393939;
  color: #393939;
}
.quiz-answer {
  position: absolute;
  top: 10vh;
  right: 0px;

  // margin-top: 2vh;
}

.quiz-button {
  /* 사이즈 설정 */
  width: 30vw;
  height: 10vh;
  /* 디자인 */
  border-radius: 15vw;
  margin: 1vw;
  background-color: #dfdfdf;

  display: block;
  /* 폰트 */
  line-height: 8vh;
  font-size: 5vh;
  font-display: inherit;
  color: black;
}

.selected {
  border: 1vw solid #f4b759;
}
</style>
