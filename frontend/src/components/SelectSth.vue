<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <div class="selectsth">
          <div class="title-with-tiki">
            <span class="showbox">{{ msg }} </span>
          </div>
          <div class="datalist">
            <div v-for="(data, index) in datas" v-bind:key="index" class="data">
              <!-- <span>{{kid}}</span> -->
              <!-- <img class="kidimage" src=kid.img> -->
              <img class="dataimage" src="../static/img/icon/kid1.png" />
              <div class="dataname">{{ data.name }}</div>
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
    option: String,
  },
  created() {
    if (this.option == 'quiz') {
      this.msg = '주제를 선택해주세요';
    } else if (this.option == 'kid') {
      this.msg = '자신의 캐릭터를 선택해주세요';
    }
  },
  data: function () {
    return {
      msg: '',
      datas: [
        {
          id: '1',
          name: '손명지',
          img: '../static/img/icon/kid1.png',
        },
        {
          id: '2',
          name: '서주현',
          img: '../images/icon/kid2.png',
        },
        {
          id: '3',
          name: '차윤석',
          img: '../images/icon/kid3.png',
        },
        {
          id: '1',
          name: '손명지',
          img: '../static/img/icon/kid1.png',
        },
        {
          id: '2',
          name: '서주현',
          img: '../images/icon/kid2.png',
        },
        {
          id: '3',
          name: '차윤석',
          img: '../images/icon/kid3.png',
        },
      ],
    };
  },
};
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
/* 로그인페이지 input박스 + tiki */
.selectsth .title-with-tiki {
  /* 좌표 설정 */
  position: absolute;
  top: 20vh;
  left: 50%;
  width: 100%;
  text-align: center;
  transform: translate(-50%, -50%);
  /* margin-left: -5vh; */
  padding: 50px;
}
.datalist {
  position: relative;
  top: 35vh;
  margin-right: 10vw;
  margin-left: 10vw;
  overflow: scroll;
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
  // top: 60vh;
  /* left:50%; */
  position: relative;
  width: 30vh;
  padding: 3vh;
  // height: 10vh;
  // transform: translate(0, -50%);
  // background-color: red;
}

.datalist .dataimage {
  position: relative;
  width: 28vh;
  vertical-align: middle;
}

.datalist .dataname {
  padding: 10px 0px;
  position: relative;
  width: 28vh;
  text-align: center;
  vertical-align: middle;
  font-size: 5vh;
}
</style>
