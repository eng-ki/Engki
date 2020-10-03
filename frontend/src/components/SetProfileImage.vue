<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <div class="profile-icon">
          <img :src="profileImage" />
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
          <!-- dummy data -->
          <table>
            <tbody id="contacts">
              <tr v-for="i in 5" :key="i" style="background-color: white">
                <td
                  v-for="j in 10"
                  :key="j"
                  :class="{ active: selectedIndex == (i - 1) * 10 + (j - 1) }"
                  @click="selectProfileImage((i - 1) * 10 + (j - 1))"
                >
                  <img
                    :class="{ block: level < (i - 1) * 10 + (j - 1) }"
                    :src="fairytale[(i - 1) * 10 + (j - 1)]"
                    style="width: 40%; padding: 10% 0% 10% 0%"
                  />
                </td>
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
export default {
  name: "SetProfileImage",
  props: {
    point: 0,
  },
  data: () => {
    return {
      profileImage: "/img/icon/fairytale/005-witch.png",
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
      ],
      selectedIndex: 0,
      level: 0,
      value: 0,
      max: 100,
      timer: null,
    };
  },
  mounted() {
    this.level = this.point / 100 - (this.point % 100) / 100;
    this.timer = setInterval(() => {
      if (this.value == this.point % 100) {
        clearInterval(this.timer);
        this.timer = null;
      } else this.value = this.value + 1;
    }, 30);
  },
  methods: {
    returnKidPage() {
      this.$emit("returnKidPage");
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
          // 변경 api 호출 후
          // 인벤토리로
          this.returnKidPage();
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
  width: 31vh;
  height: 31vh;
  top: 20vh;
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
  margin-top: -3%;
  margin-left: 10%;
  margin-right: 10%;
  background-color: white;
  height: 70vh;
  border-radius: 10vh;
  padding: 10vh 2vw 2vh 2vw;
}

.profile-icons .change-button {
  left: 43%;
  bottom: 9%;
  position: absolute;
  display: inline-block;
}
.button-1 {
  margin-left: -1vw;
  float: left;
  margin-right: 2vw;
}
.button-2 {
  float: left;
}
.active {
  border: 4px solid #8ebf43;
  border-style: dotted;
}

.block {
  opacity: 0.3;
}
</style>
