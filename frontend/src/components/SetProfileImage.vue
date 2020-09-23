<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <div class="profile-icon">
          <img src="../static/img/icon/fairytale/001-knight.png" />
          <div>김싸피</div>
        </div>
        <div class="profile-icons">
          <!-- dummy data -->
          <table>
            <tbody id="contacts">
              <tr v-for="item in 5" :key="item" style="background-color: white;">
                <td @click="clickIcon(item)">
                  <img src="../static/img/icon/fairytale/001-knight.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/fairytale/002-wizard.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/fairytale/003-dwarf.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/fairytale/004-elf.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/fairytale/005-witch.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/fairytale/006-ogre.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/fairytale/007-giant.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/fairytale/008-gnome.png" style="width:50%;" />
                </td>
                <td>
                  <img
                    src="../static/img/icon/fairytale/009-little red riding hood.png"
                    style="width:50%;"
                  />
                </td>
                <td>
                  <img src="../static/img/icon/fairytale/010-wolf.png" style="width:50%;" />
                </td>
              </tr>
            </tbody>
          </table>
          <div class="change-button">
            <v-btn rounded @click="setProfileImage()">변경하기</v-btn>
            <v-btn rounded @click="returnKidPage()">돌아가기</v-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "SetProfileImage",
  methods: {
    returnKidPage() {
      this.$emit("returnKidPage");
    },
    setProfileImage() {
      alert("HI");
    },
    clickIcon() {
      alert("HIHI");
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
  border-radius: 17vh;
  background: white;
  width: 170px;
  height: 170px;
  top: 20vh;
  left: 50vw;
  position: relative;
  box-shadow: 3px 3px 33px 4px rgba(163, 163, 163, 0.404);
  transform: translate(-50%, -50%);
}

.profile-icon img {
  padding: 10%;
  width: 80%;
  height: 80%;
  opacity: 1;
  object-fit: cover;
}
.profile-icons {
  margin-top: -1%;
  margin-left: 5%;
  margin-right: 5%;
  background-color: white;
  height: 400px;
  border-radius: 10vh;
  padding: 5vh 2vw 2vh 2vw;
}

.profile-icons .change-button {
  left: 42%;
  bottom: 10%;
  position: absolute;
}
</style>
