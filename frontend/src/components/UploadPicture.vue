<template>
  <div>
    <div v-if="isUploaded">
      <!-- 문장 수정 모달 -->
      <v-app>
        <v-dialog v-model="isEditCaption" width="400">
          <v-card>
            <v-card-title class="headline lighten-2">
              <span style="font-family: GmarketSansMedium; font-size: 1vw">
                문장 수정</span
              >
            </v-card-title>

            <v-card-text>
              <v-layout wrap>
                <v-flex xs12>
                  <v-text-field
                    label="영문"
                    v-model="selectedCaption.caption"
                    required
                  >
                  </v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    label="한글"
                    v-model="selectedCaption.caption_kor"
                    required
                    @keydown.enter="editCaption"
                  >
                  </v-text-field>
                </v-flex>
              </v-layout>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="clearCaption()"> 닫기 </v-btn>
              <v-btn color="primary" text @click="editCaption">
                수정하기
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-app>
      <!-- 문장 수정 모달 끝-->
      <!-- 단어 수정 모달 -->
      <v-app>
        <v-dialog v-model="isEditWord" width="400">
          <v-card>
            <v-card-title class="headline lighten-2">
              <span style="font-family: GmarketSansMedium; font-size: 1vw">
                단어 수정</span
              >
            </v-card-title>

            <v-card-text>
              <v-layout wrap>
                <v-flex xs12>
                  <v-text-field
                    label="영문"
                    v-model="selectedWord.word"
                    required
                  >
                  </v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    label="한글"
                    v-model="selectedWord.word_kor"
                    required
                    @keydown.enter="editWord"
                  >
                  </v-text-field>
                </v-flex>
              </v-layout>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="clearWord()"> 닫기 </v-btn>
              <v-btn color="primary" text @click="editWord"> 수정하기 </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-app>
      <!-- 단어 수정 모달 끝 -->

      <!-- 이미지 & 학습 저장 버튼 -->
      <div class="custom-bg">
        <div class="custom-img">
          <img :src="img" />
          <button @click="test()" id="custom-save">학습 정보 저장하기</button>
          <b-tooltip placement="top" target="custom-save" triggers="hover">
            <span
              style="
                font-family: GmarketSansMedium;
                color: #f2f2f2;
                font-size: 0.8vw;
              "
              >저장된 정보는 퀴즈에 사용됩니다</span
            >
          </b-tooltip>
        </div>

        <!-- 이미지 & 학습 저장 버튼 끝-->

        <!-- 학습용 문장 & 단어 수정 리스트 -->
        <div class="custom-edu">
          <div class="custom-title">
            사진에서 추출한
            <span style="color: darkgreen">문장</span>을 자녀의 눈높이에 맞게
            수정해보세요
          </div>

          <v-list subheader two-line>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title v-text="custom.caption"></v-list-item-title>
                <v-list-item-subtitle
                  v-text="custom.caption_kor"
                ></v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-action>
                <v-btn
                  @click="onEditCaption()"
                  v-b-tooltip.hover
                  title="문장 수정하기"
                  icon
                >
                  <v-icon color="grey lighten-1">mdi-pencil</v-icon>
                </v-btn>
              </v-list-item-action></v-list-item
            >
          </v-list>
          <div class="custom-title">
            사진에서 추출한 <span style="color: darkgreen">단어</span>를 자녀의
            눈높이에 맞게 수정해보세요
          </div>
          <v-list subheader two-line>
            <v-list-item
              v-for="index in custom.caption_word.length"
              :key="'A' + index"
            >
              <v-list-item-content>
                <v-list-item-title
                  v-text="custom.caption_word[index - 1]"
                ></v-list-item-title>

                <v-list-item-subtitle
                  v-text="custom.caption_word_kor[index - 1]"
                ></v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-btn
                  @click="onEditWord(index - 1, 'caption')"
                  v-b-tooltip.hover
                  title="단어 수정하기"
                  icon
                >
                  <v-icon color="grey lighten-1">mdi-pencil</v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-list>

          <v-list subheader two-line>
            <v-list-item
              v-for="index in custom.seg_word.length"
              :key="'B' + index"
            >
              <v-list-item-content>
                <v-list-item-title
                  v-text="custom.seg_word[index - 1]"
                ></v-list-item-title>

                <v-list-item-subtitle
                  v-text="custom.seg_word_kor[index - 1]"
                ></v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-btn
                  @click="onEditWord(index - 1, 'seg')"
                  v-b-tooltip.hover
                  title="단어 수정하기"
                  icon
                >
                  <v-icon color="grey lighten-1">mdi-pencil</v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-list>
        </div>
      </div>
    </div>
    <!-- 학습용 문장 & 단어 수정 리스트 끝 -->
    <!-- 이미지 등록 -->
    <div v-else>
      <div class="upload">
        <input
          class="custom-image"
          ref="imageInput"
          type="file"
          hidden
          @change="onChangeImages"
        />
        <button class="upload-image" @click="onClickImageUpload">
          학습에 사용할 사진을<br />등록해주세요
        </button>
      </div>
    </div>

    <!-- 이미지 등록 중 로딩 -->
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="100"></v-progress-circular>
    </v-overlay>
  </div>
</template>
<script>
import http from '../utils/http-common.js';

export default {
  name: 'UploadPicture',
  props: {
    kid: null,
  },
  data: () => {
    return {
      // 캡션 수정용 모달 데이터
      selectedCaption: {
        caption: '',
        caption_kor: '',
      },
      isEditCaption: false,
      // 단어 수정용 모달 데이터
      selectedWord: {
        target: '',
        index: '',
        word: '',
        word_kor: '',
      },
      isEditWord: false,
      // 캡션단어 세그단어 중복 처리 데이터
      seg_word_duplicate: [],
      seg_word_duplicate_kor: [],
      // 이미지 미리보기
      img: null,
      isUploaded: false, // 이미지 등록 여부
      overlay: false, // 로딩 여부
      // ai 서버에서 받아온 학습 결과
      custom: {
        caption: '',
        caption_kor: '',
        caption_word: [],
        caption_word_kor: [],
        seg_word: [],
        seg_word_kor: [],
      },
      tempdata: '',
    };
  },
  methods: {
    // 수정한 학습용 데이터 서버에 저장하기
    test() {
      this.$swal({
        title:
          '<span style="font-family: GmarketSansMedium;font-size:1.2vw;">현재 데이터로 학습을 시작하시겠습니까?</span>',
        showCancelButton: true,
        confirmButtonText: '네',
        showCloseButton: true,
      }).then((result) => {
        if (result.value) {
          http
            .post(
              'https://j3a510.p.ssafy.io:8083/custom/quiz/save',
              {
                parent_id: this.$store.state.parent.id,
                file_path: this.custom.file_path,
                boundaries: this.custom.boundaries,
                caption: this.custom.caption,
                caption_kor: this.custom.caption_kor,
                seg_word: this.custom.seg_word.concat(this.seg_word_duplicate),
                seg_word_kor: this.custom.seg_word_kor.concat(
                  this.seg_word_duplicate_kor
                ),
                caption_word: this.custom.caption_word,
                caption_word_kor: this.custom.caption_word_kor,
              },
              {
                headers: {
                  'Content-Type': 'application/json',
                },
              }
            )
            .then(({ data }) => {
              this.tempdata = data;
              if (data != null) {
                this.$swal({
                  title:
                    '<span style="font-family: GmarketSansMedium;font-size:1.2vw;">퀴즈 데이터에 성공적으로 저장되었습니다</span>',
                  showCancelButton: false,
                  confirmButtonText: '확인',
                }).then((result) => {
                  this.$swal({
                    title:
                      '<span style="font-family: GmarketSansMedium;font-size:1.2vw;">제작한 퀴즈를 테스트해보시겠습니까?</span>',
                    showCancelButton: true,
                    confirmButtonText: '네',
                    cancleButtonText: '아니요',
                  }).then((result) => {
                    if (result.value) {
                      this.$store.commit(
                        'setKid',
                        this.$store.state.selected_kid
                      );
                      this.$store.commit('setQuiz', {
                        url:
                          'http://j3a510.p.ssafy.io/images/' +
                          this.custom.file_path,
                        word: this.custom.caption_word[0],
                        word_kor: this.custom.caption_word_kor[0],
                      });
                      this.$store.commit('setTestCustomizing', {
                        image_id: this.tempdata,
                        word: this.custom.caption_word[0],
                      });
                      // console.log(this.$store.state.quiz);
                      // console.log(this.$store.state.test_customizing);
                      this.$router.push('/quiz');
                    } else {
                      this.isUploaded = false;
                    }
                  });
                });
              }
            })
            .catch((err) => {});
        }
      });
    },
    // 이미지 등록하기
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    // 이미지로 학습된 정보 받아오기
    onChangeImages(e) {
      this.overlay = !this.overlay;
      const file = e.target.files[0];
      const frm = new FormData();
      frm.append('files', file);
      frm.append('parent_id', this.$store.state.parent.id);
      http
        .post('https://j3a510.p.ssafy.io:8083/custom/quiz/make', frm, {
          headers: {
            'Content-Type': 'multipart/form-data',
            'access-control-allow-origin': '*',
          },
        })
        .then(({ data }) => {
          this.overlay = false;
          if (data.boundaries.length == 0) {
            this.$swal({
              title:
                '<span style="font-family: GmarketSansMedium;font-size:1.2vw;">사진에서 개체를 찾을 수 없습니다<br>다른 사진을 등록해주세요</span>',
              showCancelButton: false,
              confirmButtonText: '확인',
            }).then((result) => {});
          } else {
            this.custom = data;
            this.isUploaded = true;
            this.isDuplicate();
          }
        })
        .catch((err) => {
          this.overlay = false;
          this.$swal({
            title:
              '<span style="font-family: GmarketSansMedium;font-size:1.2vw;">사진에서 개체를 찾을 수 없습니다<br>다른 사진을 등록해주세요</span>',
            showCancelButton: false,
            confirmButtonText: '확인',
          }).then((result) => {});
        });

      this.img = URL.createObjectURL(file);
    },
    // 캡션 수정중
    onEditCaption() {
      this.selectedCaption.caption = this.custom.caption;
      this.selectedCaption.caption_kor = this.custom.caption_kor;
      this.isEditCaption = true;
    },
    // 캡션 수정완료
    editCaption() {
      this.custom.caption = this.selectedCaption.caption;
      this.custom.caption_kor = this.selectedCaption.caption_kor;
      this.clearCaption();
    },
    // 캡션 수정 모달 비우기
    clearCaption() {
      this.isEditCaption = false;
      this.selectedCaption.caption = '';
      this.selectedCaption.caption_kor = '';
    },
    // 단어 수정중
    onEditWord(index, target) {
      this.selectedWord.target = target;
      this.selectedWord.index = index;
      if (target == 'caption') {
        this.selectedWord.word = this.custom.caption_word[index];
        this.selectedWord.word_kor = this.custom.caption_word_kor[index];
      } else {
        this.selectedWord.word = this.custom.seg_word[index];
        this.selectedWord.word_kor = this.custom.seg_word_kor[index];
      }
      this.isEditWord = true;
    },
    // 단어 수정완료
    editWord() {
      var index = this.selectedWord.index;
      if (this.selectedWord.target == 'caption') {
        for (let i = 0; i < this.seg_word_duplicate.length; i++) {
          if (this.custom.caption_word[index] == this.seg_word_duplicate[i]) {
            this.seg_word_duplicate[i] = this.selectedWord.word;
            this.seg_word_duplicate_kor[i] = this.selectedWord.word_kor;
          }
        }
        this.custom.caption_word[index] = this.selectedWord.word;
        this.custom.caption_word_kor[index] = this.selectedWord.word_kor;
      } else {
        this.custom.seg_word[index] = this.selectedWord.word;
        this.custom.seg_word_kor[index] = this.selectedWord.word_kor;
      }
      this.clearWord();
    },
    // 단어 수정 모달 비우기
    clearWord() {
      this.isEditWord = false;
      this.selectedWord.target = '';
      this.selectedWord.index = '';
      this.selectedWord.word = '';
      this.selectedWord.word_kor = '';
    },
    // 캡션 단어와 세그 단어 중복시 수정 가능한 리스트에 안보여주는 대신 단방향 바인딩
    isDuplicate() {
      for (let i = 0; i < this.custom.caption_word.length; i++) {
        for (let j = 0; j < this.custom.seg_word.length; j++) {
          if (this.custom.caption_word[i] == this.custom.seg_word[j]) {
            this.seg_word_duplicate.push(this.custom.caption_word[i]);
            this.seg_word_duplicate_kor.push(this.custom.caption_word_kor[i]);
          }
        }
      }
      for (let k = 0; k < this.seg_word_duplicate.length; k++) {
        const idx1 = this.custom.seg_word.indexOf(this.seg_word_duplicate[k]);
        if (idx1 > -1) this.custom.seg_word.splice(idx1, 1);
        const idx2 = this.custom.seg_word_kor.indexOf(
          this.seg_word_duplicate_kor[k]
        );
        if (idx2 > -1) this.custom.seg_word_kor.splice(idx2, 1);
      }
    },
  },
};
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
* {
  font-family: 'GmarketSansMedium';

  text-align: left;
}
.upload {
  width: 65vw;
  height: 65vh;
  overflow: hidden;
  display: block;
  text-align: center;
}
.img-block {
  background-color: #f2f2f2;
  width: 48vw;
  height: 60%;
  float: bottom;
  margin-top: 1.5vw;
  margin-left: 8.4vw;
  margin-right: 8.4vw;
  margin-bottom: 2vw;
  display: block;
}
.upload-image {
  float: top;
  font-size: 2.5vw;
  width: 90%;
  height: 100%;
  text-align: center;
  background-color: #f2f2f2;
  box-shadow: rgba(1, 1, 1, 0.15) 0px 2px 3px 0px;
  &:hover {
    opacity: 0.8;
    transition: all 0.1s;
  }
}
.custom-bg {
  margin-top: 4vh;
  margin-left: 1vw;
  width: 62vw;
  height: 58vh;
  display: inline-block;
}

.custom-img {
  float: right;
  width: 35%;
  height: 100%;
  background-color: white;
  overflow: hidden;
}
.custom-img img {
  width: 22vw;
  height: 22vw;
  max-height: 21vw;
  border: 10px solid white;
  border-radius: 20%;
  float: right;
}

.custom-img button {
  margin-top: 10%;
  width: 100%;
  height: 15%;
  font-size: 1.7vw;
  text-align: center;
  background-color: yellowgreen;
  float: bottom;
  color: darkgreen;

  &:hover {
    opacity: 0.8;
    transition: all 0.1s;
  }
}
.custom-edu {
  float: right;
  width: 65%;
  height: 100%;
  overflow: auto;
  background-color: white;
}

.custom-title {
  float: top;
  padding-left: 1vw;
  padding-top: 1.2vw;
  padding-bottom: 1.2vw;
  font-size: 1vw;
  background-color: #f2f2f2;
}

::-webkit-scrollbar {
  width: 10px;
}
/* Track */
::-webkit-scrollbar-track {
  background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
