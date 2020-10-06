<template>
  <div>
    <div class="chest">
      <img :src="item.url" />
    </div>
    <div class="header">{{ item.name }}을 획득했습니다!</div>
    <div class="gi-buttons">
      <button class="blue-button wide" @click="goMain()">메인으로</button>
      <button class="green-button wide" @click="Again()">공부 더 하기</button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'GetItem',
  data: () => {
    return {
      item: {
        url: '/img/icon/jungle/049-mushroom.png',
        name: '버섯',
      },
    };
  },
  mounted() {
    // 아이템 가져오는 api 호출
  },
  methods: {
    goMain() {
      this.$router.push('/kid');
    },
    Again() {
      this.$router.push('/selectquiz');
    },
  },
};
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
