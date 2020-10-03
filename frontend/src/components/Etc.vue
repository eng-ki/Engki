<template>
  <div>
    <div class="etc"></div>
    {{ correct }}
    <break-time class="break-time" v-on:continue="goEtc()" v-if="isBreakTime" />
    <finish :correct="correct" class="finish" v-else-if="isFinish" />
    <img class="tiki" src="../../public/img/icon/moving_tiki.gif" />
  </div>
</template>

<script>
import BreakTime from "@/components/BreakTime.vue";
import Finish from "@/components/Finish.vue";

export default {
  name: "Etc",
  components: {
    BreakTime,
    Finish,
  },
  props: {
    isBreakTime: false,
    isFinish: false,
    correct: null,
  },
  methods: {
    goEtc() {
      this.$emit("continue");
    },
  },
};
</script>
<style lang="scss">
@import "../assets/sass/base.scss";
</style>
<style lang="scss" scoped>
.etc {
  background-color: white;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;

  /* 사이즈 fullscreen */
  min-width: 500px;
  min-height: 490px;
  width: 100vw;
  height: 100vh;

  /* 좌표 설정 */
  position: absolute;
  z-index: 4;
  opacity: 0.9;
}

.tiki {
  position: absolute;
  bottom: 2vh;
  left: 2vw;
  z-index: 5;
  width: 25vw;
}

.break-time {
  position: absolute;
  z-index: 5;
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.finish {
  position: absolute;
  z-index: 5;
  top: 42%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
