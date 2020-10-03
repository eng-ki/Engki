<template>
  <div>
    <div class="chest">
      <b-progress
        :value="value"
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
export default {
  name: "GetItem",
  props: {
    correct: null,
  },
  data: () => {
    return {
      level: 0, //현재 레벨
      value: 0,
      timer: null,
      timer2: null,
      point: 0, // 이전 경험치 + 새로 얻은 경험치
      max: 0,
      start: 0,
      exp: 0,
    };
  },
  mounted() {
    this.calculate();
    // 이전 경험치 가져오는 api 호출
    this.point = 152;

    // 이전 경험치 % 100
    this.value = this.point % 100;

    // (이전 경험치 + 새로 얻은 경험치) %100
    this.max = this.correct + this.value;

    // 이전 레벨
    this.level = this.point / 100 - (this.point % 100) / 100;

    if (this.max / 100 >= 1) this.level += 1;

    this.timer = setInterval(() => {
      if (this.value == this.max) {
        clearInterval(this.timer);
        this.timer = null;
      } else this.value = this.value + 1;
    }, 60);

    this.timer2 = setInterval(() => {
      if (this.start == this.correct) {
        clearInterval(this.timer2);
        this.timer2 = null;
      } else this.start = this.start + 1;
    }, 80);
  },
  methods: {
    goMain() {
      this.$router.push("/kid");
    },
    Again() {
      this.$router.push("/selectquiz");
    },
    calculate() {
      var score = 0;
      for (var i = 0; i < this.correct.length; i++) score += this.correct[i];
      this.correct = score;
    },
  },
};
</script>
<style lang="scss">
@import "../assets/sass/base.scss";
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
