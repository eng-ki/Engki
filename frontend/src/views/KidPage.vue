<template>
  <div class="background">
    <div class="kid" v-if="!isComponent">
      <div class="change-profile-image">
        <v-btn rounded @click="setComponent(true)">캐릭터 변경</v-btn>
      </div>
      <div class="profile-image">
        <img src="../images/icon/fairytale/005-witch.png" />
      </div>
      <div class="inventory-wrap" @click="setInventory(true)">
        <img src="../images/icon/chest.png" />
        <div class="inventory-text">아이템 인벤토리</div>
      </div>
    </div>
    <div class="box">
      <div class="innerbox">
        <!-- <v-btn rounded large dark v-if="isKidPage">학습 시작</v-btn> -->

        <set-profile-image
          v-if="isComponent"
          @returnKidPage="setComponent(false)"
        />
        <inventory
          v-if="isInventory"
          @returnKidPage="
            setComponent(false)
            setInventory(false)
          "
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
      isComponent: false,
      isInventory: false,
    }
  },
  methods: {
    setComponent(flag) {
      this.isComponent = flag
    },
    setInventory(flag) {
      this.isInventory = flag
    },
  },
}
</script>

<style lang="scss">
@import '../assets/sass/base.scss';
/* 자녀페이지 틀 */
.background .box .innerbox .loginpage {
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
</style>
