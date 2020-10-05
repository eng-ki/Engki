<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <div class="login">
          <p class="title">잉키</p>
          <p class="subtitle">English for kids</p>
          <div class="input-with-tiki">
            <img
              class="moving-tiki"
              src="../../public//img/icon/moving_tiki.gif"
            />
            <!-- <button class="kakao_btn font-weight-bold"  혹시 이런식으로 class에 kakao_btm 한번 넣어보실래요? -->
            <button class="inputbox kakao_btn" @click="kakaoLogin()">
              <img src="../../public/img/kakao.png" class="kakao_img" />
              <span class="kakao_font">카카오 로그인</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import http from '../utils/http-common.js'
export default {
  // props: {
  //   isNew: false,
  // },
  data: function () {
    return {
      // submsg1: '아이디가 생각나지 않으세요?',
    }
  },
  computed: {
    getToken() {
      return this.$store.getters.getToken
    },
  },
  watch: {
    getToken(val, oldVal) {
      if (this.$store.state.isNew) this.$emit('child', true)
      else {
        http
          .get('parents/' + this.$store.state.parent.id + '/kids', {
            headers: { 'X-AUTH-TOKEN': this.$store.state.token },
          })
          .then(({ data }) => {
            if (data.length == 0) this.$router.push('/parent')
            else this.$router.push('/selectkid')
          })
      }
    },
  },
  methods: {
    kakaoLogin() {
      Kakao.Auth.login({
        success: this.kakaoLoginStore,
      })
    },
    kakaoLoginStore(authObj) {
      this.$store.dispatch('kakaoLogin', {
        access_token: authObj.access_token,
      })
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
/* 로그인페이지 틀 */
.background .box .innerbox .login {
  position: inherit;
}

/* 로그인페이지 제목 - 잉키 */
.login .title {
  /* 좌표 설정 */
  position: absolute;
  top: 38vh;
  left: 50%;
  transform: translate(-50%, -50%);
  /* text-align: center; */

  /* 디자인 */
  font-size: 35vh;
  color: #ff9a00;
  text-shadow: 15px 15px 15px rgba(0, 0, 0, 0.65);
}

/* 로그인페이지 부제목 - english for kids */
.login .subtitle {
  /* 좌표 설정 */
  position: absolute;
  top: 55vh;
  left: 50%;
  transform: translate(-50%, -50%);

  /* 디자인 */
  font-size: 8vh;
  color: rgba(255, 255, 255, 0.919);
  text-shadow: 15px 15px 15px rgba(0, 0, 0, 0.65);
}

/* 로그인페이지 input박스 + tiki */
.login .input-with-tiki {
  /* 좌표 설정 */
  position: absolute;
  top: 76vh;
  left: 50%;
  width: 100%;
  text-align: center;
  transform: translate(-50%, -50%);
  margin-left: -5vh;
}

/* 로그인페이지 tiki*/
.login .moving-tiki {
  position: relative;
  transform: translate(100%, -50%);
  width: 10vh;
}

.kakao_btn {
  color: #333;
  background-color: #ffe500;
  font-size: 4vh;
  line-height: 10vh;
  // vertical-align: middle;
  border-radius: 5px;
}
.kakao_font {
  margin-top: 0.4vh;
  left: 50%;
  margin-left: -6vh;
  position: absolute;
  font-family: GmarketSansMedium;

  // transform: translate(-50%, -100%);
}
.kakao_img {
  float: left;
  position: inherit;
  height: 6vh;
  margin: 2.4vh;
  // width: 10%;
  // margin-top: auto;
}
</style>
