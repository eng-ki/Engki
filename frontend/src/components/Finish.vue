<template>
  <div>
    <div class="chest">
      <b-progress
        :value="startExp"
        max="100"
        height="8vh"
        variant="success"
        show-progress
      ></b-progress>
    </div>
    <div class="header">+ {{ start }} exp</div>
    <div class="gi-buttons">
      <button class="blue-button wide" @click="goMain()">메인으로</button>
      <button class="green-button wide" @click="Again()">한판 더 하기</button>
    </div>
  </div>
</template>
<script>
import http from '../utils/http-common.js'
export default {
  name: 'GetItem',
  data: () => {
    return {
      level: 0, //현재 레벨
      beforeExp: 0, // 이전 경험치
      startExp: 0, //시작 경험치
      endExp: 0, // 끝 경험치
      timer: null, // 막대기 바 올라가는 타이머
      timer2: null, // 경험치 수치 올라가는 타이머
      start: 0,
      exp: 0,
    }
  },
  mounted() {
    // 이전 경험치
    this.beforeExp = this.$store.state.kid.exp
    // 새로 얻은 경험치와 학습 단어 저장
    http
      .post(
        'edu/' + this.$store.state.kid.id,
        {
          exp: this.$store.state.exp, // 새로 얻은 경험치
          wordId: this.$store.state.quiz.id, // 새로 배운 단어
        },
        {
          headers: { 'X-AUTH-TOKEN': this.$store.state.token },
        }
      )
      .then(({ data }) => {
        http
          .get('/kids/' + this.$store.state.kid.id, {
            headers: { 'X-AUTH-TOKEN': this.$store.state.token },
          })
          .then(({ data }) => {
            this.$store.commit('setKid', data)
          })
      })
    // 이전 경험치 % 100
    this.startExp = this.beforeExp % 100

    // (이전 경험치 + 새로 얻은 경험치)
    this.endExp = this.$store.state.exp + this.startExp

    // 이전 레벨
    this.level = this.beforeExp / 100 - (this.beforeExp % 100) / 100

    // 만약 얻은 경험치를 추가했는데 레벨업을 할 경우 ? ? ? 레벨을 업 해야겠지? ? ? ?맞나 ? ? ??
    if (this.endExp / 100 >= 1) this.level += 1

    // exp progress Bar
    this.timer = setInterval(() => {
      if (this.startExp == this.endExp) {
        clearInterval(this.timer)
        this.timer = null
      } else this.startExp = this.startExp + 1
    }, 60)

    // exp circulate Bar
    this.timer2 = setInterval(() => {
      if (this.start == this.$store.state.exp) {
        clearInterval(this.timer2)
        this.timer2 = null
      } else this.start = this.start + 1
    }, 80)
  },
  methods: {
    goMain() {
      this.$router.push('/kid')
    },
    Again() {
      this.$router.push('/selectquiz')
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.chest img {
  width: 18vw;
}

.blue-button {
  margin-right: 3vw;
}
.wide {
  width: 15vw;
  transform: translate(0%, 10%);
}

.header {
  margin-top: 2vh;
  margin-bottom: 4vh;
  font-size: 4vw;
  color: #4b4b4b;
}
</style>
