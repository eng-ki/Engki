<template>
  <div class="background">
    <div class="kid">
      <div class="change-profile-image">
        <v-btn rounded color="primary" dark>Rounded Button</v-btn>
      </div>
      <div class="profile-image">
        <img src="../images/icon/fairytale/005-witch.png" />
      </div>
      <div class="open-inventory">
        <img src="../images/icon/chest.png" />
      </div>
    </div>
    <div class="box">
      <div class="innerbox">
        <set-profile-image />
        <inventory />
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
};
</script>


<style lang="scss">
@import "../assets/sass/base.scss";
/* 자녀페이지 틀 */
.background .box .innerbox .loginpage {
  position: inherit;
}

/* 프로필 변경 버튼, 프로필 아이콘, 인벤토리 아이콘 영역*/
.kid {
  width: 30%;
  height: 30%;
  bottom: 50%;
  right: 0%;
  position: inherit;
}

.change-profile-image {
  margin-bottom: 5%;
  height: 20%;
  background-color: red;
}

.change-profile-image v-btn {
  width: 50%;
  height: 100%;
}

.profile-image img {
  width: 50%;
  height: 50%;
  object-fit: cover;
}

.open-inventory {
  margin-top: -5%;
}
.open-inventory img {
  margin-top: 0px;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
