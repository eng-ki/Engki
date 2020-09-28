<template>
  <div>
    <div class="chest"><img src="../../public/img/icon/chest.png" /></div>
    <div class="header">어떤 아이템이 나올까요 ~?</div>
    <button class="blue-button wide" @click="goGetItem()">
      보물상자&nbsp;열기
    </button>
  </div>
</template>
<script>
export default {
  name: 'GoldBox',
  data: () => {
    return {}
  },
  methods: {
    goGetItem() {
      this.$emit('goGetItem')
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.chest img {
  width: 25vw;
}
.header {
  margin-top: 4vh;
  margin-bottom: 4vh;
  font-size: 3.8vw;
  color: #4b4b4b;
}
.wide {
  width: 20vw;
}
</style>
