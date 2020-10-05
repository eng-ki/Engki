<template>
  <div class="background">
    <div class="box">
      <div class="card-carousel-wrapper">
        <div class="title-with-tiki">
          <span class="showbox">{{ msg }} </span>
        </div>
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
                v-for="(data, index) in datas"
                v-bind:key="index"
                @click="returnID(index)"
              >
                <img class="dataimage" :src="imgpath + data.icon" />
                <div class="dataname">{{ data.name }}</div>
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

      <!-- <img
        src="../../public/img/icon/jungle_toucan.png"
        style="width: 150px; position: absolute; bottom: 10vh; right: 10vw"
      /> -->
      <img
        v-if="option != 'quiz'"
        src="../../public/img/icon/jungle_toucan.png"
        style="height: 20vh; position: absolute; bottom: 10vh; right: 10vw"
        @click="gotoparent"
      />
      <img
        v-if="option == 'quiz'"
        src="../../public/img/icon/kid_logout.png"
        style="height: 20vh; position: absolute; bottom: 10vh; right: 10vw"
        @click="gotoselectkid"
      />
    </div>
  </div>
</template>
<script>
import http from '../utils/http-common.js'
export default {
  props: {
    option: String,
  },
  created() {
    if (this.option == 'quiz') {
      this.msg = '주제를 선택해주세요'
      this.path = '/edu'
    } else if (this.option == 'kid') {
      this.$store.commit('setKid', null)
      console.log('초기화하고나서 스토어 kid값 조회 : ' + this.$store.state.kid)
      this.msg = '자신의 캐릭터를 선택해주세요'
      this.path = '/parents/' + this.$store.state.parent.id + '/kids'
      this.imgpath = ''
    }
    console.log('path : ' + this.path)
    http
      .get(this.path, {
        headers: { 'X-AUTH-TOKEN': this.$store.state.token },
      })
      .then(({ data }) => {
        this.datas = data
        console.log(data)
      })
    console.log('한 캐러셀에 나타나는 개수 :' + this.windowSize)
  },
  computed: {
    atEndOfList() {
      return (
        this.currentOffset <=
        this.paginationFactor * -1 * (this.datas.length - this.windowSize)
      )
    },
    atHeadOfList() {
      return this.currentOffset === 0
    },
  },
  data: function () {
    return {
      path: '',
      imgpath: '/img/icon/',
      msg: '',
      datas: [],
      currentOffset: 0,
      windowSize: ($(window).width() * 0.7) / ($(window).height() * 0.31), // carousel에 띄워줄 아이콘 갯수! <- 반응형으로 할거면 화면에 몇개 나오는지 계산해서 여기 넣어야 공백 안생길듯
      paginationFactor: $(window).height() * 0.31,
    }
  },
  methods: {
    returnID(index) {
      if (this.option == 'quiz') {
        if (this.datas[index].id == 6) {
          this.$swal({
            title:
              '<div style="font-family: GmarketSansMedium;font-size:1vw;">서비스 준비중입니다</div>',
            type: 'warning',
            showCancelButton: false,
            confirmButtonText: '확인',
            showLoaderOnConfirm: true,
          }).then((result) => {})
        } else if (this.datas[index].id == 7) {
          // person 퀴즈가 제대로 안받아와짐..
          // 일단 1~5로 지정해둠
          var num = (Math.round(Math.random() * 100) % 5) + 1
          console.log('랜덤주제 : ' + num)
          this.$store.commit('setQuiz', num)
          this.$router.push('/quiz')
          // 랜덤주제
        } else {
          this.$store.commit('setQuiz', this.datas[index].id)
          console.log(
            '버튼 클릭후 스토어 quiz값 조회 : ' + this.$store.state.quiz
          )
          this.$router.push('/quiz')
        }
      } else if (this.option == 'kid') {
        this.$store.commit('setKid', this.datas[index])
        // console.log(
        //   '버튼 클릭후 스토어 kid값 조회 : ' + this.$store.state.kid.name
        // );
        this.$router.push('/kid')
      }
    },
    moveCarousel(direction) {
      if (direction === 1 && !this.atEndOfList) {
        this.currentOffset -= this.paginationFactor
      } else if (direction === -1 && !this.atHeadOfList) {
        this.currentOffset += this.paginationFactor
      }
    },
    gotoparent() {
      this.$router.push('/parent')
    },
    gotoselectkid() {
      this.$router.push('/selectkid')
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.background .box {
  background-color: rgba(255, 255, 255, 0.6);
  border-radius: 5vh;
  margin: 10vh 6vw 10vh 6vw;
  height: 80vh;
}

/* 로그인페이지 input박스 + tiki */
.title-with-tiki {
  /* 좌표 설정 */
  position: absolute;
  // top: 20vh;
  left: 50%;
  width: 100%;
  text-align: center;
  transform: translate(-50%, -50%);
  padding: 50px;
}

$arrowcolor: #ffffff;
.card-carousel-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50vh;
}

.card-carousel {
  position: relative;
  top: 20vh;
  display: flex;
  justify-content: center;
  width: 70vw;

  &--overflow-container {
    overflow: hidden;
  }

  &--nav__left,
  &--nav__right {
    display: inline-block;
    width: 30px;
    height: 30px;
    padding: 20px;
    box-sizing: border-box;
    border-top: 20px solid $arrowcolor;
    border-right: 20px solid $arrowcolor;
    border-radius: 8px;
    cursor: pointer;
    margin: 0 50px;
    transition: transform 150ms linear;
    &[disabled] {
      opacity: 0.3;
      border-color: white;
    }
  }

  &--nav__left {
    position: relative;
    top: 20vh;
    transform: rotate(-135deg);
    &:active {
      transform: rotate(-135deg) scale(0.9);
    }
  }

  &--nav__right {
    position: relative;
    top: 20vh;
    transform: rotate(45deg);
    &:active {
      transform: rotate(45deg) scale(0.9);
    }
  }
}

.card-carousel-cards {
  position: relative;
  margin-right: 10vw;
  margin-left: 10vw;
  display: flex;
  transition: transform 150ms ease-out;
  transform: translatex(0px);
  width: 80vw;
  .card-carousel--card {
    display: inline-block;
    position: relative;
    margin: 0 1vw;
    cursor: pointer;
    // background-color: #fff;
    z-index: 3;
    &:first-child {
      margin-left: -10vw;
    }
    &:last-child {
      margin-right: 0;
    }
    img {
      position: relative;
      width: 28vh;
      vertical-align: middle;
      transition: opacity 150ms linear;
      user-select: none;

      &:hover {
        opacity: 0.5;
      }
    }
    white-space: nowrap;
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
    .dataname {
      padding: 10px 0px;
      position: relative;
      width: 28vh;
      text-align: center;
      vertical-align: middle;
      font-size: 5vh;
    }
  }
}
</style>
