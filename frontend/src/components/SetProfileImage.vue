<template>
  <div class="background">
    <div class="box">
      <div class="innerbox"></div>
    </div>
    <v-btn rounded large dark @click="returnKidPage()">돌아가기</v-btn>
  </div>
</template>
<script>
export default {
  name: 'SetProfileImage',
  methods: {
    returnKidPage() {
      this.$emit('returnKidPage')
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
/* 로그인페이지 틀 */
.background .box .innerbox {
  position: inherit;
}
</style>
