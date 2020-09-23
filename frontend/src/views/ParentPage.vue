<template>
  <div class="background">
    <div class="parent-board"></div>
    <!-- <set-kid />
    <report />
    <upload-picture />
    <set-email />-->
  </div>
</template>

<script>
import SetKid from '@/components/SetKid.vue'
import Report from '@/components/Report.vue'
import UploadPicture from '@/components/UploadPicture.vue'
import SetEmail from '@/components/SetEmail.vue'

export default {
  name: 'ParentPage',
  components: {
    SetKid,
    Report,
    UploadPicture,
    SetEmail,
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

.parent-board {
  margin-top: 10%;
  margin-left: 6%;
  margin-right: 6%;
  background-color: white;
  height: 70vh;
  border-radius: 10vh;
  padding: 10vh 2vw 2vh 2vw;
}
</style>
