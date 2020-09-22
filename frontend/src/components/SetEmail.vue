<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <span class="page-title">회원가입</span>
        <div class="parent-info-page">
          <div class="container page-text">
            <div class="row">
              <div class="col-sm-5">
                <p>이름</p>
              </div>
              <div class="col-sm-7">
                <input class="info-inputbox" v-model="parents.name" />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-5">
                <p>생일</p>
              </div>
              <div class="col-sm-7">
                <input class="info-inputbox" v-model="parents.bday" />
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
                <p>비밀번호</p>
              </div>
              <div class="col-sm-7">
                <input
                  class="info-inputbox"
                  v-model="parents.pwd"
                  type="password"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-5">
                <p>비밀번호 확인</p>
              </div>
              <div class="col-sm-7">
                <input
                  class="info-inputbox"
                  v-model="parents.pwd_re"
                  type="password"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-*">
                <button class="parents-button">가입하기</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data: function () {
    return {
      parents: {
        name: '손명지',
        bday: '1994-04-03',
        email: 'ji_exitos@naver.com',
        pwd: '1234567',
        pwd_re: '1234567',
      },
    };
  },
};
</script>
