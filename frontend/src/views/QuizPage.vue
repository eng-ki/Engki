<template>
  <div class="background">
    <img
      src="../../public/img/icon/exit (2).png"
      width="90vw"
      style="float: right; margin-top: 1vh; margin-right: 3vw"
      id="goKid"
      @click="goKid()"
    />

    <b-tooltip placement="bottom" target="goKid" triggers="hover">
      <span
        style="font-family: GmarketSansMedium; color: #f2f2f2; font-size: 0.8vw"
        >퀴즈 그만하기</span
      >
    </b-tooltip>
    <vue-web-cam
      v-if="!isFinish"
      style="display: none"
      ref="webcam"
      :device-id="deviceId"
      width="0%"
      @cameras="onCameras"
      @camera-change="onCameraChange"
    />
 
    <etc
      v-if="(isBreakTime || isFinish) && !$store.state.test_customizing"
      :isBreakTime="isBreakTime"
      :isFinish="isFinish"
      v-on:continue="isBreakTime = false"
    />

      <b-tooltip placement="bottom" target="cameraon" v-if="isCameraOn" triggers="hover">
      <span
        style="font-family: GmarketSansMedium; color: #f2f2f2; font-size: 0.7vw"
        >감정 인식을 위해 실시간으로<br>녹화가 이루어지고 있습니다.<br>인식 후 숫자로만 기록됩니다.</span
      >
    </b-tooltip>
      <b-tooltip placement="bottom" target="cameraoff" triggers="hover">
      <span
        style="font-family: GmarketSansMedium; color: #f2f2f2; font-size: 0.8vw"
        >감정 인식을 위한 실시간 녹화가 중지되었습니다.</span
      >
    </b-tooltip>

    <div class="whiteboard">
      <img id="cameraon" src="../../public/img/icon/camera-on.png" v-if="isCameraOn" class="rec"/>
      <img id="cameraoff" src="../../public/img/icon/camera-off.png" v-else class="rec"/>
      <div class="board">
        <quiz-a
          :isDone="isDone"
          v-on:correct="
            isNextStage(true)
            playAnswer()
          "
          v-on:wrong="
            isNextStage(false)
            playWrong()
          "
          class="quiz"
          v-if="stage == 0"
        />
        <quiz-b
          :isDone="isDone"
          v-on:correct="
            isNextStage(true)
            playAnswer()
          "
          v-on:wrong="
            isNextStage(false)
            playWrong()
          "
          class="quiz"
          v-if="stage == 1"
        />
        <quiz-c
          :isDone="isDone"
          v-on:correct="
            isNextStage(true)
            playAnswer()
          "
          v-on:wrong="
            isNextStage(false)
            playWrong()
          "
          class="quiz"
          v-if="stage == 2"
        />
        <quiz-d
          :isDone="isDone"
          v-on:correct="
            isNextStage(true)
            playAnswer()
          "
          v-on:wrong="
            isNextStage(false)
            playWrong()
          "
          class="quiz"
          v-if="stage == 3"
        />
        <quiz-e
          :isDone="isDone"
          v-on:correct="
            isNextStage(true)
            playAnswer()
          "
          v-on:wrong="
            isNextStage(false)
            playWrong()
          "
          class="quiz"
          v-if="stage == 4"
        />
        <quiz-f
          :isDone="isDone"
          v-on:correct="
            isNextStage(true)
            playAnswer()
          "
          v-on:wrong="
            isNextStage(false)
            playWrong()
          "
          class="quiz"
          v-if="stage == 5"
        />

        <img src="../../public/img/etc/whiteboard.png" />
      </div>

      <span
        class="subject"
        v-for="(subject, index) in subjects"
        v-bind:key="index"
        v-if="stage == index"
        >{{ subject }}</span
      >


      <img
        v-if="!isBreakTime && !isFinish"
        class="tiki"
        src="../../public/img/icon/moving_tiki.gif"
      />

      
      <img
        class="done"
        @click="isDone = true"
        src="../../public/img/icon/done.png"
      />
      
      <img
        class="difficult"
        @click="isPass()"
        src="../../public/img/icon/difficult.png"
      />
    </div>

    <!-- 개인정보 제공동의 모달 -->
    <b-modal
      ref="personalAccept"
      :no-close-on-backdrop="isHideHeaderClose"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>개인 정보를 제공하는데 동의하시겠습니까?</span>"
      :hide-footer="isHideFooter"
      :hide-header-close="isHideHeaderClose"
      header-border-variant="0"
      >
      <div class="modal-body">
          <span style="font-size:0.8vw;">
          ENGKI 에서 {{this.$store.state.kid.name}} 님의 서비스 이용 중 감정 인식을 위해 실시간 녹화 정보를 <br>
          제공받습니다. 녹화 자료는 수치상으로만 기록되어 탈퇴 전까지 보관됩니다.</span>
      </div>
      <div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="warning"
            pill 
            :class="{ isButtonBlock: stage == 1 }"
            @click="accept();"
          >
             <span style="margin-left:1vw;"></span>
             동의하기<span style="margin-right:1vw;"></span>
          </b-button>
          <span style="margin-right:3vw;"></span>
          <b-button
            size="sm"
            pill 
            @click="decline();"
          >
             <span style="margin-left:1vw;"></span>
             취소하기<span style="margin-right:1vw;"></span>
          </b-button>
        </div>
      </div>
    </b-modal>
<!-- 개인정보 제공동의 모달 -->

  </div>
</template>

<script>
import QuizA from '@/components/QuizA.vue'
import QuizB from '@/components/QuizB.vue'
import QuizC from '@/components/QuizC.vue'
import QuizD from '@/components/QuizD.vue'
import QuizE from '@/components/QuizE.vue'
import QuizF from '@/components/QuizF.vue'
import Etc from '@/components/Etc.vue'
import http from '../utils/http-common.js'
import { WebCam } from 'vue-web-cam'
import { mapMutations } from 'vuex'

export default {
  name: 'ParentPage',
  components: {
    QuizA,
    QuizB,
    QuizC,
    QuizD,
    QuizE,
    QuizF,
    Etc,
    'vue-web-cam': WebCam,
  },
  data: () => {
    return {
      answer: '',
      isDone: false,
      isBreakTime: false,
      isFinish: false, 
      isCameraOn : false,
      isHideFooter : true,
      isHideHeaderClose : true,
      stage: 0,
      subjects: [
        '사진 속 단어를 배워보세요',
        '단어에 해당하는 그림을 모두 선택해주세요',
        '색칠된 부분에 해당하는 단어를 선택해주세요',
        '빈칸에 해당하는 단어를 선택해주세요',
        '사진의 내용과 일치하는 문장을 선택해주세요',
        '사진 속 문장을 단어로 만들어보세요',
      ],
      img: null,
      camera: null,
      cameras: null,
      deviceId: null,
      devices: [],
    }
  },
  watch: {
    isBreakTime: function (val) {
      if (val) {
        this.stopCapture()
        this.onStop()
      } else {
        this.startCapture()
        this.onStart()
      }
    },
    camera: function (id) {
      this.deviceId = id
    },
    devices: function () {
      const [first, ...tail] = this.devices
      if (first) {
        this.camera = first.deviceId
        this.deviceId = first.deviceId
      }
    },
  },
  beforeDestroy() {
    this.stopCapture()
    this.$store.state.test_customizing = false
  },
  computed: {
    device: function () {
      return this.devices.find((n) => n.deviceId === this.deviceId)
    },
  },
  created() {
    this.onStop()
  },
  mounted() {
    this.$store.state.exp = 0
    if (this.$store.state.test_customizing===false) {
      this.$refs['personalAccept'].show()
    }
  },
  methods: {
    ...mapMutations(['playAnswer', 'playWrong', 'playClick']),
    accept(){
      this.onStart()
      this.startCapture()
      this.$refs['personalAccept'].hide()
    },
    decline(){
      this.onStop()
      this.$refs['personalAccept'].hide()
    },
    goKid() {
      setTimeout(() => {
        this.stopCapture()
      }, 600)
      this.$router.push('/kid')
    },
    isNextStage(flag) {
      this.isDone = false
      if (flag) {
        this.stage++
      }

      if (this.stage == 6) {
        this.stage = 5
        if (!this.$store.state.test_customizing) {
          this.stopCapture()
          this.isFinish = true
        } else {
          this.$swal({
            title:
              '<div><span style="font-weight:100; font-size:2vw;">테스트가 완료되었습니다.</span><br><span  style="font-weight:100; font-size:2vw;">부모 페이지로 이동합니다.</span></div>',
            showCancelButton: false,
            confirmButtonText: '확인',
            timer: 3000,
          }).then((result) => {
            this.$router.push('/parent')
          })
        }
      }
    },
    startCapture() {
      this.camTimer = setInterval(() => {
        this.onCapture()
        var dt = new Date()

        dt =
          dt.getFullYear() +
          (dt.getMonth() + 1) +
          dt.getDate() +
          dt.getHours() +
          dt.getMinutes() +
          dt.getSeconds()

        var file = this.dataURLtoFile(this.img, dt)
        var frm = new FormData()
        frm.append('files', file)
        frm.append('kid_id', this.$store.state.kid.id)

        http
          .post('https://j3a510.p.ssafy.io:8083/custom/emotion', frm, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          })
          .then(({ data }) => {
            if (data == 'STOP') {
              this.isBreakTime = true
            }
          })
      }, 5000)
    },
    // 캡쳐 중지
    stopCapture() {
      clearInterval(this.camTimer)
    },
    // 모르겠어요 버튼 눌렀을때 완전 끝내기랑 다음으로가기
    isPass() {
      this.$swal({
        title:
          this.stage != 5
            ? '<div style="font-weight:100; font-size:2vw; margin-top:1vw;">다음 퀴즈로 넘어갈까요?</div>'
            : '<div style="font-weight:100; font-size:2vw; margin-top:1vw;">퀴즈 푸는걸 그만할까요?</div>',
        showCancelButton: true,
        confirmButtonText:
          '<span style="font-weight:100; font-size:1.5vw;">네</span>',
        cancelButtonText:
          '<span style="font-weight:100; font-size:1.5vw;">계속 풀래요</span>',

        showCloseButton: true,
      }).then((result) => {
        if (result.value) {
          this.isNextStage(true)
        } else {
          this.isNextStage(false)
        }
      })
    },
    onCapture() {
      this.img = this.$refs.webcam.capture()
    },
    onStop() {
      setTimeout(() => {
      this.isCameraOn = false
      this.$refs.webcam.stop();
      }, 600)
    },
    onStart() {
      
      this.isCameraOn = true
      this.onCameras(this.cameras)
      this.$refs.webcam.start();
    },
    onCameras(cameras) {
      if(this.isCameraOn){
      this.devices = cameras;
      }else{
      this.cameras = cameras
      }
    },
    onCameraChange(deviceId) {
      this.deviceId = deviceId
      this.camera = deviceId
    },
    dataURLtoFile(dataurl, fileName) {
      var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n)

      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }

      return new File([u8arr], fileName, { type: mime })
    },
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
.whiteboard .board {
  width: 83%;
  height: 80%;
  position: absolute;
  top: 11vh;
  left: 7.5vw;
  z-index: 1;
}

.whiteboard .board img {
  width: 100%;
  height: 100%;
  z-index: 1;
}

.quiz {
  float: left;
  position: absolute;
  left: 8vw;
  top: 10vh;
  width: 68vw;
  height: 60vh;
  z-index: 1000;
  padding: 5px;
}

.rec{
  z-index:3;
  width:6vw;
  top: 15vh;
  right: 13vw;
  position: absolute;
  margin:0px;
}

.whiteboard .tiki {
  width: 18%;
  position: absolute;
  top: 2vh;
  left: 5vw;
  z-index: 3;
}

.whiteboard .subject {
  width: 45%;
  height: 12%;
  top: 6vh;
  left: 27vw;
  position: absolute;
  z-index: 3;
  background-color: white;
  border-radius: 10vh;
  font-size: 2.5vw;
  vertical-align: middle;
  padding-top: 2.5vh;
  box-shadow: 0px 10px 5px 1px rgba(163, 163, 163, 0.404);
  color: #393939;
}

.whiteboard .done {
  bottom: 12vh;
  right: 22vw;
  position: absolute;
  z-index: 3;
  width: 8%;
}

.whiteboard .difficult {
  bottom: 12vh;
  right: 14vw;
  position: absolute;
  z-index: 3;
  width: 8%;
}

.modal-body {
  margin-top: -2vw;
  margin-bottom: 0.5vw;
}

.modal-body span {
  color: #263747;
  opacity: 0.9;
  font-family: GmarketSansMedium;
}
.modal-foot2 {
  text-align:center;
  font-family: GmarketSansMedium;
}
</style>
