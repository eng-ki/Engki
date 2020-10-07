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
  data: function () {
    return {
      inMove: false,
      activeSection: 0,
      offsets: [],
      touchStartY: 0,
    }
  },
  computed: {
    getToken() {
      return this.$store.getters.getToken
    },
  },
  mounted() {
    this.calculateSectionOffsets()
    window.addEventListener('DOMMouseScroll', this.handleMouseWheelDOM) // Mozilla Firefox
    window.addEventListener('mousewheel', this.handleMouseWheel, {
      passive: false,
    }) // Other browsers
  },
  destroyed() {
    window.removeEventListener('mousewheel', this.handleMouseWheel, {
      passive: false,
    }) // Other browsers
    window.removeEventListener('DOMMouseScroll', this.handleMouseWheelDOM) // Mozilla Firefox
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
    calculateSectionOffsets() {
      // 한 화면 크기 저장
      const sections = document.getElementsByClassName('fullpage')
      const { length } = sections
      for (let i = 0; i < length; i += 1) {
        const sectionOffset = sections[i].offsetTop
        this.offsets.push(sectionOffset)
      }
    },
    scrollToSection(id, force = false) {
      if (this.inMove && !force) return false
      this.activeSection = id
      this.inMove = true
      document.getElementsByClassName('fullpage')[id].scrollIntoView({
        behavior: 'smooth',
      })
      setTimeout(() => {
        this.inMove = false
      }, 400)
      return true
    },
    handleMouseWheel(e) {
      if (e.wheelDelta < 30 && !this.inMove) {
        this.moveUp()
      } else if (e.wheelDelta > 30 && !this.inMove) {
        this.moveDown()
      }
      e.preventDefault()
      return false
    },
    moveDown() {
      this.inMove = true
      this.activeSection -= 1
      if (this.activeSection < 0) {
        this.activeSection = 0
      }
      this.scrollToSection(this.activeSection, true)
    },
    moveUp() {
      this.inMove = true
      this.activeSection += 1
      if (this.activeSection > this.offsets.length - 1) this.activeSection = 0
      this.scrollToSection(this.activeSection, true)
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

$t: 1s;
$d: 0.08em;
$n: 3;
$e: cubic-bezier(0.86, 0, 0.07, 1);
$front: black;
$back: lightyellow;
html {
  background: white;
}
.fullpage {
  height: 100vh;
  width: 100%;
  align-items: center;
}
.mdUpRow {
  padding-top: 8vh;
  padding-bottom: 8vh;
}
.smDownRow {
  padding-top: 15vh;
  padding-bottom: 15vh;
}
.mdUp {
  font-size: 80px;
}
.smDown {
  font-size: 50px;
}
.title2 {
  font-family: GmarketSansMedium;
  font-size: 8vh !important;
}
.titleSmDown {
  font-family: GmarketSansMedium;
  font-size: 4vh !important;
}
.content {
  font-family: GmarketSansMedium;
  font-size: 2vh;
}
.popout {
  font-family: GmarketSansMedium;
  font-weight: 900;
  @keyframes ani {
    0% {
      transform: translate3d(0, 0, 0);
      text-shadow: 0em 0em 0 $back;
      color: $front;
    }
    30% {
      transform: translate3d(0, 0, 0);
      text-shadow: 0em 0em 0 $back;
      color: $front;
    }
    70% {
      transform: translate3d($d, -$d, 0);
      text-shadow: -$d $d $back;
      color: $front;
    }
    100% {
      transform: translate3d($d, -$d, 0);
      text-shadow: -$d $d $back;
      color: $front;
    }
  }
  span {
    position: relative;
    display: inline-block;
    animation: ani $t infinite alternate $e;
    @for $i from 1 through $n {
      &:nth-last-child(#{$i}n) {
        animation-delay: -$t * $i/$n/2;
      }
    }
  }
}
</style>
