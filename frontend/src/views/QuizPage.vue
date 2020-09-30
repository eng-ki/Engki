<template>
  <div class="background">
    <!-- etc : 종료 화면 / pause 화면 컴포넌트들 들어갈 자리-->
    <etc
      v-if="isBreakTime || isFinish"
      :isBreakTime="isBreakTime"
      :isFinish="isFinish"
      v-on:continue="isBreakTime = false"
    />

    <div class="whiteboard">
      <div class="board">
        <quiz-a
          :isDone="isDone"
          v-on:correct="isNextStage(true)"
          v-on:wrong="isNextStage(false)"
          class="quiz"
          v-if="stage == 0"
        />
        <quiz-b
          :isDone="isDone"
          v-on:correct="isNextStage(true)"
          v-on:wrong="isNextStage(false)"
          class="quiz"
          v-if="stage == 1"
        />
        <quiz-c
          :isDone="isDone"
          v-on:correct="isNextStage(true)"
          v-on:wrong="isNextStage(false)"
          class="quiz"
          v-if="stage == 2"
        />
        <quiz-d
          :isDone="isDone"
          v-on:correct="isNextStage(true)"
          v-on:wrong="isNextStage(false)"
          class="quiz"
          v-if="stage == 3"
        />
        <quiz-e
          :isDone="isDone"
          v-on:correct="isNextStage(true)"
          v-on:wrong="isNextStage(false)"
          class="quiz"
          v-if="stage == 4"
        />
        <quiz-f
          :isDone="isDone"
          v-on:correct="isNextStage(true)"
          v-on:wrong="isNextStage(false)"
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

      <!-- 잠깐 티키 눌렀을때 ETC (휴식) 페이지로 이동하게 해놓음 -->
      <img
        v-if="!isBreakTime && !isFinish"
        @click="isBreakTime = true"
        class="tiki"
        src="../../public/img/icon/moving_tiki.gif"
      />

      <!--다했어요 버튼 눌렀을때 1. isDone 변경 -> 2. 컴포넌트에서 정답인지 확인 -> 3. 다음 스테이지로-->
      <img
        class="done"
        @click="isDone = true"
        src="../../public/img/icon/done.png"
      />
      <!-- 모르겠어요 버튼 눌렀을때 props로 설정 줘야하는데 아직 안줌 -->
      <img class="difficult" src="../../public/img/icon/difficult.png" />
    </div>
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
  },
  data: () => {
    return {
      isDone: false, // 다했어요
      isHint: false, // 모르겠어요
      isBreakTime: false, // 쉬는시간
      isFinish: false, // 퀴즈 종료
      stage: 0, // stage 0~5 : 퀴즈
      subjects: [
        '사진 속 단어를 배워보세요',
        '단어에 해당하는 그림을 모두 선택해주세요',
        '단어에 해당하는 부분을 선택해주세요',
        '빈칸에 해당하는 단어를 선택해주세요',
        '사진의 내용과 일치하는 문장을 선택해주세요',
        '사진 속 문장을 단어로 만들어보세요',
      ],
    }
  },
  methods: {
    isNextStage(flag) {
      this.isDone = false
      // 정답일 경우 다음 스테이지
      if (flag) this.stage++
      if (this.stage == 6) {
        this.stage = 5
        this.isFinish = true
      }
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
</style>
