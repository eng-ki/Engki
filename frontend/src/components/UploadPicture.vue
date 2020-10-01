<template>
  <div>
    <div v-if="isUploaded">
      <div class="custom-edu">
        <img class="custom-img" :src="img" />
        <span class="title"
          >아이가 학습할 문장과 단어를 커스터마이징 하세요!</span
        >
        <div
          class="sentences"
          v-for="(sentence, index) in sentences"
          v-bind:key="index"
        >
          <div class="sentence" @click="selectedSentence(index)">
            <input :value="sentence" size="34vh" :readonly="!readS" />
          </div>
        </div>
      </div>
      <span class="word" v-for="(word, index) in words" v-bind:key="index">
        <input
          :value="word"
          size="10vh"
          @click="selectedWord(index)"
          :readonly="!readW" />
        <img
          src="../../public/img/icon/pencil.png"
          :class="{ selectedpencil: selectedPencil == index }"
          class="btn-update2"
      /></span>
      <div class="custom-start">
        <img
          v-bind="attrs"
          v-on="on"
          @click="test()"
          src="../../public/img/icon/next.png"
        />
      </div>
    </div>
    <div v-else>
      <input ref="imageInput" type="file" hidden @change="onChangeImages" />
      <v-btn type="button" @click="onClickImageUpload" large
        >학습에 사용할 이미지를 등록해주세요</v-btn
      >
      <v-img v-if="imageUrl" :src="imageUrl"></v-img>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Report',
  props: {
    kid: null,
  },
  data: () => {
    return {
      img: '/img/etc/womanpuppy.jpg',
      sentences: ['The dog and the woman are running'],
      words: ['dog', 'woman', 'sea', 'sand'],
      isUploaded: false, // 이미지 등록시
      selectedPencil: -1,
      imageUrl: null,
      ReadS: false,
      ReadW: false,
    }
  },
  methods: {
    test() {
      this.$swal({
        title:
          '<span style="font-family: GmarketSansMedium;font-size:1.5vw;">현재 데이터로 학습을 시작하시겠습니까?</span>',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: '네',
        cancelButtonText: '아니요',
        showCloseButton: true,
        showLoaderOnConfirm: true,
      }).then((result) => {
        if (result.value) {
          this.$router.push('/quiz')
        }
      })
    },
    selectedSentence(index) {
      this.readS = true
      this.selectedCheck = index
    },
    selectedWord(index) {
      this.readW = true
      this.selectedPencil = index
    },
    onClickImageUpload() {
      this.$refs.imageInput.click()
    },
    onChangeImages(e) {
      console.log(e.target.files)
      const file = e.target.files[0]
      this.isUploaded = true
      this.img = URL.createObjectURL(file)
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
* {
  font-family: 'GmarketSansMedium';
}
.custom-edu {
  position: absolute;
  left: 16.5vw;
  display: inline-block;
}
.custom-edu .custom-img {
  width: 20vw;
  float: left;
  border-radius: 1vw;
  margin-right: 2vw;
}

.title {
  font-size: 1.5vw;
  float: left;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.sentences {
  float: left;
  font-size: 1.5vw;
  margin-top: 3vw;
  text-align: left;
}

.sentence input {
  padding-bottom: 0.3vw;
  color: #4b4b4b;
  margin-right: 0.5vw;
  height: 10vh;
  &:hover {
    opacity: 0.8;
    transition: all 0.1s;
  }

  &:focus {
    opacity: 0.8;
    transition: all 0.1s;
    color: green;
  }
  box-shadow: rgba(17, 12, 46, 0.15) 0px 48px 100px 0px;
}
// .sentence .btn-update {
//   opacity: 0;
//   width: 1.5vw;
//   float: right;
// }
// .sentence:hover .btn-update {
//   opacity: 1;
//   transform: scale(3vw);
//   float: right;
// }

// .sentence .selectedcheck {
//   opacity: 1;
//   transform: scale(3vw);
//   float: right;
// }

.custom-start {
  position: absolute;
  right: 0vw;
  top: 18vh;
}

.custom-start img {
  width: 4vw;
  &:hover {
    opacity: 0.6;
  }
}

.word {
  position: relative;
  top: 45vh;
  left: 2vw;
  float: left;
  margin-right: 1.5vw;
  margin-bottom: 1.5vh;
  padding-right: 1vw;
  width: 10vw;
  height: 5.5vh;
  /* 디자인 */
  border-radius: 7vw;
  background-color: #dfdfdf;
  padding-top: 1.4vh;

  /* 폰트 */
  font-size: 1vw;
  &:hover {
    opacity: 0.8;
  }
}

.word input {
  color: #4b4b4b;
}
.word .btn-update2 {
  opacity: 0;
  width: 1vw;
  float: right;
}
.word:hover .btn-update2 {
  opacity: 1;
  transform: scale(1vw);
  float: right;
}

.word .selectedpencil {
  opacity: 1;
  transform: scale(1vw);
  float: right;
}
</style>
