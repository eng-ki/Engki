<template>
  <div>
    <div v-if="isUploaded">
      <div class="custom-edu">
        <img class="custom-img" :src="img" />
        <!-- 타이틀 -->
        <span class="title"
          >아이가 학습할 단어와 문장을 커스터마이징 하세요!</span
        >
        <!-- 세그멘테이션 단어 (0~3개) 수정 -->
        <span class="word" v-for="(word, index) in words" v-bind:key="index">
          <input
            :v-model="word"
            size="7vh"
            @click="selectedWord(index)"
            :readonly="!readW" />
          <img
            src="../../public/img/icon/pencil.png"
            :class="{ selectedpencil: selectedPencil == index }"
            class="btn-update2"
        /></span>
      </div>

      <!-- 이미지 캡셔닝 문장 수정 -->
      <div
        class="sentences"
        v-for="(sentence, index) in sentences"
        v-bind:key="index"
      >
        <div class="sentence">
          <img src="../../public/img/icon/copy.png" />

          <input
            @click="readS = true"
            :v-model="sentence"
            size="56vh"
            :readonly="!readS"
          />
        </div>
      </div>

      <!-- 학습 시작 버튼 -->
      <div class="custom-start">
        <img
          v-bind="attrs"
          v-on="on"
          @click="test()"
          src="../../public/img/icon/next.png"
        />
      </div>
    </div>

    <!-- 이미지 등록 -->
    <div v-else>
      <input ref="imageInput" type="file" hidden @change="onChangeImages" />
      <button class="upload-image" @click="onClickImageUpload">
        학습에 사용할 이미지를 등록해주세요
      </button>
      <v-img v-if="imageUrl" :src="imageUrl"></v-img>
    </div>

    <!-- 이미지 등록 중 로딩 -->
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="100"></v-progress-circular>
    </v-overlay>
  </div>
</template>
<script>
export default {
  name: 'UploadPicture',
  props: {
    kid: null,
  },
  data: () => {
    return {
      img: '/img/etc/womanpuppy.jpg',
      sentences: ['The dog and the woman are running beside the seaside'],
      words: ['dog', 'woman', 'sea'],
      isUploaded: false, // 이미지 등록 여부
      selectedPencil: -1, // 연필 아이콘 focus,hover
      imageUrl: null, // 등록한 이미지 링크
      ReadS: false, // 문장 readonly 여부
      ReadW: false, // 단어 readonly 여부
      overlay: false, // 로딩 여부
    }
  },
  methods: {
    test() {
      this.$swal({
        title:
          '<span style="font-family: GmarketSansMedium;font-size:1.2vw;">현재 데이터로 학습을 시작하시겠습니까?</span>',
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
      this.overlay = !this.overlay
      setTimeout(() => {
        this.overlay = false
        this.isUploaded = true
      }, 3000)
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
  // z-index: 1000;
  // padding: 20%;
}
.upload-image {
  position: absolute;
  top: 20vh;
  left: 13.5vw;
  font-size: 2.5vw;
  width: 50vw;
  height: 15vh;
  background-color: #f2f2f2;
  box-shadow: rgba(1, 1, 1, 0.15) 0px 2px 3px 0px;
}
.custom-edu {
  position: absolute;
  top: 8vh;
  left: 9vw;
  display: inline-block;
}
.custom-edu .custom-img {
  width: 18vw;
  float: left;
  border-radius: 3vw;
  margin-right: 2.2vw;
  border: 7px solid #f2f2f2;
}

.title {
  font-size: 1.45vw;
  float: left;
  margin-top: 1.1vh;
  margin-bottom: 1vh;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.sentences {
  position: relative;
  top: 39vh;
  left: 2vw;
  font-size: 1.3vw;
  margin-top: 3vw;
  text-align: left;
}
.sentence {
  display: inline-block;
  // box-shadow: rgba(10, 5, 26, 0.15) 0px 48px 100px 0px;
}
.sentence img {
  float: left;
  width: 2.2vw;
  opacity: 0.6;
  margin-top: 1.2vw;
  margin-left: 1vw;
  margin-right: 0.6vw;
}
.sentence input {
  float: left;
  border-bottom: 3px dashed #f2f2f2;
  padding-bottom: 0.3vw;
  color: #4b4b4b;
  margin-right: 0.5vw;
  height: 10vh;
  padding: 0 1vw 0 1vw;

  &:hover {
    opacity: 0.8;
    transition: all 0.1s;
  }

  &:focus {
    opacity: 0.8;
    transition: all 0.1s;
    color: green;
  }
}

.word {
  float: left;
  width: 14.5vw;
  height: 9vh;
  /* 디자인 */
  border-radius: 10vw;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 1px 2px;
  background-color: #f2f2f2;
  margin-top: 4vh;
  margin-right: 2.3vw;
  margin-bottom: 1.5vh;
  padding-top: 2.5vh;
  padding-left: 1.4vw;

  /* 폰트 */
  font-size: 1.5vw;
}

.word input {
  color: #4b4b4b;
  text-align: center;
  vertical-align: middle;
  &:focus {
    color: green;
    opacity: 0.8;
  }
}
.word .btn-update2 {
  opacity: 0;
  width: 1vw;
  float: right;
  margin-top: 1vh;
  margin-right: 1vw;
}
.word:hover .btn-update2 {
  opacity: 1;
  transform: scale(1vw);
  float: right;
  margin-top: 1vh;
  margin-right: 1vw;
}

.word .selectedpencil {
  opacity: 1;
  transform: scale(1vw);
  float: right;
  margin-top: 1vh;
  margin-right: 1vw;
}

.custom-start {
  position: absolute;
  right: -3vw;
  top: 23vh;
}

.custom-start img {
  width: 5vw;
  &:hover {
    opacity: 0.6;
  }
}
</style>
