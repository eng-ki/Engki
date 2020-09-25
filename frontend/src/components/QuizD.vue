<template>
  <div style="background-color: red">퀴즈 D : div 영역 테스트</div>
</template>
<script>
export default {
  props: {
    isDone: false,
  },
  mounted() {
    //api 호출
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) this.$emit('correct')
      else this.$emit('wrong')
    },
  },
  methods: {
    isCorrect() {
      return true
    },
  },
}
</script>
