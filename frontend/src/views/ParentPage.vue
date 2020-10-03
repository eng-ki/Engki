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
    <div v-else class="board">
      <div class="board-header">
        <div class="board-header-kid">
          <div class="card-carousel-wrapper">
            <div
              class="card-carousel--nav__left"
              @click="moveCarousel(-1)"
              :disabled="atHeadOfList"
            ></div>
            <div class="card-carousel">
              <div class="card-carousel--overflow-container">
                <div
                  class="card-carousel-cards"
                  :style="{
                    transform: 'translateX' + '(' + currentOffset + 'px' + ')',
                  }"
                >
                  <div
                    class="card-carousel--card"
                    v-for="(kid, index) in kids"
                    v-bind:key="index"
                    @click="selectKid(index)"
                  >
                    <img :src="kid.url" />
                    <div :class="{ selected: selectedIndex == index }">
                      <div class="dataname">{{ kid.name }}</div>
                      <div
                        @click="deleteKid(index)"
                        class="deletemark"
                        v-if="selectedIndex == index"
                      >
                        X
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div
              class="card-carousel--nav__right"
              @click="moveCarousel(1)"
              :disabled="atEndOfList"
            ></div>
          </div>
        </div>
        <div class="board-header-parent">
          <div class="add-kid" @click="isAddKid = true">
            <img src="../../public/img/icon/plus3.png" />
            <div>자녀 등록</div>
          </div>
          <div class="parent" @click="selectKid(-1)">
            <img src="../../public/img/icon/couple.png" />
            <div>내 정보</div>
          </div>
        </div>
      </div>
      <div class="board-body">
        <v-row no-gutters>
          <v-col cols="1"> </v-col>
          <v-col cols="10">
            <div class="category-board">
              <report :kid="kids[selectedIndex]" v-if="isReport" />
              <upload-picture :kid="kids[selectedIndex]" v-else />
              <!-- <camera v-else /> -->
            </div>
          </v-col>

          <v-col cols="1"> </v-col>
        </v-row>
      </div>
      <!-- <div> -->
      <div>
        <img
          class="backtomain"
          src="../../public/img/icon/street-sign-f2.png"
          v-if="isReport == true"
        />
        <img
          class="backtomain"
          src="../../public/img/icon/street-sign-f3.png"
          v-if="isReport == false"
        />
        <div
          class="custom-btn"
          @click="isReport = false"
          v-if="isReport == true"
        ></div>
        <div
          class="custom-btn"
          @click="isReport = true"
          v-if="isReport == false"
        ></div>
        <div class="backtomain-btn" @click="backtomain()"></div>
      </div>

      <!-- </div> -->
    </div>
  </div>
</template>

<script>
import SetKid from '@/components/SetKid.vue'
import Report from '@/components/Report.vue'
import UploadPicture from '@/components/UploadPicture.vue'
import SetEmail from '@/components/SetEmail.vue'

export default {
  name: 'ParentPage',
  components: {
    SetKid,
    Report,
    UploadPicture,
    SetEmail,
  },
  data: () => {
    return {
      kids: [
        { url: '/img/icon/fairytale/001-knight.png', name: '김싸피' },
        { url: '/img/icon/fairytale/002-wizard.png', name: '김싸파' },
        { url: '/img/icon/fairytale/003-dwarf.png', name: '김싸푸' },
      ],
      selectedIndex: 0,
      isReport: true,
      isAddKid: false,
      currentOffset: 0,
      windowSize: 5, // carousel에 띄워줄 아이콘 갯수! <- 반응형으로 할거면 화면에 몇개 나오는지 계산해서 여기 넣어야 공백 안생길듯
      paginationFactor: 50,
    }
  },
  computed: {
    atEndOfList() {
      return (
        this.currentOffset <=
        this.paginationFactor * -1 * (this.kids.length - this.windowSize)
      )
    },
    atHeadOfList() {
      return this.currentOffset === 0
    },
  },
  methods: {
    selectKid(index) {
      this.selectedIndex = index
    },
    deleteKid(index) {
      this.$swal({
        title:
          '<span style="font-family: GmarketSansMedium;font-size:1.5vw;">자녀 데이터를 삭제하시겠습니까?</span>',

        type: 'warning',
        showCancelButton: true,
        confirmButtonText: '삭제',
        cancelButtonText: '취소',
        showCloseButton: true,
        showLoaderOnConfirm: true,
      }).then((result) => {
        if (result.value) {
          // K002
          // http
          //   .delete('/kids/' + this.kid_id, {
          //     headers: { Authorization: access_token },
          //   })
          //   .then(({ data }) => {})
          // 2. 처음 받아온 데이터 삭제
          this.kids.splice(index, 1)
          if (this.kids.length != 0) this.selectedIndex = 0
        }
      })
    },
    updateKid: function (kid) {
      this.kids.push(kid)
      this.isAddKid = false
    },
    moveCarousel(direction) {
      if (direction === 1 && !this.atEndOfList) {
        this.currentOffset -= this.paginationFactor
      } else if (direction === -1 && !this.atHeadOfList) {
        this.currentOffset += this.paginationFactor
      }
    },
    backtomain() {
      this.$router.push('/selectkid')
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
$top-margin: 16vh;
$left-margin: 7vw;
$display-width: 85vw;
$board-height: 78vh;
$header-height: 15vh;
$body-height: $board-height - $header-height;

$icon-size: 12vh;
$font-size: 2.5vh;
$arrowcolor: black;

* {
  font-family: 'GmarketSansMedium';
  color: #4b4b4b;
}

.board {
  top: $top-margin;
  left: $left-margin;
  background-color: white;
  opacity: 100%;
  width: $display-width;
  height: $board-height;
  border-radius: 10vh;
  padding: 0vh 2vw 0vh 2vw;
  position: absolute;
  z-index: 1;
}

.board-header {
  top: -1/2 * $header-height;
  width: $display-width;
  height: $header-height;
  position: absolute;
  z-index: 3;
  // background-color :red;
  // opacity:50%;
  .board-header-kid {
    float: left;
    // margin:10px;
    display: inline-block;
    // background:yellow;
    width: 60%;
    .card-carousel-wrapper {
      display: flex;
      align-items: center;
      justify-content: center;
      .card-carousel {
        position: relative;
        // display: flex;
        justify-content: center;
        width: 85%;

        &--overflow-container {
          overflow: hidden;
        }

        &--nav__left,
        &--nav__right {
          display: inline-block;
          width: 15px;
          height: 15px;
          padding: 5px;
          box-sizing: border-box;
          border-top: 7px solid $arrowcolor;
          border-right: 7px solid $arrowcolor;
          opacity: 50%;
          border-radius: 4px;
          cursor: pointer;
          margin: 0 10px;
          transition: transform 150ms linear;
          &[disabled] {
            opacity: 0;
          }
        }

        &--nav__left {
          position: relative;
          // top: 3vh;
          transform: rotate(-135deg);
          &:active {
            transform: rotate(-135deg) scale(0.9);
          }
        }

        &--nav__right {
          position: relative;
          // top: 10vh;
          transform: rotate(45deg);
          &:active {
            transform: rotate(45deg) scale(0.9);
          }
        }
      }
      .card-carousel-cards {
        display: flex;
        transition: transform 150ms ease-out;
        transform: translatex(0px);
        position: relative;
        margin-right: 10vw;
        margin-left: 10vw;
        white-space: nowrap;
        // width: 40vw;
        .card-carousel--card {
          margin: 0 10px;
          display: inline-block;
          position: relative;
          .dataname {
            display: inline-block;
            padding: 10px 0px;
            position: relative;
            text-align: center;
            vertical-align: middle;
            font-size: $font-size;
            // font-size: auto;
            width: 100%;
            z-index: 2;
          }
          .selected {
            height: 30%;
            border-bottom: 5px solid #ffe26d;
            z-index: 1;
          }
          .deletemark {
            display: inline-block;
            position: relative;
            font-size: $font-size;
            color: lightgray;
          }
          cursor: pointer;
          z-index: 3;
          &:first-child {
            margin-left: -10vw;
          }
          &:last-child {
            margin-right: 0;
          }
          img {
            position: relative;
            width: $icon-size;
            vertical-align: middle;
            transition: opacity 150ms linear;
            user-select: none;

            &:hover {
              opacity: 0.5;
            }
          }
        }
      }
    }
  }
  .board-header-parent {
    display: inline-block;
    width: 30%;
    margin-top: 0.5vh;
    // float:right;
    .add-kid {
      margin-right: 10%;
      display: inline-block;
      width: $icon-size * 0.9;
      font-size: $font-size * 0.9;
      z-index: 2;
      img {
        width: $icon-size;
      }
    }
    .parent {
      display: inline-block;
      width: $icon-size * 0.9;
      font-size: $font-size * 0.9;
      z-index: 2;
      img {
        width: $icon-size;
      }
    }
  }
}

.board-body {
  position: relative;
  margin-top: $header-height/3 * 2;
}

.category {
  height: 40vh;
  font-size: 3vh;
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
  position: display;
  height: 60vh;
  padding: 1vw;
  margin-left: 5vw;
}

.activect {
  background-color: #ffe26d;
}

.backtomain {
  position: absolute;
  display: block;
  bottom: 0px;
  left: 0.1vw;
  height: 45vh;
  width: auto;
  // img{
  //   // margin-bottom: -1vh;
  //   width:100%;
  // }
  // transform: translate(-50%, -50%);
  .backtomain-txt {
    width: 2.5vh * 4;
    // height:auto;
    font-size: $font-size;
    position: absolute;
    display: block;
    bottom: 11vh;
    // margin:auto;
    right: 8vh;
    // right:0.1vw;
    color: black;
    // transform: translate(0, -50%);
  }
}

.backtomain-btn {
  width: 17vh;
  height: 5vh;
  background-color: transparent;
  bottom: 21vh;
  left: 1.5vh;
  z-index: 5;
  position: absolute;
}

.custom-btn {
  width: 17vh;
  height: 5vh;
  background-color: transparent;
  bottom: 28vh;
  left: 3vh;
  z-index: 5;
  position: absolute;
}
</style>
