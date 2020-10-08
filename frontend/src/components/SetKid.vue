<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <span class="page-title"
          ><span id="tutorial-1" style="font-family: GmarketSansMedium"
            >자녀 등록</span
          ><img
            @click="startTutorial()"
            class="page-title-img"
            id="tutorial"
            src="../../public/img/icon/question-mark1.png"
        /></span>
        <div class="parent-info-page">
          <div class="container page-text">
            <div class="row">
              <div class="col-sm-4 title">
                <p>이름</p>
              </div>
              <div class="col-sm-8 sub">
                <input class="info-inputbox" v-model="kid.name" />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-4">
                <p>생일</p>
              </div>
              <div class="col-sm-8">
                <input
                  class="info-inputbox"
                  placeholder="ex) 2010-01-01"
                  v-model="kid.birthday"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-*">
        <!-- style="width: 100%; height: 20px; background-color: yellow" -->
        <button class="parents-button" @click="register()">등록하기</button>
        <button
          class="parents-button return-button"
          @click="returnParentPage()"
        >
          돌아가기
        </button>
      </div>
    </div>
    <b-tooltip placement="right" target="tutorial" triggers="hover">
      <span
        style="font-family: GmarketSansMedium; color: #f2f2f2; font-size: 0.8vw"
        >도움말</span
      >
    </b-tooltip>

    <!-- 첫번째 튜토리얼 -->
    <b-modal
      modal-class="mymodal"
      ref="my-modal1"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>자녀 등록</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
    >
      <div class="modal-body">
        <span>
          자녀는 한 명 이상 필수로 등록해야 합니다.<br />
          서비스를 이용할 자녀의 이름과 생년월일을 입력해주세요.
        </span>
      </div>
      <div>
        <div style="float: left; color: gray; padding-left: 1vw">
          {{ stage }}/2
        </div>
        <div style="float: right">
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

    <!-- 두번째 튜토리얼 -->
    <b-modal
      ref="my-modal2"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>등록하기</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
    >
      <div class="modal-body">
        <span>
          등록한 아이로 학습을 시작할 수 있습니다.<br />
          부모페이지에서 아이의 학습 정보를 관리해보세요.
        </span>
      </div>
      <div>
        <div style="float: left; color: gray; padding-left: 1vw">
          {{ stage }}/2
        </div>
        <div style="float: right">
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
  </div>
</template>
<script>
import http from '../utils/http-common.js';
export default {
  props: {
    from: null,
  },
  data: function () {
    return {
      kid: {
        name: '',
        birthday: '',
        icon: '/img/icon/fairytale/001-knight.png',
      },
      stage: 1,
      limit: 2,
      isHideFooter: true,
      mymodal: ['mymodal'],
    };
  },
  methods: {
    prevTutorial(stage) {
      if (stage > 1) {
        this.$refs['my-modal' + stage].hide();
        this.stage--;
        this.showTutorial(this.stage);
      }
    },
    nextTutorial(stage) {
      this.$refs['my-modal' + stage].hide();
      if (stage < this.limit) {
        this.stage++;
        this.showTutorial(this.stage);
      }
    },
    showTutorial(index) {
      this.$refs['my-modal' + index].show();
    },
    startTutorial() {
      this.stage = 1;
      this.$refs['my-modal1'].show();
    },

    register() {
      this.$swal({
        title:
          '<div style="font-family: GmarketSansMedium;font-size:2vw;">자녀를 등록하시겠습니까?</div>',
        showCancelButton: true,
        confirmButtonText: '등록',
        cancelButtonText: '취소',
        showCloseButton: true,
      }).then((result) => {
        if (result.value) {
          if (this.kid.name.length == 0) {
            this.$swal({
              title:
                '<div style="font-family: GmarketSansMedium;font-size:1vw;">이름을 입력해주세요</div>',
              showCancelButton: false,
              confirmButtonText: '확인',
            }).then((result) => {});
          } else if (!this.isValidDate(this.kid.birthday)) {
            this.$swal({
              title:
                '<div style="font-family: GmarketSansMedium;font-size:1vw;">생일을 형식에 맞게 입력해주세요</div>',
              showCancelButton: false,
              confirmButtonText: '확인',
            }).then((result) => {});
          } else {
            http
              .post(
                'kids/',
                {
                  birthday: this.kid.birthday,
                  icon: this.kid.icon,
                  name: this.kid.name,
                  parentId: this.$store.state.parent.id,
                },
                {
                  headers: { 'X-AUTH-TOKEN': this.$store.state.token },
                }
              )
              .then(({ data }) => {
                this.$emit('update', this.kid);
              });
          }
        }
      });
    },
    returnParentPage() {
      this.$emit('returnParentPage');
    },
    isValidDate(dateString) {
      var regEx = /^\d{4}-\d{2}-\d{2}$/;
      if (!dateString.match(regEx)) return false; // Invalid format
      var d = new Date(dateString);
      var dNum = d.getTime();
      if (!dNum && dNum !== 0) return false; // NaN value, Invalid date
      return d.toISOString().slice(0, 10) === dateString;
    },
  },
};
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
* {
  color: black;
  font-family: GmarketSansMedium;
}
.background .box {
  background-color: rgba(255, 255, 255, 0.6);
  border-radius: 5vh;
}

/* 회원가입 페이지 틀 */
.background .box {
  margin: 10vh 6vw 10vh 6vw;
}

/* 회원가입 페이지 틀 */
.background .box .innerbox {
  height: 80vh;
  width: 88vw;
  padding: 4vh;
  text-align: left;
  position: relative;
}

/* 회원가입 페이지 틀 */
.background .box .innerbox .parent-info-page {
  position: inherit;
}

/* 회원가입 타이틀 */
.innerbox .page-title {
  // text-align: left;
  font-size: 8vh;
  top: 2vh;
  left: 4vw;
  position: relative;
  font-family: GmarketSansMedium;
}

.page-title .page-title-img {
  width: 4%;
  margin-left: 1vw;
  margin-bottom: 1vh;
  &:hover {
    opacity: 0.6;
  }
}
/* 회원가입 내용 */
.innerbox .page-text {
  text-align: left;
  margin-top: 3vh;
  margin-left: 3vw;
}

.row {
  margin-top: 2vh;
}

.page-text p {
  vertical-align: middle;
  font-size: 5vh;
  margin: 0px;
  font-family: GmarketSansMedium;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5);
}

.parents-button {
  /* 좌표 설정 */
  position: absolute;
  padding-left: 1vh;
  padding-right: 1vh;
  top: 80%;
  left: 39%;
  width: 15vw;
  transform: translate(-50%, -50%);
}
.return-button {
  left: 61%;
}

// .parents-button {
//   /* 좌표 설정 */
//   position: absolute;
//   top: 80%;
//   left: 39vw;
//   width: 20vw;
//   transform: translate(-50%, -50%);
// }
// .return-button{
//   left: 61vw;
// }
.gender-button {
  margin-right: 1vw;
  width: 10vw;
  border-radius: 3vh;
  background-color: rgba(255, 255, 255, 0.5);
  border: transparent;

  /* input 내 폰트 좌표 */
  /* 글자 아래 약간 여백이 있어서 완전 중앙에 놓으려면 위에 패딩 살짝 넣어줘야함 */
  padding-top: 1%;

  /* 폰트 디자인 */
  color: black;
  font-family: GmarketSansMedium;
  // padding-left: 3vw;

  text-align: center;
  vertical-align: middle;
  font-size: 4.5vh;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
}
.active {
  background-color: #ff9a00;
}

.tutorial {
  background-color: #f2f2f2;
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
}

.mymodal > div {
  position: fixed !important;
  top: 0 !important;
  left: -23vw !important;
}
</style>
