<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <span class="page-title">이메일 정보 수집</span>
        <div class="parent-info-page">
          <div class="container page-text">
            <div class="row">
              <div class="col-sm-4 title">
                <p>이름</p>
              </div>
              <div class="col-sm-8 sub">
                <input class="info-inputbox" v-model="parents.name" />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-4">
                <p>이메일</p>
              </div>
              <div class="col-sm-8">
                <input class="info-inputbox" v-model="parents.email" />
              </div>
            </div>
            <div class="col-sm-*">
              <div v-if="from == 'parent'">
                <button @click="$emit('visible')" class="parents-button">
                  수정하기
                </button>
              </div>
              <button v-else class="parents-button">가입하기</button>
            </div>
          </div>
        </div>
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
      parents: {
        name: "손명지",
        bday: "1994-04-03",
        email: "ji_exitos@naver.com",
        pwd: "1234567",
        pwd_re: "1234567",
      },
    };
  },
};
</script>
<style lang="scss">
@import "../assets/sass/base.scss";
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
  top: 95%;
  left: 50%;
  transform: translate(-50%, -100%);
}
</style>
