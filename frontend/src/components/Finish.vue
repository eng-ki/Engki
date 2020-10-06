<template>
  <div>
    <div class="chest">
      <div v-if="isLevelUp">
        <img :src="fairytale[level]" />
        <span style="font-size: 1vw">새로운 캐릭터를 획득하였습니다!</span>
      </div>
      <b-progress
        :value="startExp"
        max="100"
        height="8vh"
        variant="success"
        show-progress
      ></b-progress>
    </div>
    <div class="header">+ {{ start }} exp</div>
    <div class="gi-buttons">
      <button class="blue-button wide" @click="goMain()">메인으로</button>
      <button class="green-button wide" @click="Again()">공부 더 하기</button>
    </div>
  </div>
</template>
<script>
import http from '../utils/http-common.js';
export default {
  name: 'GetItem',
  data: () => {
    return {
      level: 0, //현재 레벨
      beforeExp: 0, // 이전 경험치
      startExp: 0, //시작 경험치
      endExp: 0, // 끝 경험치
      timer: null, // 막대기 바 올라가는 타이머
      timer2: null, // 경험치 수치 올라가는 타이머
      start: 0,
      exp: 0,
      isLevelUp: false,
      fairytale: [
        '/img/icon/fairytale/001-knight.png',
        '/img/icon/fairytale/002-wizard.png',
        '/img/icon/fairytale/003-dwarf.png',
        '/img/icon/fairytale/004-elf.png',
        '/img/icon/fairytale/005-witch.png',
        '/img/icon/fairytale/006-ogre.png',
        '/img/icon/fairytale/007-giant.png',
        '/img/icon/fairytale/008-gnome.png',
        '/img/icon/fairytale/009-little red riding hood.png',
        '/img/icon/fairytale/010-wolf.png',
        '/img/icon/fairytale/011-queen.png',
        '/img/icon/fairytale/012-king.png',
        '/img/icon/fairytale/013-princess.png',
        '/img/icon/fairytale/014-prince.png',
        '/img/icon/fairytale/015-frog prince.png',
        '/img/icon/fairytale/016-fairy.png',
        '/img/icon/fairytale/017-robin hood.png',
        '/img/icon/fairytale/018-pirate.png',
        '/img/icon/fairytale/019-goblin.png',
        '/img/icon/fairytale/020-elf.png',
        '/img/icon/fairytale/021-pig.png',
        '/img/icon/fairytale/022-tin man.png',
        '/img/icon/fairytale/023-scarecrow.png',
        '/img/icon/fairytale/024-cowardly lion.png',
        '/img/icon/fairytale/025-pinocchio.png',
        '/img/icon/fairytale/026-puss in boots.png',
        '/img/icon/fairytale/027-mad hatter.png',
        '/img/icon/fairytale/028-cyclops.png',
        '/img/icon/fairytale/029-white rabbit.png',
        '/img/icon/fairytale/030-mermaid.png',
        '/img/icon/fairytale/031-genie.png',
        '/img/icon/fairytale/032-vampire.png',
        '/img/icon/fairytale/033-unicorn.png',
        '/img/icon/fairytale/034-dragon.png',
        '/img/icon/fairytale/035-phoenix.png',
        '/img/icon/fairytale/036-poison.png',
        '/img/icon/fairytale/037-poison.png',
        '/img/icon/fairytale/038-cauldron.png',
        '/img/icon/fairytale/039-poisonous.png',
        '/img/icon/fairytale/040-magic mirror.png',
        '/img/icon/fairytale/041-excalibur.png',
        '/img/icon/fairytale/042-glass shoes.png',
        '/img/icon/fairytale/043-magic lamp.png',
        '/img/icon/fairytale/044-crystal ball.png',
        '/img/icon/fairytale/045-ring.png',
        '/img/icon/fairytale/046-broom.png',
        '/img/icon/fairytale/047-magic wand.png',
        '/img/icon/fairytale/048-tower.png',
        '/img/icon/fairytale/049-castle.png',
        '/img/icon/fairytale/050-fairytale.png',
      ],
    };
  },
  mounted() {
    // 이전 경험치
    this.beforeExp = this.$store.state.kid.exp;
    // 새로 얻은 경험치와 학습 단어 저장
    http
      .post(
        'edu/' + this.$store.state.kid.id,
        {
          exp: this.$store.state.exp, // 새로 얻은 경험치
          wordId: this.$store.state.quiz.id, // 새로 배운 단어
        },
        {
          headers: { 'X-AUTH-TOKEN': this.$store.state.token },
        }
      )
      .then(({ data }) => {
        http
          .get('/kids/' + this.$store.state.kid.id, {
            headers: { 'X-AUTH-TOKEN': this.$store.state.token },
          })
          .then(({ data }) => {
            this.$store.commit('setKid', data);
          });
      });
    // 이전 경험치 % 100
    this.startExp = this.beforeExp % 100;

    // (이전 경험치 + 새로 얻은 경험치)
    this.endExp = this.$store.state.exp + this.startExp;

    // 이전 레벨
    this.level = parseInt(this.beforeExp / 100 - (this.beforeExp % 100) / 100);

    // 만약 얻은 경험치를 추가했는데 레벨업을 할 경우 ? ? ? 레벨을 업 해야겠지? ? ? ?맞나 ? ? ??
    if (this.endExp / 100 >= 1) {
      this.isLevelUp = true;
      this.level += 1;
    }

    // exp progress Bar
    this.timer = setInterval(() => {
      if (this.startExp == this.endExp || this.startExp == 100) {
        clearInterval(this.timer);
        this.timer = null;
      } else this.startExp = this.startExp + 1;
    }, 60);

    // exp circulate Bar
    this.timer2 = setInterval(() => {
      if (this.start == this.$store.state.exp) {
        clearInterval(this.timer2);
        this.timer2 = null;
      } else this.start = this.start + 1;
    }, 80);
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
