<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <span class="page-title">이메일 정보 수집</span>
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
                <button
                  v-if="from == 'parent'"
                  @click="saveInfo()"
                  class="parents-button"
                >
                  수정하기
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
  </div>
</template>
<script>
import http from '../utils/http-common.js'
import jwt_decode from 'jwt-decode'
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
    }
  },
  created() {
    var parent_id = jwt_decode(this.$store.state.token).sub
    http
      .get('parents/' + parent_id, {
        headers: { 'X-AUTH-TOKEN': this.$store.state.token },
      })
      .then(({ data }) => {
        this.parents = data
      })
  },
  methods: {
    saveInfo() {
      if (this.from == null) {
        this.$swal({
          title:
            '<div style="font-family: GmarketSansMedium;font-size:2vw;">이메일을 등록하시겠습니까?</div>',
          type: 'warning',
          showCancelButton: true,
          confirmButtonText: '확인',
          cancelButtonText: '취소',
          showCloseButton: true,
          showLoaderOnConfirm: true,
        }).then((result) => {
          if (result.value) {
            http
              .put(
                'parents/' + this.$store.state.user.id,
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
                this.$router.push('/parent')
              })
              .catch((err) => {
                console.error(err)
              })
          }
        })
      } else if (this.from == 'parent') {
        this.$swal({
          title:
            '<div style="font-family: GmarketSansMedium;font-size:2vw;">이메일을 수정하시겠습니까?</div>',
          type: 'warning',
          showCancelButton: true,
          confirmButtonText: '확인',
          cancelButtonText: '취소',
          showCloseButton: true,
          showLoaderOnConfirm: true,
        }).then((result) => {
          if (result.value) {
            http
              .put(
                'parents/' + this.$store.state.user.id,
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
                this.$emit('visible')
              })
          }
        })
      }
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
.page-title {
  // text-align: left;
  font-size: 8vh;
}

/* 회원가입 내용 */
.page-text {
  text-align: left;
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

.parent-info-page .parents-button {
  /* 좌표 설정 */
  position: absolute;
  top: 60vh;
  left: 50%;
  transform: translate(-50%, -100%);
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
</style>
