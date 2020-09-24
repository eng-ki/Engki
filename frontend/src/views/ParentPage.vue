<template>
  <div class="background">
    <set-email
      v-on:visible="selectKid(0)"
      from="parent"
      v-if="selectedIndex == -1"
    />
    <set-kid
      v-on:returnParentPage="isAddKid = false"
      v-on:update="updateKid"
      v-else-if="kids.length == 0 || isAddKid"
    />
    <div v-else>
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
            <div
              @click="deleteKid(index)"
              class="deletemark"
              v-if="selectedIndex == index"
            >
              X
            </div>
            <div :class="{ selected: selectedIndex == index }"></div>
          </div>
        </div>
        <div class="add-kid" @click="isAddKid = true">
          <img src="../../public/img/icon/plus.png" style="width: 85%" />
          <div style="margin-top: -0.3vh">아이 등록</div>
        </div>
        <div class="parent" @click="selectKid(-1)">
          <img src="../../public/img/icon/couple.png" style="width: 100%" />
          <div style="margin-top: -1vh">내 정보</div>
        </div>
      </div>
      <div class="parent-board">
        <v-row no-gutters>
          <v-col cols="2">
            <div class="category">
              <div
                class="report"
                :class="{ activect: isReport == true }"
                @click="isReport = true"
              >
                감정 그래프
              </div>
              <div
                class="custom"
                :class="{ activect: isReport == false }"
                @click="isReport = false"
              >
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
    </div>
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
      ],
      selectedIndex: 0,
      isReport: true,
      isAddKid: false,
    };
  },
  methods: {
    selectKid(index) {
      this.selectedIndex = index;
    },
    deleteKid(index) {
      this.$swal({
        title: "자녀를 삭제하시겠습니까?",
        text: "자녀 데이터가 모두 삭제됩니다",
        type: "warning",
        showCancelButton: true,
        confirmButtonText: "삭제",
        cancelButtonText: "취소",
        showCloseButton: true,
        showLoaderOnConfirm: true,
      }).then((result) => {
        if (result.value) {
          // 1. 백엔드에서 삭제

          // 2. 처음 받아온 데이터 삭제
          this.kids.splice(index, 1);
          if (this.kids.length != 0) this.selectedIndex = 0;
        }
      });
    },
    updateKid: function (kid) {
      this.kids.push(kid);
      this.isAddKid = false;
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
  top: 20vh;
  right: 8vw;
  background-color: white;
  width: 85vw;
  height: 75vh;
  border-radius: 10vh;
  padding: 15vh 2vw 2vh 2vw;
  position: absolute;
  z-index: 1;
}

.parent {
  width: 8vw;
  position: absolute;
  top: 7vh;
  right: 12vw;
  font-size: 3vh;
  z-index: 2;
}
.datalist {
  z-index: 2;
  position: absolute;
  top: 5vh;
  margin-left: 10vw;
  overflow: scroll;
  white-space: nowrap;
  width: 45vw;
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
  width: 18vh;
  vertical-align: middle;
}

.datalist .dataname {
  padding: 10px 0px;
  position: relative;
  width: 18h;
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

.datalist .deletemark {
  margin-top: -5vh;
  right: 1vw;
  position: absolute;
  width: 40%;
}

.parent .selected {
  border-bottom: 0.5vw solid #ffe26d;
  width: 70%;
  margin-left: 1.5vw;
  text-align: center;
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
  padding: 1vw;
}

.add-kid {
  width: 7.7vw;
  position: absolute;
  top: 9.2vh;
  right: 23vw;
  font-size: 3vh;
  z-index: 2;
}

.add-kid img {
  width: 8vw;
}

.activect {
  background-color: #ffe26d;
}
</style>
