<template>
  <div class="background">
    <img
      v-if="!isChangeProfile"
      @click="startTutorial()"
      class="page-title-img"
      id="tutorial2"
      src="../../public/img/icon/question-mark1.png"
    />
    <b-tooltip placement="bottom" target="tutorial2" triggers="hover">
      <span
        style="font-family: GmarketSansMedium; color: #f2f2f2; font-size: 0.8vw"
        >도움말</span
      >
    </b-tooltip>

    <div class="kid" v-if="!isChangeProfile" @click="goChangeProfile()">
      <div class="profile-image">
        <img :src="kid.icon" />
      </div>
      <div class="inventory-wrap">
        <img src="../../public/img/icon/chest.png" />
        <div class="inventory-text">캐릭터 변경하기</div>
      </div>
    </div>
    <div class="box">
      <div class="innerbox">
        <button
          class="parents-button start-button"
          style="
            top: 28%;
            left: 8.5%;
            position: absolute;
            width: 50%;
            height: 40%;
            font-size: 9vw;
            border-radius: 10vh;
            font-family: 'Jua', sans-serif;
            padding: 1%;
            color: #24282c;
          "
          @click="goSelectQuiz()"
        >
          공부 시작하기
        </button>
        <set-profile-image
          :kid="kid"
          v-if="isChangeProfile"
          @returnKidPage="goChangeProfile()"
        />
      </div>

      <img
        src="../../public/img/icon/kid_logout.png"
        style="height: 20vh; position: absolute; bottom: 0vh; left: 5vw"
        @click="gotoselectkid"
      />
    </div>

    <!-- 첫번째 튜토리얼 -->
    <b-modal
      :modal-class="kidmodal[0]"
      ref="my-modal1"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>공부 시작하기</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
      ><div class="arrow-down"></div>
      <div class="modal-body">
        <span>
          이 버튼을 누르면 퀴즈를 시작할 수 있습니다.<br />
          퀴즈를 풀고 경험치를 얻어 원하는 캐릭터를 획득해보세요.
        </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 첫번째 튜토리얼 끝-->

    <!-- 두번째 튜토리얼 -->
    <b-modal
      :modal-class="kidmodal[1]"
      ref="my-modal2"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>캐릭터 변경하기</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
      ><div class="arrow-right"></div>
      <div class="modal-body">
        <span>
          이곳을 클릭하면 캐릭터를 미리 보고 변경할 수 있습니다.<br />
          열심히 경험치를 쌓아 귀여운 캐릭터로 변경해보세요.<br />
        </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 두번째 튜토리얼 끝-->

    <!-- 세번째 튜토리얼 -->
    <b-modal
      :modal-class="kidmodal[2]"
      ref="my-modal3"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>공부 끝내기</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
      ><div class="arrow-left"></div>
      <div class="modal-body">
        <span>
          다른 아이로 학습하고 싶다면 이곳을 클릭해주세요.<br />
          프로필 선택 페이지로 이동합니다.<br />
        </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 세번째 튜토리얼 끝-->
  </div>
</template>

<script>
import SetProfileImage from '@/components/SetProfileImage.vue'
import Inventory from '@/components/Inventory.vue'

export default {
  name: 'KidPage',
  components: {
    SetProfileImage,
    Inventory,
  },
  data: () => {
    return {
      isChangeProfile: false,
      kid: null,
      stage: 1,
      limit: 3,
      isHideFooter: true,
      kidmodal: ['kidmodal1', 'kidmodal2', 'kidmodal3'],
    }
  },
  created() {
    this.kid = this.$store.state.kid
    // console.log(this.kid);
  },
  methods: {
    prevTutorial(stage) {
      if (stage > 1) {
        this.$refs['my-modal' + stage].hide()
        this.stage--
        this.showTutorial(this.stage)
      }
    },
    nextTutorial(stage) {
      this.$refs['my-modal' + stage].hide()
      if (stage < this.limit) {
        this.stage++
        this.showTutorial(this.stage)
      }
    },
    showTutorial(index) {
      this.$refs['my-modal' + index].show()
    },
    startTutorial() {
      this.stage = 1
      this.$refs['my-modal1'].show()
    },
    goChangeProfile() {
      this.isChangeProfile = !this.isChangeProfile
    },
    goSelectQuiz() {
      this.$router.push('/selectquiz')
    },
    gotoselectkid() {
      this.$router.push('/selectkid')
    },
  },
}
</script>

<style>
.kidmodal1 > div {
  position: absolute !important;
  top: 3vh !important;
  left: 20vw !important;
}

.kidmodal2 > div {
  position: absolute !important;
  top: 25vh !important;
  left: 37vw !important;
}

.kidmodal3 > div {
  position: absolute !important;
  bottom: 3vh !important;
  left: 15vw !important;
}
</style>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
/* 자녀페이지 틀 */
.background .box .innerbox {
  position: inherit;
}

/* 프로필 변경 버튼, 프로필 아이콘, 인벤토리 아이콘 영역*/
.kid {
  bottom:0px;
  right:1vw;
  position: absolute;
}

.profile-image img {
  width: 50%;
  height: 50%;
  object-fit: cover;
}

.inventory-wrap {
  margin-top: -5%;
  position: relative;
}

.inventory-wrap img {
  margin-top: 0px;
  // width: 90%;
  height: 100%;
  object-fit: cover;
  vertical-align: middle;
}

.inventory-wrap .inventory-text {
  text-align: center;
  position: absolute;
  top: 80%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 2vw;
}

.start-button {
  top: 30%;
  left: 10%;
  position: absolute;
  width: 50%;
  height: 40%;
  font-size: 9vw;
  border-radius: 10vh;
  font-family: 'Jua', sans-serif;
  padding: 1%;
  color: #24282c;
}

.page-title-img {
  position: absolute;
  top: 0px;
  right: 0px;
  width: 3%;
  margin-top: 3vh;
  margin-right: 3vw;
  &:hover {
    opacity: 0.6;
  }
  z-index: 1000;
}

.isButtonBlock {
  opacity: 0.6;
  pointer-events: none;
}
.modal-body {
  margin-top: -2vw;
  margin-bottom: 1vw;
}

.modal-body span {
  color: #263747;
  opacity: 0.9;
  font-family: GmarketSansMedium;
}
.modal-foot {
  float: left;
  color: gray;
  padding-left: 1vw;
  font-family: GmarketSansMedium;
}
.modal-foot2 {
  float: right;
  font-family: GmarketSansMedium;
}
.mymodal > div {
  position: fixed !important;
  top: 0 !important;
  left: -23vw !important;
}
</style>
