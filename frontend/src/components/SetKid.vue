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
            <div class="row">
              <div class="col-sm-4">
                <p>성별</p>
              </div>
              <div class="col-sm-8">
                <button
                  class="gender-button"
                  @click="kid.gender = 'female'"
                  :class="{ active: kid.gender == 'female' }"
                >
                  여자
                </button>
                <button
                  class="gender-button"
                  @click="kid.gender = 'male'"
                  :class="{ active: kid.gender == 'male' }"
                >
                  남자
                </button>
                <!-- <v-btn-toggle
                    v-model="gender"
                    borderless>
          <v-btn value="female">여자 </v-btn>
          <v-btn value="male">남자</v-btn>
                 </v-btn-toggle> -->
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-*">
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
export default {
  props: {
    from: null,
  },
  data: function () {
    return {
      kid: {
        name: '손명지',
        birthday: '1994-04-03',
        gender: 'female',
        url: '/img/icon/fairytale/001-knight.png',
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
          // 백엔드 자녀 등록 api

          // 등록 완료시 parentpage로 데이터 넘겨줌
          this.$emit('update', this.kid)
        }
      })
    },
    returnParentPage() {
      this.$emit('returnParentPage')
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
  top: 80%;
  left: 39vw;
  width: 20vw;
  transform: translate(-50%, -50%);
}
.return-button{
  left: 61vw;
}
.gender-button {
  margin-right:1vw;
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
