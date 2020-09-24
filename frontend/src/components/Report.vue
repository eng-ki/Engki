<template>
  <div>
    {{ kid }}
    <div>리포트</div>
  </div>
</template>
<script>
export default {
  name: "Report",
  props: {
    kid: null,
  },
};
</script>
<style lang="scss">
@import "../assets/sass/base.scss";
</style>
<style lang="scss" scoped>
* {
  font-family: "GmarketSansMedium";
  color: #4b4b4b;
}
</style>