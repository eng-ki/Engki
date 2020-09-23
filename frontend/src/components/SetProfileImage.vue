<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <div class="profile-icon">
          <img src="../../public/img/icon/fairytale/005-witch.png" />
          <div>김싸피</div>
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
                    src="../../public/img/icon/fairytale/001-knight.png"
                    style="width: 50%"
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <div class="change-button">
            <v-btn rounded @click="setProfileImage()">변경하기</v-btn>
            <span style="color: white">ㅡㅡ</span>
            <v-btn rounded @click="returnKidPage()">돌아가기</v-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'SetProfileImage',
  data: () => {
    return {
      profileImage: '../../public/img/icon/fairytale/005-witch.png',
      selectedIndex: 0,
    }
  },
  methods: {
    returnKidPage() {
      this.$emit('returnKidPage')
    },
    setProfileImage() {
      this.profileImage = '../../public/img/icon/fairytale/001-knight.png'
    },
    selectProfileImage(num) {
      this.selectedIndex = num
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
.profile-icon {
  opacity: 0.8;
  border-radius: 17vh;
  background: white;
  width: 15%;
  height: 15%;
  top: 20vh;
  left: 50vw;
  position: relative;
  box-shadow: 3px 3px 33px 4px rgba(163, 163, 163, 0.404);
  transform: translate(-50%, -50%);
}

.profile-icon img {
  padding: 7%;
  width: 80%;
  height: 80%;
  opacity: 1;
  object-fit: cover;
}
.profile-icons {
  margin-top: -1%;
  margin-left: 10%;
  margin-right: 10%;
  background-color: white;
  height: 70vh;
  border-radius: 10vh;
  padding: 10vh 2vw 2vh 2vw;
}

.profile-icons .change-button {
  left: 42%;
  bottom: 10%;
  position: absolute;
}
.active {
  border: 4px solid #ff9f9f;
}
</style>
