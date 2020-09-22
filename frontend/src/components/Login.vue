<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <div class="loginpage">
          <p class="title">잉키</p>
          <p class="subtitle">English for kids</p>
          <div class="input-with-tiki">
            <img class="moving-tiki" src="../images/icon/moving_tiki.gif" />
            <input class="inputbox" type="text" :placeholder="placeholder" />
          </div>
          <button class="parents-button">시작하기</button>
          <div class="login-message-box">
            <span v-on:click="submsg_clicked(1)" class="login-message" v-text="submsg1"></span>
            <br />
            <span v-on:click="submsg_clicked(2)" class="login-message" v-text="submsg2"></span>
          </div>

          <!-- 1단계: placeholder=이메일을 입력해주세요

            if email 정보 없으면,
            placeholder=이메일을 다시 입력해주세요. <- 주황글씨
            시작버튼 하단에 
            '아이디가 생각나지 않으세요?'
            '아직 회원가입을 하지 않으셨나요?'
            else if email 정보 있으면, 다음 단계
          -->

          <!-- 2단계: placeholder=비밀번호를 입력해주세요.

            if password 정보 없으면,
            placeholder=비밀번호를 다시 입력해주세요. <- 주황글씨
            시작버튼 하단에 
            '비밀번호가 생각나지 않으세요?'
            else if password  정보 있으면, 
            placeholder=비밀번호를 입력해주세요. 
          -->
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data: function () {
    return {
      submsg1: "아이디가 생각나지 않으세요?",
      // submsg1:'비밀번호가 생각나지 않으세요?',
      submsg2: "아직 회원가입을 하지 않으셨나요?",
      // placeholder:'이메일을 입력해주세요',
      placeholder: "이메일을 다시 입력해주세요",
      // placeholder:'비밀번호를 입력해주세요',
      // placeholder:'비밀번호를 다시 입력해주세요',
    };
  },
  methods: {
    submsg_clicked(seq) {
      alert(seq + "번째 문구 눌림");
      // + 현재 상태(아이디 단계인지 비밀번호 단계인지)
    },
  },
};
</script>

<style lang="scss">
@import "../assets/sass/base.scss";
/* 로그인페이지 틀 */
.background .box .innerbox .loginpage {
  position: inherit;
}

/* 로그인페이지 제목 - 잉키 */
.loginpage .title {
  /* 좌표 설정 */
  position: absolute;
  top: 0%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* text-align: center; */

  /* 디자인 */
  font-size: 30vh;
  color: #ff9a00;
  text-shadow: 15px 15px 15px rgba(0, 0, 0, 0.65);
}

/* 로그인페이지 부제목 - english for kids */
.loginpage .subtitle {
  /* 좌표 설정 */
  position: absolute;
  top: 38%;
  left: 50%;
  transform: translate(-50%, -50%);

  /* 디자인 */
  font-size: 8vh;
  color: rgba(255, 255, 255, 0.919);
  text-shadow: 15px 15px 15px rgba(0, 0, 0, 0.65);
}

/* 로그인페이지 input박스 + tiki */
.loginpage .input-with-tiki {
  /* 좌표 설정 */
  position: absolute;
  top: 65%;
  left: 50%;
  width: 100%;
  text-align: center;
  transform: translate(-50%, -50%);
  margin-left: -5vh;
}

/* 로그인페이지 tiki*/
.loginpage .moving-tiki {
  position: relative;
  transform: translate(100%, -50%);
  width: 10vh;
}

.loginpage .parents-button {
  /* 좌표 설정 */
  position: absolute;
  top: 83%;
  left: 50%;
  transform: translate(-50%, -100%);
}

.login-message-box {
  text-align: center;
  /* 좌표 설정 */
  position: absolute;
  top: 100%;
  left: 50%;
  transform: translate(-50%, -25%);
}
</style>
