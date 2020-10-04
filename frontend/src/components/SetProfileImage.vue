<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <div class="profile-icon">
          <img :src="kid.icon" />
          <div class="name">김싸피</div>
          <div class="bar" v-if="!isChangeProfile">
            <b-progress
              :value="value"
              :max="max"
              variant="success"
              show-progress
              class="mb-3"
            ></b-progress>
          </div>
        </div>
        <div class="profile-icons">
          <table>
            <tbody id="contacts">
              <tr v-for="i in 5" :key="i" style="background-color: white">
                <td
                  v-for="j in 10"
                  :key="j"
                  :class="{ block: level < (i - 1) * 10 + (j - 1) }"
                  @click="selectProfileImage((i - 1) * 10 + (j - 1))"
                  style="
                    width: 7vw;
                    height: 2vw;
                    padding-top: 1vw;
                    padding-bottom: 1vw;
                    margin-right: 10vw;
                    margin-left: 10vw;
                  "
                >
                  <img
                    :class="{ active: selectedIndex == (i - 1) * 10 + (j - 1) }"
                    :src="fairytale[(i - 1) * 10 + (j - 1)]"
                    style="width: 52%"
                  />
                </td>
                <span style="width: 1vw"></span>
              </tr>
            </tbody>
          </table>
          <div class="change-button">
            <div class="button-1">
              <v-btn rounded @click="setProfileImage()">변경하기</v-btn>
            </div>
            <div class="button-2">
              <v-btn rounded @click="returnKidPage()">돌아가기</v-btn>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import http from "../utils/http-common.js";
export default {
  name: "SetProfileImage",
  props: {
    kid: null,
  },
  data: () => {
    return {
      fairytale: [
        "/img/icon/fairytale/001-knight.png",
        "/img/icon/fairytale/002-wizard.png",
        "/img/icon/fairytale/003-dwarf.png",
        "/img/icon/fairytale/004-elf.png",
        "/img/icon/fairytale/005-witch.png",
        "/img/icon/fairytale/006-ogre.png",
        "/img/icon/fairytale/007-giant.png",
        "/img/icon/fairytale/008-gnome.png",
        "/img/icon/fairytale/009-little red riding hood.png",
        "/img/icon/fairytale/010-wolf.png",
        "/img/icon/fairytale/011-queen.png",
        "/img/icon/fairytale/012-king.png",
        "/img/icon/fairytale/013-princess.png",
        "/img/icon/fairytale/014-prince.png",
        "/img/icon/fairytale/015-frog prince.png",
        "/img/icon/fairytale/016-fairy.png",
        "/img/icon/fairytale/017-robin hood.png",
        "/img/icon/fairytale/018-pirate.png",
        "/img/icon/fairytale/019-goblin.png",
        "/img/icon/fairytale/020-elf.png",
        "/img/icon/fairytale/021-pig.png",
        "/img/icon/fairytale/022-tin man.png",
        "/img/icon/fairytale/023-scarecrow.png",
        "/img/icon/fairytale/024-cowardly lion.png",
        "/img/icon/fairytale/025-pinocchio.png",
        "/img/icon/fairytale/026-puss in boots.png",
        "/img/icon/fairytale/027-mad hatter.png",
        "/img/icon/fairytale/028-cyclops.png",
        "/img/icon/fairytale/029-white rabbit.png",
        "/img/icon/fairytale/030-mermaid.png",
        "/img/icon/fairytale/031-genie.png",
        "/img/icon/fairytale/032-vampire.png",
        "/img/icon/fairytale/033-unicorn.png",
        "/img/icon/fairytale/034-dragon.png",
        "/img/icon/fairytale/035-phoenix.png",
        "/img/icon/fairytale/036-poison.png",
        "/img/icon/fairytale/037-poison.png",
        "/img/icon/fairytale/038-cauldron.png",
        "/img/icon/fairytale/039-poisonous.png",
        "/img/icon/fairytale/040-magic mirror.png",
        "/img/icon/fairytale/041-excalibur.png",
        "/img/icon/fairytale/042-glass shoes.png",
        "/img/icon/fairytale/043-magic lamp.png",
        "/img/icon/fairytale/044-crystal ball.png",
        "/img/icon/fairytale/045-ring.png",
        "/img/icon/fairytale/046-broom.png",
        "/img/icon/fairytale/047-magic wand.png",
        "/img/icon/fairytale/048-tower.png",
        "/img/icon/fairytale/049-castle.png",
        "/img/icon/fairytale/050-fairytale.png",
      ],
      selectedIndex: 0,
      level: 0,
      value: 0,
      max: 100,
      timer: null,
      isChangeProfile: false,
    };
  },
  mounted() {
    this.level = this.kid.exp / 100 - (this.kid.exp % 100) / 100;
    this.timer = setInterval(() => {
      if (this.value == this.kid.exp % 100) {
        clearInterval(this.timer);
        this.timer = null;
      } else this.value = this.value + 1;
    }, 30);
  },
  methods: {
    returnKidPage() {
      this.$emit("returnKidPage", this.fairytale[this.selectedIndex]);
    },
    setProfileImage() {
      this.$swal({
        title: "선택된 아바타로 변경하시겠습니까?",
        type: "warning",
        showCancelButton: true,
        confirmButtonText: "네",
        cancelButtonText: "아니오",
        showCloseButton: true,
        showLoaderOnConfirm: true,
      }).then((result) => {
        if (result.value) {
          http
            .put(
              "kids/" + this.kid.id,
              {
                icon: this.fairytale[this.selectedIndex],
              },
              {
                headers: { "X-AUTH-TOKEN": this.$store.state.token },
              }
            )
            .then(({ data }) => {
              this.returnKidPage();
            });
        }
      });
    },
    selectProfileImage(num) {
      if (this.level >= num) {
        this.selectedIndex = num;
        this.profileImage = this.fairytale[this.selectedIndex];
      }
    },
  },
};
</script>

<style lang="scss">
@import "../assets/sass/base.scss";
</style>
<style lang="scss" scoped>
/* 로그인페이지 틀 */
.background .box .innerbox {
  position: inherit;
}
.profile-icon {
  opacity: 0.8;
  border-radius: 50%;
  background: white;
  width: 29vh;
  height: 29vh;
  top: 18vh;
  left: 50vw;
  position: relative;
  box-shadow: 3px 3px 33px 4px rgba(163, 163, 163, 0.404);
  transform: translate(-50%, -50%);
}

.profile-icon img {
  padding: 7%;
  width: 72%;
  height: 72%;
  opacity: 1;
  object-fit: cover;
}
.profile-icon .name {
  margin-top: -1vw;
}
.profile-icon .bar {
  position: absolute;
  left: 2.5vw;
  width: 10vw;
}
.profile-icons {
  margin-top: -2%;
  margin-left: 10%;
  margin-right: 10%;
  background-color: white;
  height: 70vh;
  border-radius: 10vh;
  padding: 5vh 1vw 1vh 5vw;
}

.profile-icons .change-button {
  left: 43%;
  bottom: 7%;
  position: absolute;
  display: inline-block;
}
.button-1 {
  float: left;
  margin-right: 3.5vw;
}
.button-2 {
  float: left;
}
.active {
  border: 3px solid #8ebf43;
  border-style: dotted;
}

.block {
  opacity: 0.3;
}
</style>
