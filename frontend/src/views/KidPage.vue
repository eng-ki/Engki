<template>
  <div class="background">
    <div class="kid" v-if="!isChangeProfile" @click="goChangeProfile()">
      <div class="profile-image">
        <img :src="kid.icon" />
      </div>
      <div class="inventory-wrap">
        <img src="../../public/img/icon/chest.png" />
        <div class="inventory-text">캐릭터 변경하기</div>
      </div>
    </div>
    <div class="box">
      <div class="innerbox">
        <button
          class="parents-button start-button"
          style="
            top: 28%;
            left: 8.5%;
            position: absolute;
            width: 50%;
            height: 40%;
            font-size: 9vw;
            border-radius: 10vh;
            font-family: 'Jua', sans-serif;
            padding: 1%;
            color: #24282c;
          "
          @click="goSelectQuiz()"
        >
          공부 시작하기
        </button>
        <set-profile-image
          :kid="kid"
          v-if="isChangeProfile"
          @returnKidPage="goChangeProfile()"
        />
      </div>
    </div>
  </div>
</template>

<script>
import SetProfileImage from '@/components/SetProfileImage.vue'
import Inventory from '@/components/Inventory.vue'

export default {
  name: 'KidPage',
  components: {
    SetProfileImage,
    Inventory,
  },
  data: () => {
    return {
      isChangeProfile: false,
      kid: null,
    }
  },
  created() {
    this.kid = this.$store.state.kid
    console.log(this.kid)
  },
  methods: {
    goChangeProfile() {
      this.isChangeProfile = !this.isChangeProfile
    },
    goSelectQuiz() {
      this.$router.push('/selectquiz')
    },
  },
}
</script>

<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
/* 자녀페이지 틀 */
.background .box .innerbox {
  position: inherit;
}

/* 프로필 변경 버튼, 프로필 아이콘, 인벤토리 아이콘 영역*/
.kid {
  width: 35%;
  height: 35%;
  bottom: 45%;
  right: -1.9%;
  position: inherit;
}

.profile-image img {
  width: 50%;
  height: 50%;
  object-fit: cover;
}

.inventory-wrap {
  margin-top: -5%;
  position: relative;
}
.inventory-wrap img {
  margin-top: 0px;
  width: 90%;
  height: 100%;
  object-fit: cover;
  vertical-align: middle;
}

.inventory-wrap .inventory-text {
  text-align: center;
  position: absolute;
  top: 80%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 2vw;
}

.start-button {
  top: 30%;
  left: 10%;
  position: absolute;
  width: 50%;
  height: 40%;
  font-size: 9vw;
  border-radius: 10vh;
  font-family: 'Jua', sans-serif;
  padding: 1%;
  color: #24282c;
}
</style>
