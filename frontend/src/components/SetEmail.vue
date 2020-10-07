<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <span v-if="from == 'parent'" class="page-title"
          >내 정보

          <img
            @click="startTutorial()"
            class="page-title-img"
            id="tutorial"
            src="../../public/img/icon/question-mark1.png"
          />
        </span>
        <span v-else class="page-title"
          >이메일 정보 수집
          <img
            @click="startTutorial()"
            class="page-title-img"
            id="tutorial"
            src="../../public/img/icon/question-mark1.png"
          />
        </span>
        <button v-if="from == 'parent'" @click="deleteInfo()" class="delete">
          탈퇴하기
        </button>
        <div class="parent-info-page">
          <div class="container page-text">
            <div class="row">
              <div class="col-sm-5 title">
                <p>이름</p>
              </div>
              <div class="col-sm-7">
                <input class="info-inputbox" v-model="parents.name" />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-5">
                <p>이메일</p>
              </div>
              <div class="col-sm-7">
                <input class="info-inputbox" v-model="parents.email" />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-5">
                <p>학습 보고서 수신</p>
              </div>
              <div class="col-sm-7">
                <div class="neumorphism-toggle">
                  <input
                    class="info-inputbox"
                    type="checkbox"
                    id="neumorphism"
                    checked
                    v-model="parents.receiveEmailFlag"
                  />

                  <label for="neumorphism">
                    <div class="switch">
                      <div class="dot"></div>
                    </div>
                  </label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-*">
                <!-- style="width: 100%; height: 20px; background-color: yellow" -->
                <!-- <button class="parents-button" @click="saveInfo()">
                  수정하기
                </button>
                <button
                  class="parents-button return-button"
                  @click="returnParentPage()"
                >
                  돌아가기
                </button> -->
                <button
                  v-if="from == 'parent'"
                  @click="saveInfo()"
                  class="parents-button edit"
                >
                  수정하기
                </button>
                <button
                  v-if="from == 'parent'"
                  @click="returnParentPage()"
                  class="parents-button back"
                >
                  뒤로가기
                </button>
                <button v-else @click="saveInfo()" class="parents-button">
                  가입하기
                </button>
              </div>
            </div>
          </div>
        </div>
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
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>내 정보</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
    >
      <div class="modal-body">
        <span>
          부모님의 이름, 이메일 정보를 이곳에서 수정할 수 있습니다.<br />
          또한 자녀의 학습 보고서 수신 여부를 수정할 수 있습니다.
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

    <!-- 두번째 튜토리얼 -->
    <b-modal
      ref="my-modal2"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>탈퇴하기</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
    >
      <div class="modal-body">
        <span>
          우측 하단의 탈퇴 버튼을 누르면 회원 정보가 영구 삭제됩니다.<br />
          모든 자녀의 데이터도 함께 삭제되니 신중히 결정해주세요.<br />
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

    <!-- 처음 가입했을 때 첫번째 튜토리얼 -->
    <b-modal
      modal-class="mymodal"
      ref="first-modal"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>이메일 정보 수집</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
    >
      <div class="modal-body">
        <span>
          부모님의 이름, 이메일 정보, 이메일 수신 여부를 선택한 뒤<br />
          가입하기 버튼을 누르면 서비스를 이용할 수 있습니다.<br />
          부모님의 정보는 이후 언제든지 수정할 수 있습니다.
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
  </div>
</template>
<script>
import http from '../utils/http-common.js';
import jwt_decode from 'jwt-decode';
export default {
  props: {
    from: null,
  },
  data: function () {
    return {
      parents: {
        name: '',
        email: '',
        receiveEmailFlag: true,
      },
      stage: 1,
      limit: this.$store.state.isNew ? 1 : 2,
      isHideFooter: true,
      mymodal: ['mymodal'],
    };
  },
  created() {
    var parent_id = jwt_decode(this.$store.state.token).sub;
    http
      .get('parents/' + parent_id, {
        headers: { 'X-AUTH-TOKEN': this.$store.state.token },
      })
      .then(({ data }) => {
        this.parents = data;
      });
  },
  methods: {
    prevTutorial(stage) {
      if (stage > 1) {
        if (this.from == null) {
          this.$refs['first-modal'].hide();
        } else {
          this.$refs['my-modal' + stage].hide();
        }
        this.stage--;

        this.showTutorial(this.stage);
      }
    },
    nextTutorial(stage) {
      if (this.from == null) {
        this.$refs['first-modal'].hide();
      } else {
        this.$refs['my-modal' + stage].hide();
      }
      if (stage < this.limit) {
        this.stage++;
        this.showTutorial(this.stage);
      }
    },
    showTutorial(index) {
      if (this.from == null) {
        this.$refs['first-modal'].show();
      } else this.$refs['my-modal' + index].show();
    },
    startTutorial() {
      this.stage = 1;
      if (this.from == null) {
        this.$refs['first-modal'].show();
      } else this.$refs['my-modal1'].show();
    },
    deleteInfo() {
      this.$swal({
        title:
          '<div style="font-family: GmarketSansMedium;font-size:2vw;">탈퇴하시겠습니까?<br><span style="font-family: GmarketSansMedium;font-size:1vw;">모든 데이터가 영구적으로 삭제됩니다.</span></div>',
        showCancelButton: true,
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        showCloseButton: true,
      }).then((result) => {
        if (result.value) {
          http
            .delete('parents/' + this.$store.state.parent.id, {
              headers: { 'X-AUTH-TOKEN': this.$store.state.token },
            })
            .then(({ data }) => {
              this.$store.commit('setParent', null);
              this.$store.commit('setToken', null);
              this.$router.push('/');
            });
        }
      });
    },
    saveInfo() {
      if (this.from == null) {
        this.$swal({
          title:
            '<div style="font-family: GmarketSansMedium;font-size:2vw;">이메일을 등록하시겠습니까?</div>',
          showCancelButton: true,
          confirmButtonText: '확인',
          cancelButtonText: '취소',
          showCloseButton: true,
        }).then((result) => {
          if (result.value) {
            http
              .put(
                'parents/' + this.$store.state.parent.id,
                {
                  email: this.parents.email,
                  name: this.parents.name,
                  receiveEmailFlag: this.parents.receiveEmailFlag,
                },
                {
                  headers: { 'X-AUTH-TOKEN': this.$store.state.token },
                }
              )
              .then(({ data }) => {
                this.$router.push('/parent');
              })
              .catch((err) => {
                console.error(err);
              });
          }
        });
      } else if (this.from == 'parent') {
        this.$swal({
          title:
            '<div style="font-family: GmarketSansMedium;font-size:2vw;">이메일을 수정하시겠습니까?</div>',
          showCancelButton: true,
          confirmButtonText: '확인',
          cancelButtonText: '취소',
          showCloseButton: true,
        }).then((result) => {
          if (result.value) {
            http
              .put(
                'parents/' + this.$store.state.parent.id,
                {
                  email: this.parents.email,
                  name: this.parents.name,
                  receiveEmailFlag: this.parents.receiveEmailFlag,
                },
                {
                  headers: { 'X-AUTH-TOKEN': this.$store.state.token },
                }
              )
              .then(({ data }) => {
                this.$emit('visible');
              });
          }
        });
      }
    },
    returnParentPage() {
      this.$emit('returnParentPage');
    },
  },
};
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
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

.delete {
  position: absolute;
  bottom: 2vw;
  right: 3vw;
  font-family: GmarketSansMedium;
}
.parents-button {
  /* 좌표 설정 */
  position: absolute;
  padding-left: 1vh;
  padding-right: 1vh;
  top: 55vh;
  left: 50%;
  width: 15vw;
  transform: translate(-50%, -50%);
}
.edit {
  // position: relative;
  left: 39%;
}
.back {
  // position: relative;
  left: 61%;
}

/* 회원가입 타이틀 */
.page-title {
  // text-align: left;
  font-size: 8vh;
  top: 2vh;
  left: 4vw;
  position: relative;
  font-family: GmarketSansMedium;
  color: black;
}

/* 회원가입 내용 */
.innerbox .page-text {
  color: black;
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

.neumorphism-toggle {
  padding-top: 1.8vh; //내가추가
  padding-bottom: 1.8vh; //내가추가
  position: relative;
  transition: transform 0.3s;
  transform: scale(var(--scale, 1)) translateZ(0);
  &:active {
    --scale: 0.96;
  }
  input {
    display: none;
    & + label {
      .switch {
        position: relative;
        display: inline-block;
        z-index: 1;
        vertical-align: top;
        // height: 22px;
        height: 5vh;
        // width: 40px;
        width: 9vh;
        // border-radius: 11px;
        border-radius: 2.5vh;
        background: #eceffc;
        &:before {
          content: '';
          position: absolute;
          left: 0;
          top: 0;
          right: 0;
          bottom: 0;
          border-radius: inherit;
          background: linear-gradient(90deg, #00a123, #027a1c);
          opacity: var(--gradient, 0);
          transition: opacity 0.4s;
        }
        .dot {
          background: #d1d6ee;
          position: absolute;
          width: 5vh;
          height: 5vh;
          // width: 24px;
          // height: 24px;
          border-radius: 50%;
          // left: -1px;
          // top: -1px;
          transform: translateX(var(--offset, 0));
          transition: transform 0.4s, box-shadow 0.4s;
          &:before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            border-radius: inherit;
            background: linear-gradient(160deg, #f1f4ff, #f9faff);
            opacity: var(--gradient, 0);
            transition: opacity 0.4s;
          }
        }
      }
    }
    &:checked {
      & + label {
        --offset: 4.2vh;
        --gradient: 1;
      }
    }
  }
}

.page-title .page-title-img {
  width: 4%;
  margin-bottom: 1vh;
  &:hover {
    opacity: 0.6;
  }
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
