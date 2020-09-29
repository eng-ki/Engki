<template>
  <div>
      <div class="quiz-img">
      <img :src="quiz.url" @click="soundAndTranslation()"/>
      <div class="quiz-text" >{{word}}</div>
      </div>
  </div>
</template>
<script>
export default {
  props: {
    isDone: false,
    stage: 0, // 0일때 quiz A, 2일때 quiz C (AC에만 있는 props)
  },
  data: () => {
    return {
      quiz: null,
      selectedIndex: -1,
      showKorean: false,
      word: '',
    }
  },
  created(){
    //api 호출
    this.quiz = {
      url: '/img/etc/puppy.jpg',
      word_eng: 'dog',
      word_kor: '강아지',
    }
    this.word=this.quiz.word_eng;
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) this.$emit('correct')
      else this.$emit('wrong')
    },
    showKorean: function(val){
      if(this.showKorean){
        this.word = this.quiz.word_kor;
      }else{
        this.word = this.quiz.word_eng;
      }
    },
  },
  methods: {
    isCorrect() {
      // 다했어요 버튼이 클릭됐을때 호출되는 함수, 정답이 맞으면 true, 틀리면 false 리턴
      return true
    },
    soundAndTranslation(){
      this.showKorean=true;
      setTimeout(()=>{
      this.showKorean=false; 
      }, 1000);
    }
  },
}
</script>

<style lang="scss" scoped>
.quiz-img{
  position: absolute;
  top: 30vh;
  left: 50%;
  transform: translate(-50%, -50%);
}
.quiz-img img {
  position: relative;
  width: 45vh;
  border-radius: 5vw;
  border: 1vw solid #d5d5d5;
}
.quiz-img .quiz-text {
  position: relative;
  margin-top:2vh;
  width: 45vh;
  font-size: 10vh;
}
</style>