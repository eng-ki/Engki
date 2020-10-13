<template>
  <div class="background">
    <div class="box">
      <img
            @click="startTutorial()"
            class="page-title-img"
            id="tutorial"
            src="../../public/img/icon/question-mark1.png"
          />
          <b-tooltip placement="bottom" target="tutorial" triggers="hover">
            <span
              style="
                font-family: GmarketSansMedium;
                color: #f2f2f2;
                font-size: 0.8vw;
              "
              >도움말</span
            >
          </b-tooltip>
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
                <div
                  v-if="(data.id != 7 && option == 'quiz') || option == 'kid'"
                >
                  <img class="dataimage" :src="imgpath + data.icon" />
                  <div class="dataname">{{ data.name }}</div>
                </div>
                <div v-if="option == 'kid'">
                  Level.{{ data.exp / 100 - (data.exp % 100) / 100 + 1 }}
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
        src="../../public/img/icon/jungle_my_page.png"
        style="height: 20vh; position: absolute; bottom: 10vh; right: 10vw"
        @click="gotomypage"
      />
    </div>
    <!-- selectkid 첫번째 튜토리얼 -->
    <b-modal
      :modal-class="selectmodal[0]"
      ref="kid-modal1"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>자녀 선택</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
    >
      <div class="modal-body">
        <span>
          학습할 자녀의 프로필을 선택하세요.<br />
          클릭시 해당 자녀의 마이 페이지로 이동합니다.<br />
        </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 첫번째 튜토리얼 끝-->

    <!-- selectkid 두번째 튜토리얼 -->
    <b-modal
      :modal-class="selectmodal[1]"
      ref="kid-modal2"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>부모 페이지</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
      ><div class="arrow-right"></div>
      <div class="modal-body">
        <span>
          이 표지판을 클릭하면 자녀의 정보 관리와<br />
          커스텀 학습이 가능한 부모 페이지로 이동할 수 있습니다.<br />
        </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 두번째 튜토리얼 끝-->

    <!-- selectquiz 첫번째 튜토리얼 -->
    <b-modal
      :modal-class="selectmodal[2]"
      ref="quiz-modal1"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>주제 선택</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
    >
      <div class="modal-body">
        <span>
          아이콘을 클릭해 퀴즈의 주제를 선택해보세요.<br />
          관심있는 주제에 관한 문제를 풀 수 있습니다.
        </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 첫번째 튜토리얼 끝-->

    <!-- selectquiz 두번째 튜토리얼 -->
    <b-modal
      :modal-class="selectmodal[3]"
      ref="quiz-modal2"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>마이 페이지</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
      ><div class="arrow-right"></div>
      <div class="modal-body">
        <span>
          이 표지판을 클릭하면 자녀의 마이 페이지로 이동합니다.<br />
          마이 페이지에서 캐릭터를 변경해보세요.<br />
        </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 두번째 튜토리얼 끝-->
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
      this.msg = '학습할 주제를 선택하세요'
      this.path = '/edu'
      this.isFromKid = 1
    } else if (this.option == 'kid') {
      this.$store.commit('setKid', null)
      // console.log('초기화하고나서 스토어 kid값 조회 : ' + this.$store.state.kid)
      this.msg = '학습을 시작할 프로필을 고르세요'
      this.path = '/parents/' + this.$store.state.parent.id + '/kids'
      this.imgpath = ''
      this.isFromKid = 0
    }
    // console.log('path : ' + this.path)
    http
      .get(this.path, {
        headers: { 'X-AUTH-TOKEN': this.$store.state.token },
      })
      .then(({ data }) => {
        this.datas = data
        // console.log(data)
      })
    // console.log('한 캐러셀에 나타나는 개수 :' + this.windowSize)
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
      stage: 1,
      limit: 2,
      isHideFooter: true,
      selectmodal: [
        'selectmodal1',
        'selectmodal2',
        'selectmodal3',
        'selectmodal4',
      ],
    }
  },
  methods: {
    prevTutorial(stage) {
      if (stage > 1) {
        this.$refs[this.option + '-modal' + stage].hide()
        this.stage--
        this.showTutorial(this.stage)
      }
    },
    nextTutorial(stage) {
      this.$refs[this.option + '-modal' + stage].hide()
      if (stage < this.limit) {
        this.stage++
        this.showTutorial(this.stage)
      }
    },
    showTutorial(index) {
      this.$refs[this.option + '-modal' + index].show()
    },
    startTutorial() {
      this.stage = 1
      this.$refs[this.option + '-modal1'].show()
    },
    returnID(index) {
      if (this.option == 'quiz') {
        if (this.datas[index].id == 7) {
          this.$swal({
            title:
              '<div style="font-family: GmarketSansMedium;font-size:1vw;">서비스 준비중입니다</div>',
            showCancelButton: false,
            confirmButtonText: '확인',
          }).then((result) => {})
        } else if (this.datas[index].id == 8) {
          // person 퀴즈가 제대로 안받아와짐..
          // 일단 1~5로 지정해둠
          var num = (Math.round(Math.random() * 100) % 6) + 1
          this.$store.commit('setTheme', num)
          // console.log('랜덤주제 : ' + this.$store.state.theme)
          this.$router.push('/quiz')
          // 랜덤주제
        } else {
          this.$store.commit('setTheme', this.datas[index].id)
          // console.log('일반주제 : ' + this.$store.state.theme)
          this.$router.push('/quiz')
        }
      } else if (this.option == 'kid') {
        this.$store.commit('setKid', this.datas[index])
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
    gotomypage() {
      this.$router.push('/kid')
    },
  },
}
</script>

<style>
.selectmodal2 > div {
  position: absolute !important;
  bottom: 3vh !important;
  right: 18vw !important;
}

.selectmodal4 > div {
  position: absolute !important;
  bottom: 3vh !important;
  right: 18vw !important;
}
</style>

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

.page-title-img {
  // width: 4.9%;
  position: absolute;
  margin:1vh;
  padding:1vh;
  right:6vw;
  top:10vh;
  height: 4vw;
  // 10vh 6vw 10vh 6vw
  // margin-top: 2.5vh;
  // margin-left: 2.5vw;
  // transform: translate(0, -50%);
  // margin-bottom: 1vh;
  &:hover {
    opacity: 0.6;
  }
  z-index: 1000;
}

.isButtonBlock {
  opacity: 0.6;
  pointer-events: none;
}
.modal-body {
  margin-top: -2vw;
  margin-bottom: 1vw;
}

.modal-body span {
  color: #263747;
  opacity: 0.9;
  font-family: GmarketSansMedium;
}
.modal-foot {
  float: left;
  color: gray;
  padding-left: 1vw;
  font-family: GmarketSansMedium;
}
.modal-foot2 {
  float: right;
  font-family: GmarketSansMedium;
}
.mymodal > div {
  position: fixed !important;
  top: 0 !important;
  left: -23vw !important;
}
</style>
