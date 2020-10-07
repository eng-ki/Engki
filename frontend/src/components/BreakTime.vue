<template>
  <div>
    <div class="header">
      <p>{{ this.$store.state.kid.name }} 어린이</p>
      <p>5분 동안</p>
      <p>쉬는시간을 가져볼까요?</p>
    </div>
    <div v-if="isTimer">
      <div class="timer">
        <span class="minute">{{ minutes }}</span>
        <span>:</span>
        <span class="seconds">{{ seconds }}</span>
      </div>
      <button class="blue-button wide" @click="goQuizPage()">
        그만 쉴래요
      </button>
    </div>

    <div class="bt-buttons" v-else>
      <button class="blue-button" @click="startTimer()">네</button>
      <button class="green-button" @click="goQuizPage()">아니오</button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BreakTime',
  data: () => {
    return {
      isTimer: false,
      timer: null,
      totalTime: 5 * 60,
    }
  },
  watch: {
    totalTime: function (val) {
      if (val == 0) this.$emit('continue')
    },
  },
  computed: {
    minutes: function () {
      const minutes = Math.floor(this.totalTime / 60)
      return this.padTime(minutes)
    },
    seconds: function () {
      const seconds = this.totalTime - this.minutes * 60
      return this.padTime(seconds)
    },
  },
  methods: {
    goQuizPage() {
      if (this.isTimer) {
        this.stopTimer()
      }
      this.$swal({
        title: '퀴즈 페이지로 돌아갈까요?',
        showCancelButton: true,
        confirmButtonText: '네',
        cancelButtonText: '아니요',
        showCloseButton: true,
      }).then((result) => {
        if (result.value) {
          this.$emit('continue')
        } else {
          if (this.isTimer) {
            this.startTimer()
          }
        }
      })
    },
    startTimer: function () {
      this.isTimer = true
      this.timer = setInterval(() => this.countdown(), 1000) //1000ms = 1 second
    },
    stopTimer: function () {
      clearInterval(this.timer)
      this.timer = null
    },
    padTime: function (time) {
      return (time < 10 ? '0' : '') + time
    },
    countdown: function () {
      this.totalTime--
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.header {
  margin-top: 15vh;
  font-size: 4.5vw;
  color: #4b4b4b;
}

.blue-button {
  margin-right: 4vw;
}

.wide {
  margin-top: -2vh;
  width: 15vw;
  transform: translate(15%, 50%);
}

.timer {
  padding-top: 4vh;
  font-size: 7vw;
  color: gray;
  text-shadow: 2px 2px 3px rgba(0, 0, 0, 0.5);
}

.bt-buttons {
  padding-top: 7vh;
}
</style>
