<template>
  <div class="background">
    <div class="card-carousel-wrapper box">
      <div class="title-with-tiki">
        <span class="showbox">{{ msg }} </span>
      </div>
      <div
        class="card-carousel--nav__left"
        @click="moveCarousel(-1)"
        :disabled="atHeadOfList"
      ></div>
      <div class="card-carousel innerbox">
        <div class="card-carousel--overflow-container selectsth">
          <div
            class="card-carousel-cards datalist"
            :style="{
              transform: 'translateX' + '(' + currentOffset + 'px' + ')',
            }"
          >
            <div
              class="card-carousel--card data"
              v-for="(data, index) in datas"
              v-bind:key="index"
              @click="returnID(data.id)"
            >
              <img class="dataimage" :src="data.img" />
              <div class="dataname">{{ data.name }}</div>
              <!-- <div class="card-carousel--card--footer">
              <p>{{ item.name }}</p>
              <p
                class="tag"
                v-for="(tag, index) in item.tag"
                :class="index &gt; 0 ? 'secondary' : ''"
              >
                {{ tag }}
              </p>
            </div> -->
            </div>
          </div>
        </div>
      </div>
      <div
        class="card-carousel--nav__right"
        @click="moveCarousel(1)"
        :disabled="atEndOfList"
      ></div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    option: String,
  },
  created() {
    if (this.option == 'quiz') {
      this.msg = '주제를 선택해주세요'
    } else if (this.option == 'kid') {
      this.msg = '자신의 캐릭터를 선택해주세요'
    }
  },
  computed: {
    atEndOfList() {
      return (
        this.currentOffset <=
        this.paginationFactor * -1 * (this.datas.length - this.windowSize)
      )
    },
    atHeadOfList() {
      return this.currentOffset === 0
    },
  },
  data: function () {
    return {
      msg: '',
      // datas: [
      //   {
      //     id: '1',
      //     name: '손명지',
      //     img: '/img/icon/kid1.png',
      //   },
      //   {
      //     id: '2',
      //     name: '서주현',
      //     img: '/img/icon/kid2.png',
      //   },
      //   {
      //     id: '3',
      //     name: '차윤석',
      //     img: '/img/icon/kid3.png',
      //   },
      //   {
      //     id: '4',
      //     name: '손명지',
      //     img: '/img/icon/kid1.png',
      //   },
      //   {
      //     id: '5',
      //     name: '서주현',
      //     img: '/img/icon/kid2.png',
      //   },
      //   {
      //     id: '6',
      //     name: '차윤석',
      //     img: '/img/icon/kid3.png',
      //   },
      // ],
      datas: [
        {
          id: '1',
          name: '음식',
          img: '/img/icon/theme/fruit.png',
        },
        {
          id: '2',
          name: '동물',
          img: '/img/icon/theme/cat.png',
        },
        {
          id: '3',
          name: '탈것',
          img: '/img/icon/theme/transportation.png',
        },
        {
          id: '4',
          name: '사물',
          img: '/img/icon/theme/desk.png',
        },
        {
          id: '3',
          name: '탈것',
          img: '/img/icon/theme/transportation.png',
        },
        {
          id: '4',
          name: '사물',
          img: '/img/icon/theme/desk.png',
        },
      ],
      currentOffset: 0,
      windowSize: 3, // carousel에 띄워줄 아이콘 갯수! <- 반응형으로 할거면 화면에 몇개 나오는지 계산해서 여기 넣어야 공백 안생길듯
      paginationFactor: 222,
    }
  },
  methods: {
    returnID(id) {
      if (this.option == 'quiz') {
        alert(id + '번째 퀴즈로 이동')
        this.$router.push('/quiz')
      } else if (this.option == 'kid') {
        alert(id + '번째 자녀 계정 로그인')
      }
    },
    moveCarousel(direction) {
      if (direction === 1 && !this.atEndOfList) {
        this.currentOffset -= this.paginationFactor
      } else if (direction === -1 && !this.atHeadOfList) {
        this.currentOffset += this.paginationFactor
      }
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
/* 로그인페이지 input박스 + tiki */
.title-with-tiki {
  /* 좌표 설정 */
  position: absolute;
  top: 20vh;
  left: 50%;
  width: 100%;
  text-align: center;
  transform: translate(-50%, -50%);
  padding: 50px;
}
.box {
  position: relative;
  // top: 20vh;
}
.datalist {
  position: relative;
  margin-right: 10vw;
  margin-left: 10vw;
  white-space: nowrap;
  width: 80vw;
  &::-webkit-scrollbar {
    width: 10px;
  }
  &::-webkit-scrollbar-thumb {
    width: 10px;
    background-color: rgba(220, 219, 223, 0.486);
    border-radius: 30px;
    background-clip: padding-box;
    border: 6px solid transparent;
  }
  &::-webkit-scrollbar-track {
    width: 5px;
    background-color: transparent;
    border-radius: 30px;
  }
}

.data {
  display: inline-block;
  position: relative;
}

.datalist .dataname {
  padding: 10px 0px;
  position: relative;
  width: 28vh;
  text-align: center;
  vertical-align: middle;
  font-size: 5vh;
}

$arrowcolor: #ffffff;
.card-carousel-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50vh;
}

.card-carousel {
  position: relative;
  top: 30vh;
  display: flex;
  justify-content: center;
  width: 640px;

  &--overflow-container {
    overflow: hidden;
  }

  &--nav__left,
  &--nav__right {
    display: inline-block;
    width: 15px;
    height: 15px;
    padding: 10px;
    box-sizing: border-box;
    border-top: 10px solid $arrowcolor;
    border-right: 10px solid $arrowcolor;
    border-radius: 4px;
    cursor: pointer;
    margin: 0 50px;
    transition: transform 150ms linear;
    &[disabled] {
      opacity: 0.3;
      border-color: white;
    }
  }

  &--nav__left {
    position: relative;
    top: 30vh;
    transform: rotate(-135deg);
    &:active {
      transform: rotate(-135deg) scale(0.9);
    }
  }

  &--nav__right {
    position: relative;
    top: 30vh;
    transform: rotate(45deg);
    &:active {
      transform: rotate(45deg) scale(0.9);
    }
  }
}

.card-carousel-cards {
  display: flex;
  transition: transform 150ms ease-out;
  transform: translatex(0px);
  width: 80vw;
  .card-carousel--card {
    margin: 0 10px;
    cursor: pointer;
    // background-color: #fff;
    z-index: 3;
    &:first-child {
      margin-left: -10vw;
    }
    &:last-child {
      margin-right: 0;
    }
    img {
      position: relative;
      width: 28vh;
      vertical-align: middle;
      transition: opacity 150ms linear;
      user-select: none;

      &:hover {
        opacity: 0.5;
      }
    }
  }
}
</style>
