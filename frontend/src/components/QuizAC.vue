<template>
  <div style="background-color: pink">퀴즈 AC : div 영역 테스트</div>
</template>
<script>
export default {
  props: {
    isDone: false,
    stage: 0, // 0일때 quiz A, 2일때 quiz C (AC에만 있는 props)
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
      // 다했어요 버튼이 클릭됐을때 호출되는 함수, 정답이 맞으면 true, 틀리면 false 리턴
      return true
    },
  },
}
</script>
