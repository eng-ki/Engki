<template>
  <div>
    <div v-if="isUploaded">
      <div class="custom-edu">
        <img class="custom-img" :src="img" />
        <span class="title">
          아이가 학습할 단어와 문장을 커스터마이징 하세요!</span
        >
      </div>

      <div class="words">
        <table class="word">
          <tr
            v-for="(word, index) in custom.caption_word"
            v-bind:key="'A' + index"
          >
            <td width="150" style="border-right: 2px solid lightgray">
              <input size="10" v-model="custom.caption_word[index]" />
            </td>
            <td>
              <input size="14" v-model="custom.caption_word_kor[index]" />
            </td>
          </tr>
          <tr v-for="(word, index) in custom.seg_word" v-bind:key="'B' + index">
            <td width="150" style="border-right: 2px solid lightgray">
              <input size="10" v-model="custom.seg_word[index]" />
            </td>
            <td><input size="14" v-model="custom.seg_word_kor[index]" /></td>
          </tr>
        </table>
      </div>

      <!-- 이미지 캡셔닝 문장 수정 -->
      <div class="sentences">
        <div class="sentence">
          <span>영)</span>

          <input v-model="custom.caption" size="56vh" />
        </div>
        <div class="sentence">
          <span>한)</span>
          <input v-model="custom.caption_kor" size="56vh" />
        </div>
      </div>

      <!-- 학습 시작 버튼 -->
      <div class="custom-start">
        <img @click="test()" src="../../public/img/icon/next.png" />
      </div>
    </div>

    <!-- 이미지 등록 -->
    <div v-else>
      <input
        class="custom-image"
        ref="imageInput"
        type="file"
        hidden
        @change="onChangeImages"
      />
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
import http from '../utils/http-common.js'
export default {
  name: 'UploadPicture',
  props: {
    kid: null,
  },
  data: () => {
    return {
      caption_word_index: null,
      img: null,
      isUploaded: false, // 이미지 등록 여부
      selectedPencil: -1, // 연필 아이콘 focus,hover
      imageUrl: null, // 등록한 이미지 링크
      overlay: false, // 로딩 여부
      custom: null, //(file_path, boundaries, caption, seg_word, caption_word , caption_kor, seg_word_kor, caption_word_kor)
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
          // console.log(this.custom)
          // console.log(this.$store.state.parent.id)
          http
            .post(
              'http://j3a510.p.ssafy.io:8083/custom/quiz/save',
              {
                parent_id: this.$store.state.parent.id,
                file_path: this.custom.file_path,
                boundaries: this.custom.boundaries,
                caption: this.custom.caption,
                caption_kor: this.custom.caption_kor,
                seg_word: this.custom.seg_word,
                seg_word_kor: this.custom.seg_word_kor,
                caption_word: this.custom.caption_word,
                caption_word_kor: this.custom.caption_word_kor,
              },
              {
                headers: {
                  'Content-Type': 'application/json',
                  'Access-Control-Allow-Origin': '*',
                },
              }
            )
            .then(({ data }) => {
              console.log(data)
            })
            .catch((err) => {
              console.error(err)
            })
        }
      })
    },
    selectedWord(index) {
      this.selectedPencil = index
    },
    onClickImageUpload() {
      this.$refs.imageInput.click()
    },
    onChangeImages(e) {
      this.overlay = !this.overlay
      const file = e.target.files[0]
      const frm = new FormData()
      frm.append('files', file)
      frm.append('parent_id', this.$store.state.parent.id)

      http
        .post('http://j3a510.p.ssafy.io:8083/custom/quiz/make', frm, {
          headers: {
            'Content-Type': 'multipart/form-data',
            'Access-Control-Allow-Origin': '*',
          },
        })
        .then(({ data }) => {
          this.custom = data
          this.overlay = false
          this.isUploaded = true
        })
        .catch((err) => {
          console.error(err)
          this.overlay = false
        })

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
  height: 18vw;
  overflow: hidden;
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
.sentence span {
  float: left;

  opacity: 0.6;
  margin-top: 1.3vw;
  margin-left: 1vw;
  margin-right: 0.6vw;
}
.sentence input {
  float: left;
  border-bottom: 3px dashed #f2f2f2;
  color: #4b4b4b;
  margin-right: 0.5vw;
  height: 9vh;
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

.words {
  position: absolute;
  left: 29.5vw;
  top: 7.5vw;
  // background-color: red;
}
.words table {
  width: 31.5vw;
  height: 15vh;
  background-color: #f2f2f2;
  border-radius: 1vw;
}
.word td {
  font-size: 1.5vw;
  text-align: left;
  border-bottom: 2px solid lightgray;
  padding: 1vw 1vw 1vw 2vw;
}
.word td input {
  color: #4b4b4b;
}
.custom-start {
  position: absolute;
  right: 0vw;
  top: 23vh;
}

.custom-start img {
  width: 5vw;
  &:hover {
    opacity: 0.6;
  }
}
</style>
