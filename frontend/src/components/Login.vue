<template>
  <div>
    <v-layout fluid fill-height class="fullpage">
      <div class="login">
        <p class="title">잉키</p>
        <p class="subtitle">English for kids</p>
        <div class="input-with-tiki">
          <img
            class="moving-tiki"
            src="../../public//img/icon/moving_tiki.gif"
          />
          <button class="inputbox kakao_btn" @click="kakaoLogin()">
            <img src="../../public/img/kakao.png" class="kakao_img" />
            <span class="kakao_font">카카오 로그인</span>
          </button>
        </div>
        <div class="pointerdown">
          <img src="../../public/img/icon/arrow-down.png" />
        </div>
      </div>
    </v-layout>

    <v-layout fluid fill-height class="fullpage">
      <v-layout
        row
        wrap
        align-center
        :data-aos="$vuetify.breakpoint.mdAndUp ? 'fade-right' : ''"
        :data-aos-duration="$vuetify.breakpoint.mdAndUp ? '1000' : ''"
      >
        <v-row align="center" justify="center">
          <v-col
            cols="12"
            sm="8"
            md="5"
            :align="$vuetify.breakpoint.mdAndUp ? 'right' : 'center'"
            justify="center"
          >
            <span
              :class="{
                title2: $vuetify.breakpoint.mdAndUp,
                titleSmDown: $vuetify.breakpoint.smAndDown,
              }"
            >
              아이 친화적 환경</span
            ><br /><br />
            <span class="content">
              학습의 흥미를 높일 수 있는 귀여운 UI<br />
              지속적인 참여를 유도하는 Gamification<br />
              다양한 클릭 이벤트 기반의 이미지 학습<br />
            </span>
          </v-col>
          <v-col cols="12" sm="8" md="6" align="center" justify="center">
            <v-carousel
              reverse-transition="fade-transition"
              transition="fade-transition"
              hide-delimiters
              :next-icon="false"
              :prev-icon="false"
              :style="{
                width: $vuetify.breakpoint.mdAndUp ? '90vh' : '35vh',
              }"
              height="auto"
              interval="2500"
              :cycle="true"
            >
              <v-carousel-item
                src="../../public/img/etc/selectquiz.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/profileimage.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/selectkid.png"
              ></v-carousel-item>
            </v-carousel>
          </v-col>
          <v-col
            cols="12"
            sm="8"
            md="1"
            align="center"
            justify="center"
          ></v-col>
        </v-row>
      </v-layout>
    </v-layout>
    <v-layout fluid fill-height class="fullpage">
      <v-layout
        row
        wrap
        align-center
        :data-aos="$vuetify.breakpoint.mdAndUp ? 'fade-left' : ''"
        :data-aos-duration="$vuetify.breakpoint.mdAndUp ? '1000' : ''"
      >
        <v-row align="center" justify="center">
          <v-col
            cols="12"
            sm="8"
            md="1"
            align="center"
            justify="center"
          ></v-col>
          <v-col cols="12" sm="8" md="6" align="center" justify="center">
            <v-carousel
              reverse-transition="fade-transition"
              transition="fade-transition"
              hide-delimiters
              :next-icon="false"
              :prev-icon="false"
              :style="{
                width: $vuetify.breakpoint.mdAndUp ? '90vh' : '35vh',
              }"
              height="auto"
              interval="2500"
              :cycle="true"
            >
              <v-carousel-item
                src="../../public/img/etc/parent.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/breaktime.png"
              ></v-carousel-item>
            </v-carousel>
          </v-col>
          <v-col
            cols="12"
            sm="8"
            md="5"
            :align="
              (textAlignment = $vuetify.breakpoint.mdAndUp ? 'left' : 'center')
            "
            justify="center"
          >
            <span
              :class="{
                title2: $vuetify.breakpoint.mdAndUp,
                titleSmDown: $vuetify.breakpoint.smAndDown,
              }"
            >
              데이터 기반 학습</span
            ><br /><br />
            <span class="content">
              아이의 학습 상황을 알려주는 email report<br />
              AI가 표정을 판단해 자동으로 학습 시간 조절<br />
              부모님의 참여로 만들어지는, 우리 아이만을 위한 학습 자료<br />
            </span>
          </v-col>
        </v-row>
      </v-layout>
    </v-layout>

    <v-layout fluid fill-height class="fullpage">
      <v-layout
        row
        wrap
        align-center
        :data-aos="$vuetify.breakpoint.mdAndUp ? 'fade-right' : ''"
        :data-aos-duration="$vuetify.breakpoint.mdAndUp ? '1000' : ''"
      >
        <v-row align="center" justify="center">
          <v-col
            cols="12"
            sm="8"
            md="5"
            :align="$vuetify.breakpoint.mdAndUp ? 'right' : 'center'"
            justify="center"
          >
            <span
              :class="{
                title2: $vuetify.breakpoint.mdAndUp,
                titleSmDown: $vuetify.breakpoint.smAndDown,
              }"
            >
              커스텀 학습 </span
            ><br /><br />
            <span class="content">
              학습자료를 직접 등록하고 수정할 수 있어요<br />
              자녀의 눈높이에 맞춰<br />
              직접 퀴즈를 만들어보세요<br />
            </span>
          </v-col>
          <v-col cols="12" sm="8" md="6" align="center" justify="center">
            <v-carousel
              reverse-transition="fade-transition"
              transition="fade-transition"
              hide-delimiters
              :next-icon="false"
              :prev-icon="false"
              :style="{
                width: $vuetify.breakpoint.mdAndUp ? '90vh' : '35vh',
              }"
              height="auto"
              interval="2500"
              :cycle="true"
            >
              <v-carousel-item
                src="../../public/img/etc/ex.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/ex1.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/ex2.png"
              ></v-carousel-item>
            </v-carousel>
          </v-col>
          <v-col
            cols="12"
            sm="8"
            md="6"
            align="center"
            justify="center"
          ></v-col>
        </v-row>
      </v-layout>
    </v-layout>

    <v-layout fluid fill-height class="fullpage">
      <v-layout row wrap align="center" justify="center">
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="12" align="center" justify="center">
            <span
              :class="{
                title2: $vuetify.breakpoint.mdAndUp,
                titleSmDown: $vuetify.breakpoint.smAndDown,
              }"
            >
              아이를 위한 하나뿐인 학습자료</span
            ><br /><br />
            <span class="content">
              주변에 있는 친숙한 사물로 영어 단어를 공부할 수 있어요<br /><br />
            </span>
          </v-col>
        </v-row>
        <v-row align="center" justify="center">
          <v-col
            cols="12"
            sm="8"
            md="2"
            align="center"
            justify="center"
          ></v-col>
          <v-col cols="12" sm="8" md="4" align="center" justify="center">
            <v-carousel
              reverse-transition="fade-transition"
              transition="fade-transition"
              hide-delimiters
              :next-icon="false"
              :prev-icon="false"
              :style="{
                width: $vuetify.breakpoint.mdAndUp ? '60vh' : '20vh',
              }"
              height="auto"
              interval="2500"
              :cycle="true"
            >
              <v-carousel-item
                src="../../public/img/etc/quiz6.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/quiz1.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/quiz2.png"
              ></v-carousel-item>
            </v-carousel>
          </v-col>
          <v-col cols="12" sm="8" md="4" align="center" justify="center">
            <v-carousel
              reverse-transition="fade-transition"
              transition="fade-transition"
              hide-delimiters
              :next-icon="false"
              :prev-icon="false"
              :style="{
                width: $vuetify.breakpoint.mdAndUp ? '60vh' : '20vh',
              }"
              height="auto"
              interval="2500"
              :cycle="true"
            >
              <v-carousel-item
                src="../../public/img/etc/quiz5.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/quiz4.png"
              ></v-carousel-item>
              <v-carousel-item
                src="../../public/img/etc/quiz3.png"
              ></v-carousel-item>
            </v-carousel>
          </v-col>
          <v-col
            cols="12"
            sm="8"
            md="2"
            align="center"
            justify="center"
          ></v-col>
        </v-row>
      </v-layout>
    </v-layout>

    <v-layout fluid fill-height class="fullpage">
      <v-layout row wrap align-center>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="12" align="center" justify="center">
            <span
              :class="{
                title2: $vuetify.breakpoint.mdAndUp,
                titleSmDown: $vuetify.breakpoint.smAndDown,
              }"
              style="
                font-family: 'Jua', sans-serif;
                font-size: 6vw;
                color: rgba(255, 255, 255, 0.919);
                text-shadow: 15px 15px 15px rgba(0, 0, 0, 0.65);
              "
            >
              English for kids </span
            ><br />
            <p
              class="popout"
              :class="{
                mdUp: $vuetify.breakpoint.mdAndUp,
                smDown: $vuetify.breakpoint.smAndDown,
              }"
              style="font-family: 'GmarketSansBold' !important; font-size: 10vw"
            >
              <span>E</span>
              <span>N</span>
              <span>G</span>
              <span>K</span>
              <span>I</span>
              <br />
            </p>
            <span
              class="content"
              style="
                font-size: 2vw;
                color: white;
                font-weight: bold;
                border-bottom: 0px;
              "
            >
              Made by. Team 디키 (Developers for kids)
            </span>
          </v-col>
        </v-row>
      </v-layout>
    </v-layout>
  </div>
</template>
<script>
import http from '../utils/http-common.js';
export default {
  data: function () {
    return {
      inMove: false,
      activeSection: 0,
      offsets: [],
      touchStartY: 0,
    };
  },
  computed: {
    getToken() {
      return this.$store.getters.getToken;
    },
  },
  mounted() {
    this.calculateSectionOffsets();
    window.addEventListener('DOMMouseScroll', this.handleMouseWheelDOM); // Mozilla Firefox
    window.addEventListener('mousewheel', this.handleMouseWheel, {
      passive: false,
    }); // Other browsers
  },
  destroyed() {
    window.removeEventListener('mousewheel', this.handleMouseWheel, {
      passive: false,
    }); // Other browsers
    window.removeEventListener('DOMMouseScroll', this.handleMouseWheelDOM); // Mozilla Firefox
  },
  watch: {
    getToken(val, oldVal) {
      if (this.$store.state.isNew) this.$emit('child', true);
      else {
        http
          .get('parents/' + this.$store.state.parent.id + '/kids', {
            headers: { 'X-AUTH-TOKEN': this.$store.state.token },
          })
          .then(({ data }) => {
            if (data.length == 0) this.$router.push('/parent');
            else this.$router.push('/selectkid');
          });
      }
    },
  },
  methods: {
    kakaoLogin() {
      Kakao.Auth.login({
        success: this.kakaoLoginStore,
      });
      this.$store.commit('setTestCustomizing', false);
    },
    kakaoLoginStore(authObj) {
      this.$store.dispatch('kakaoLogin', {
        access_token: authObj.access_token,
      });
    },
    calculateSectionOffsets() {
      // 한 화면 크기 저장
      const sections = document.getElementsByClassName('fullpage');
      const { length } = sections;
      for (let i = 0; i < length; i += 1) {
        const sectionOffset = sections[i].offsetTop;
        this.offsets.push(sectionOffset);
      }
    },
    scrollToSection(id, force = false) {
      if (this.inMove && !force) return false;
      this.activeSection = id;
      this.inMove = true;
      document.getElementsByClassName('fullpage')[id].scrollIntoView({
        behavior: 'smooth',
      });
      setTimeout(() => {
        this.inMove = false;
      }, 400);
      return true;
    },
    handleMouseWheel(e) {
      if (e.wheelDelta < 30 && !this.inMove) {
        this.moveUp();
      } else if (e.wheelDelta > 30 && !this.inMove) {
        this.moveDown();
      }
      e.preventDefault();
      return false;
    },
    moveDown() {
      this.inMove = true;
      this.activeSection -= 1;
      if (this.activeSection < -1) {
        this.activeSection = 0;
      }
      this.scrollToSection(this.activeSection, true);
    },
    moveUp() {
      this.inMove = true;
      this.activeSection += 1;
      if (this.activeSection > this.offsets.length - 1) this.activeSection = 0;
      this.scrollToSection(this.activeSection, true);
    },
  },
};
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
/* 로그인페이지 틀 */
.background .box .innerbox .login {
  position: inherit;
}

.pointerdown {
  position: absolute;
  bottom: 2vw;
  left: 50%;
  transform: translate(-50%, -50%);
}

.pointerdown img {
  width: 5.6vw;
  opacity: 0.82;
  animation: pointers 1.5s linear 1.5s infinite alternate running;
}

@keyframes pointers {
  from {
    opacity: 0.82;
  }
  20% {
    opacity: 0.82;
    transform: translateX(0);
  }
  to {
    opacity: 0.82;
    transform: translate(0px, -25px);
  }
}

/* 로그인페이지 제목 - 잉키 */
.login .title {
  /* 좌표 설정 */
  position: absolute;
  top: 35vh;
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
  top: 52vh;
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
  top: 68vh;
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
  background: url('../static/img/background/background1.jpg') no-repeat bottom
    center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;

  /* 사이즈 fullscreen */
  min-width: 500px;
  min-height: 490px;
  opacity: 1;
}

.mdUpRow {
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
  font-weight: 900;
  font-size: 3.5vw;
  color: white;
  font-family: GmarketSansMedium;
  text-shadow: 2px 2px 2px #2a632e;
}
.titleSmDown {
  font-family: GmarketSansMedium;
  font-size: 4vh !important;
}
.content {
  font-size: 1.2vw;
  margin-bottom: 2vw;
  color: white;
  font-family: GmarketSansMedium;
  text-shadow: 1.2px 1.2px 1.2px #2a632e;
  border-bottom: 3px solid green;
  line-height: 2.1em;
}
.popout {
  font-family: GmarketSansMedium;
  font-weight: 900;
  letter-spacing: 0.5vw;
  @keyframes ani {
    0% {
      transform: translate3d(0, 0, 0);
      text-shadow: 0em 0em 0 $back;
      color: #ff9a00;
    }
    30% {
      transform: translate3d(0, 0, 0);
      text-shadow: 0em 0em 0 $back;
      color: #ff9a00;
    }
    70% {
      transform: translate3d($d, -$d, 0);
      text-shadow: 15px 15px 15px rgba(0, 0, 0, 0.65);
      color: #ff9a00;
    }
    100% {
      transform: translate3d($d, -$d, 0);
      text-shadow: 15px 15px 15px rgba(0, 0, 0, 0.65);
      color: #ff9a00;
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
