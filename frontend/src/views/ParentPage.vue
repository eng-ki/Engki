<template>
  <div class="background">
    <div class="kids-header">
      <div class="datalist">
        <div
          v-for="(kid, index) in kids"
          v-bind:key="index"
          class="data"
          @click="selectKid(index)"
        >
          <img class="dataimage" :src="kid.url" />

          <div class="dataname">
            {{ kid.name }}
          </div>
          <div :class="{ selected: selectedIndex == index }"></div>
        </div>
      </div>
      <img class="add-kid" src="../../public/img/icon/SETTING.png" />
    </div>
    <div class="parent-board">
      <v-row no-gutters>
        <v-col cols="2">
          <div class="category">
            <div class="report" @click="selectComponent(true)">감정 그래프</div>
            <div class="custom" @click="selectComponent(false)">
              커스텀 학습
            </div>
          </div>
        </v-col>
        <v-col cols="10">
          <div class="category-board">
            <report :kid="kids[selectedIndex]" v-if="isReport" />
            <upload-picture :kid="kids[selectedIndex]" v-else />
          </div>
        </v-col>
      </v-row>
    </div>

    <!-- <set-kid />
    <set-email />-->
  </div>
</template>

<script>
import SetKid from "@/components/SetKid.vue";
import Report from "@/components/Report.vue";
import UploadPicture from "@/components/UploadPicture.vue";
import SetEmail from "@/components/SetEmail.vue";

export default {
  name: "ParentPage",
  components: {
    SetKid,
    Report,
    UploadPicture,
    SetEmail,
  },
  data: () => {
    return {
      kids: [
        { url: "/img/icon/fairytale/001-knight.png", name: "김싸피" },
        { url: "/img/icon/fairytale/002-wizard.png", name: "김싸파" },
        { url: "/img/icon/fairytale/003-dwarf.png", name: "김싸푸" },
        { url: "/img/icon/fairytale/004-elf.png", name: "김싸표" },
        { url: "/img/icon/fairytale/005-witch.png", name: "김싸패" },
        { url: "/img/icon/fairytale/006-ogre.png", name: "김싸핑" },
      ],
      selectedIndex: 0,
      isReport: true,
    };
  },
  methods: {
    selectKid(index) {
      this.selectedIndex = index;
    },
    selectComponent(flag) {
      this.isReport = flag;
    },
  },
};
</script>
<style lang="scss">
@import "../assets/sass/base.scss";
</style>
<style lang="scss" scoped>
* {
  font-family: "GmarketSansMedium";
  color: #4b4b4b;
}
/* 로그인페이지 틀 */
.background .box .innerbox {
  position: inherit;
}

.parent-board {
  margin-top: -4%;
  margin-left: 6%;
  margin-right: 6%;
  background-color: white;
  height: 75vh;
  border-radius: 10vh;
  padding: 15vh 2vw 2vh 2vw;
}

.add-kid {
  width: 5vw;
  position: absolute;
  top: 10vh;
  right: 10vw;
}
.datalist {
  position: relative;
  top: 5vh;
  margin-right: 9vw;
  margin-left: 10vw;
  overflow: scroll;
  white-space: nowrap;
  width: 74vw;
  &::-webkit-scrollbar {
    width: 10px;
  }
  &::-webkit-scrollbar-thumb {
    width: 10px;
    background-color: rgba(220, 219, 223, 0.486);
    border-radius: 30px;
    background-clip: padding-box;
    border: 6px solid transparent;
  }
  &::-webkit-scrollbar-track {
    width: 5px;
    background-color: transparent;
    border-radius: 30px;
  }
}

.data {
  display: inline-block;
  position: relative;
  width: 30vh;
}

.datalist .dataimage {
  position: relative;
  width: 20vh;
  vertical-align: middle;
}

.datalist .dataname {
  padding: 10px 0px;
  position: relative;
  width: 20h;
  text-align: center;
  vertical-align: middle;
  font-size: 3vh;
}
.datalist .selected {
  border-bottom: 0.5vw solid #ffe26d;
  width: 40%;
  text-align: center;
  margin-top: -1vh;
  margin-bottom: 0.5vh;
  margin-left: 4.5vw;
  border-radius: 10vw;
}

.category {
  height: 55vh;
  font-size: 4vh;
}

.category .report {
  top: 11vh;
  position: relative;
}

.category .custom {
  top: 23vh;
  position: relative;
}

.category-board {
  height: 55vh;
  background-color: lightgrey;
  padding: 1vw;
}
</style>
