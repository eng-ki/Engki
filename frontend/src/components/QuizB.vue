<template>
  <div class="quiz-board">
    <div class="row" v-for="j in 2" :key="j">
      <div class="col-4 imagebox" v-for="(data,i) in datas" v-if="parseInt(i/3)==(j-1)" >
          <img 
            :src="data.url"
            class="img" :class="{ selected: data.selected }"
            @click="select(i)"
          />
      </div>
    </div>
    
    <div class="row">
      <div class="col-3">
      </div>
      <div class="col-6 quiz-text">
        DOG
      </div>
      <div class="col-3">
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ['isDone','answer'],
  mounted() {
    //api 호출
  },
  created(){
    this.isDone=false;
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) this.$emit('correct')
      else this.$emit('wrong')
    },
  },
  methods: {
    isCorrect() {
      // console.log(this.answer)
      var count = 0
      for(var data in this.datas){
        // console.log(this.datas[data].word)
        if(this.datas[data].selected){
          count++
          if(this.datas[data].word!=this.answer){
            return false
          }
        }
      }
      if(count!=3){
        // isDone 한번 누르면 두번 누른것처럼 인식되는데 왤까요
        alert('3개를 선택해주세요')
        return false
      }
      return true
    },
    select(index) {
      this.datas[index].selected = !this.datas[index].selected ;
    },
  },data: function () {
    return {
      selectedIndex: -1,
      datas: [
        {
          word: 'dog',
          url: '/img/etc/puppy.jpg',
          selected: false,
        },
        {
          word: 'cat',
          url: '/img/icon/theme/cat.png',
          selected: false,
        },
        {
          word: 'cat',
          url: '/img/icon/theme/cat.png',
          selected: false,
        },
        {
          word: 'dog',
          url: '/img/etc/puppy.jpg',
          selected: false,
        },
        {
          word: 'cat',
          url: '/img/icon/theme/cat.png',
          selected: false,
        },
        {
          word: 'dog',
          url: '/img/etc/puppy.jpg',
          selected: false,
        },
      ],
    }
  },
}
</script>
<style lang="scss" scoped>
.quiz-board {
  margin-top:-3vh;
  // position:absolute;
  // top:7vh;
  // left:20vw;
  // transform: translate(0, -50%);
}
.quiz-text{
  margin-top:-3vh;
  font-size:10vh;
  
}
.imagebox{
  padding:0vw;
}
.img{
    // display:inherit;
    width: 13vw;
    height:13vw; 
    border-radius: 5vw;
    border: 1vw solid transparent;
    // background-color:yellow;
  }
  .selected {
    border: 0.5vw solid #f4b759;
  }
</style>