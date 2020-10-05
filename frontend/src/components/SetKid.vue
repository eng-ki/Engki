<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <span class="page-title">자녀 등록</span>
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
  </div>
</template>
<script>
import http from '../utils/http-common.js'
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
    }
  },
  methods: {
    register() {
      this.$swal({
        title:
          '<div style="font-family: GmarketSansMedium;font-size:2vw;">자녀를 등록하시겠습니까?</div>',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: '등록',
        cancelButtonText: '취소',
        showCloseButton: true,
        showLoaderOnConfirm: true,
      }).then((result) => {
        if (result.value) {
          if (this.kid.name.length == 0) {
            alert('이름을 입력해주세요.')
          } else if (!this.isValidDate(this.kid.birthday)) {
            alert('메일을 형식에 맞게 입력해주세요.')
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
                this.$emit('update', this.kid)
              })
          }
        }
      })
    },
    returnParentPage() {
      this.$emit('returnParentPage')
    },
    isDateFormat(d) {
      var df = /[0-9]{4}-[0-9]{2}-[0-9]{2}/
      return d.match(df)
    },
    isLeaf(year) {
      var leaf = false

      if (year % 4 == 0) {
        leaf = true

        if (year % 100 == 0) {
          leaf = false
        }

        if (year % 400 == 0) {
          leaf = true
        }
      }

      return leaf
    },
    isValidDate(d) {
      // 포맷에 안맞으면 false리턴
      if (!this.isDateFormat(d)) {
        return false
      }

      var month_day = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

      var dateToken = d.split('-')
      var year = Number(dateToken[0])
      var month = Number(dateToken[1])
      var day = Number(dateToken[2])

      // 날짜가 0이면 false
      if (day == 0) {
        return false
      }

      var isValid = false

      // 윤년일때
      if (this.isLeaf(year)) {
        if (month == 2) {
          if (day <= month_day[month - 1] + 1) {
            isValid = true
          }
        } else {
          if (day <= month_day[month - 1]) {
            isValid = true
          }
        }
      } else {
        if (day <= month_day[month - 1]) {
          isValid = true
        }
      }

      return isValid
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
* {
  color: black;
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

// .return-button {
//   margin-left: 18vw;
// }
</style>
