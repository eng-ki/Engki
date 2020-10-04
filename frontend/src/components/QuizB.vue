<template>
  <div class="quiz-board">
    <div class="row" v-for="j in 2" :key="j">
      <div
        class="col-4 imagebox"
        v-for="(data, i) in datas"
        v-if="parseInt(i / 3) == j - 1"
      >
        <img
          :src="'http://j3a510.p.ssafy.io/images/' + data.filePath"
          class="img"
          :class="{ selected: selects[i].selected }"
          @click="select(i)"
        />
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-6 quiz-text">
        {{ this.$store.state.quiz.word }}
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>
<script>
import http from '../utils/http-common.js'
export default {
  props: ['isDone'],
  created() {
    this.isDone = false
    this.quizapipath = '/edu/' + this.$store.state.quiz.id + '/images'
    console.log('퀴즈2패스 : ' + this.quizapipath)
    http
      .get(this.quizapipath, {
        headers: { 'X-AUTH-TOKEN': this.$store.state.token },
      })
      .then((data) => {
        this.datas = data.data
        console.log(this.datas)
      })
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) this.$emit('correct')
      else this.$emit('wrong')
    },
  },
  methods: {
    isCorrect() {
      var count = 0
      for (var data in this.datas) {
        // console.log(this.datas[data].word)
        if (this.datas[data].selected) {
          count++
          if (this.datas[data].word != this.$store.state.quiz.word) {
            return false
          }
        }
      }
      if (count != 3) {
        // isDone 한번 누르면 두번 누른것처럼 인식되는데 왤까요 정답이 이제 3개가 아닙니다 이미지 개수가 모자란게 있어서
        alert('3개를 선택해주세요')
        return false
      }
      return true
    },
    select(index) {
      this.datas[index].selected = !this.datas[index].selected
      this.selects[index].selected = !this.selects[index].selected
      // alert(this.datas[index].selected);
      console.log(this.datas)
    },
  },
  data: function () {
    return {
      selectedIndex: -1,
      datas: [],
      selects: [
        {
          selected: false,
        },
        {
          selected: false,
        },
        {
          selected: false,
        },
        {
          selected: false,
        },
        {
          selected: false,
        },
        {
          selected: false,
        },
      ],
    }
  },
}
</script>
<style lang="scss" scoped>
.quiz-board {
  margin-top: -3vh;
  // position:absolute;
  // top:7vh;
  // left:20vw;
  // transform: translate(0, -50%);
}
.quiz-text {
  margin-top: -3vh;
  font-size: 10vh;
}
.imagebox {
  padding: 0vw;
}
.img {
  // display:inherit;
  width: 13vw;
  height: 13vw;
  border-radius: 5vw;
  border: 1vw solid transparent;
  // background-color:yellow;
}
.selected {
  border: 0.5vw solid #f4b759;
}
</style>
