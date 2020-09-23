<template>
  <div class="background">
    <div class="kid" v-if="!isChangeProfile">
      <div class="change-profile-image">
        <v-btn rounded @click="goProfile()">캐릭터 변경</v-btn>
      </div>
      <div class="profile-image">
        <img src="../static/img/icon/fairytale/005-witch.png" />
      </div>
      <div class="inventory-wrap" @click="goInventory()">
        <img src="../static/img/icon/chest.png" />
        <div class="inventory-text">아이템 인벤토리</div>
      </div>
    </div>
    <div class="box">
      <div class="innerbox">
        <button class="parents-button start-button" @click="kakaoLogin()">공부 시작하기</button>
        <set-profile-image v-if="isChangeProfile" @returnKidPage="goProfile()" />
        <inventory title="BAG" :itemlist.sync="itemlist" :visible.sync="visible" />
      </div>
    </div>
  </div>
</template>

<script>
import SetProfileImage from "@/components/SetProfileImage.vue";
import Inventory from "@/components/Inventory.vue";

export default {
  name: "KidPage",
  components: {
    SetProfileImage,
    Inventory,
  },
  data: () => {
    return {
      isChangeProfile: false,
      visible: false,
      itemlist: [
        "../static/img/icon/fairytale/001-knight.png",
        "../static/img/icon/fairytale/002-wizard.png",
        "../static/img/icon/fairytale/003-dwarf.png",
      ],
    };
  },
  cerated() {},
  methods: {
    goProfile() {
      this.isChangeProfile = !this.isChangeProfile;
    },
    goInventory() {
      this.visible = !this.visible;
    },
    kakaoLogin() {
      Kakao.Auth.login({
        success: this.kakaoLoginStore,
      });
    },
    kakaoLoginStore(authObj) {
      alert(authObj.access_token);
    },
  },
};
</script>


<style lang="scss">
@import "../assets/sass/base.scss";
/* 자녀페이지 틀 */
.background .box .innerbox {
  position: inherit;
}

/* 프로필 변경 버튼, 프로필 아이콘, 인벤토리 아이콘 영역*/
.kid {
  width: 30%;
  height: 30%;
  bottom: 52%;
  right: 0%;
  position: inherit;
}

.change-profile-image {
  margin-bottom: 5%;
  height: 20%;
}

.change-profile-image .v-btn {
  width: 50%;
  min-width: 50%;
  height: 100%;
  min-height: 100%;
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
  width: 100%;
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
  font-family: "Jua", sans-serif;
  padding: 1%;
  color: #24282c;
}
</style>

