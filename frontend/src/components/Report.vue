<template>
  <div id="app">
    <v-app id="inspire">
      <v-row style="background-color: transparent; height: 65vh">
        <v-col cols="12" style="padding: 5px">
          <v-row class="row-title">
            <div class="cal-left">
              <v-btn icon class="ma-2 toprev" @click="toprevious();">
                <v-icon>mdi-chevron-left</v-icon>
              </v-btn>
            </div>
            <div class="cal">
              <v-toolbar-title v-if="$refs.calendar">
                {{ $refs.calendar.title }}
              </v-toolbar-title>
            </div>
            <div class="cal-right">
              <v-btn icon class="ma-2 tonext" @click="tonext();">
                <v-icon>mdi-chevron-right</v-icon>
              </v-btn>
            </div>
          </v-row>
          <v-row>
            <v-col cols="12" style="text-align: center">
              <v-sheet >
                <v-calendar
                  ref="calendar"
                  type="week"
                  v-model="value"
                  :weekdays="weekday"
                  :events="events"
                ></v-calendar>
              </v-sheet>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <div class="showemotion">
        <div class="x-axis">
          <div style="display: inline-block; float: left">
            <h3 style="text-align: left">감정 그래프 <span style="font-size:1.5vh"> 이번주 데이터만 조회 가능합니다. </span> </h3>
          </div>
          <div style="display: inline-block; float: right">
            <ul class="legend">
              <li v-for="(emotion,index) in emotions" v-bind:key="index">{{emo[emotion.text]}}</li>
            </ul>
          </div>
        </div>
        <div class="graphic">
            <div v-if="emotions" class="chart">
              <span v-for="(eachemotion,index) in emotions" v-bind:key="index" class="block" :title=emo[eachemotion.text]>
                <span class="val" v-text="eachemotion.value"></span>
              </span>
            </div>
          <div v-if="emotions==''" class="chart">
              <span class="block" style="background-color:yellow">
                <span class="val">데이터가 존재하지 않습니다</span>
              </span>
            </div>
        </div>
      </div>
    </v-app>
  </div>
</template>

<script>
import http from "../utils/http-common.js";
export default {
  name: "Report",
  props: {
    kid: '',
  },
  data: function () {
    return {
      test:0,
      thisweek: 0,
      type: "week",
      mode: "stack",
      weekday: [1, 2, 3, 4, 5, 6, 0],
      value: "",
      events: [],
      emotions: [],
      temp:[],
      emo:
      {'angry':'분노','disgusting':'역겨움','fearful':'무서움','happy':'행복','sad':'슬픔','surprising':'놀라움','neutral':'무표정'},
      edu: [
        {date:'',emotion:'', words:' '},
      ],
      dayclicked:'',
    };
  },
  watch:{
    kid:function(){
      this.refreshdata();
    },   
    thisweek:function(){
      this.refreshdata();
    },
  },
  created(){   
    this.dayclicked = new Date().getDay()-1;
    this.refreshdata(); 
  },
  mounted(){
    var vm = this;
    $(".v-calendar-daily__intervals-head").remove();
    $(".v-calendar-daily__body").remove();
    $(".v-calendar-daily_head-day-label button").click(function(event) {
      // $(this).addClass("colored");
      // $(this).removeClass("transparent");
      vm.dayclicked = $(this).parent().parent().index();
      vm.setEmotionGraph();
      // console.log(vm.dayclicked );
    })
  },
  methods: {
    toprevious(){ 
      // console.log('previous 클릭됨')
      // $(".v-calendar-daily_head-day").parent().addClass("colored");
      // console.log($('.v-calendar-daily_head-day').className);
      this.thisweek++;
      this.$refs.calendar.prev();
    },
    tonext(){
      this.thisweek--;
      this.$refs.calendar.next();
    },
    setEmotionGraph(){
      this.emotions=[];
      // console.log(this.dayclicked);
      // console.log(this.edu);
      for(var val in this.edu[this.dayclicked].emotion){
        this.emotions.push({text:val, value:this.edu[this.dayclicked].emotion[val]})
      }
        setTimeout(() => {
          $(function () {
          $(".val").each(function () {
            var text = $(this).text()+'%';
            if(text=='데이터가 존재하지 않습니다%'){
              $(this).parent().css("width", "100%");
            }else{
              $(this).parent().css("width", text);
            }
          });
        });
        }, 1);
      
      
    },
    refreshdata(){
      this.events = []; 
      http
        .get('/kids/'+this.kid.id+'/week/'+this.thisweek, {
          headers: { 'X-AUTH-TOKEN': this.$store.state.token },
        })
        .then(({ data }) => {
          this.edu = data;
          // console.log(this.edu)
          for(var val in data){
              this.temp = data[val].words;
              for (var w in this.temp){
                this.events.push({
                name: this.temp[w],
                start: data[val].date,
                color: "red",
                });
              }
          }
          this.setEmotionGraph();
        })
    },
  },
};
</script>

</script>
<style lang="scss">
@import "../assets/sass/base.scss";

.v-application--wrap {
  width: auto;
  min-height: 1vh !important;
}
.v-calendar-daily__interval {
  background-color: transparent;
}

.v-calendar-daily_head-day-label {
  border-bottom: #e0e0e0 1px solid;
}

.v-calendar-daily_head-day {
  height: 40vh;
}

.v-calendar-daily {
  .v-event {
    width: 90% !important;
    margin: 5%;
    height: 4vh !important;
    line-height: 4vh !important;
    .pl-1 {
      vertical-align: middle;
      font-size: 3vh !important;
    }
  }
}
.v-calendar-daily {
  border-left: none !important;
  .v-calendar-daily_head-day {
    border-left: #e0e0e0 1px solid;
  }

  .canvas {
    width: 100% !important;
    height: 10vh !important;
  }
  .row-title {
    height: 5vh;
  }
}
</style>
<style lang="scss" scoped>
* {
  font-family: "GmarketSansMedium";
  color: #4b4b4b;
  #inspire {
    background-color: transparent;
  }
}

.report {
  margin-left: 7vw;
  margin-top: -4vh;
}

.my-event {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  border-radius: 2px;
  background-color: #1867c0;
  color: #ffffff;
  border: 1px solid #1867c0;
  font-size: 12px;
  padding: 3px;
  cursor: pointer;
  margin-bottom: 1px;
  left: 4px;
  margin-right: 8px;
  position: relative;
}

.my-event.with-time {
  position: absolute;
  right: 4px;
  margin-right: 0px;
}

.cal-left {
  width: 8%;
  // background-color: yellow;
  display: inline-block;
  position: relative;
}

.cal {
  width: 80%;
  // background-color: green;
  display: inline-block;
  position: relative;
  // height: 100%;
  vertical-align: middle;
}

.cal-right {
  width: 8%;
  // background-color: yellow;
  display: inline-block;
  position: relative;
}

.showemotion {
  width: 100%;
  background-color: transparent;
  position: absolute;
  bottom: 10px;
}

/////////////////////@at-root

figure {
  margin: 0 auto;
  max-width: 1100px;
  position: relative;
}
.graphic {
  padding-left: 0.5vw;
  padding-right: 0.5vw;
  width: 100%;
}
@keyframes expand {
  from {
    width: 0%;
  }
  to {
    width: 100%;
  }
}
@media screen and (min-width: 768px) {
  @keyframes expand {
    from {
      width: 0%;
    }
    to {
      width: calc(100%);
    }
  }
}
.chart {
  overflow: hidden;
  width: 0%;
  animation: expand 1.5s ease forwards;
}
.row + .row .chart {
  animation-delay: 0.2s;
}
.row + .row + .row .chart {
  animation-delay: 0.4s;
}
.block {
  display: block;
  height: 4.5vh;
  line-height: 4.5vh;
  color: #fff;
  font-size: 0.75em;
  float: left;
  background-color: #5a7280;
  position: relative;
  overflow: hidden;
  opacity: 1;
  transition: opacity, 0.3s ease;
  cursor: pointer;
}
.block:nth-of-type(2),
.legend li:nth-of-type(2):before {
  background-color: #45b29d;
}
.block:nth-of-type(3),
.legend li:nth-of-type(3):before {
  background-color: #efc94c;
}
.block:nth-of-type(4),
.legend li:nth-of-type(4):before {
  background-color: #e27a3f;
}
.block:nth-of-type(5),
.legend li:nth-of-type(5):before {
  background-color: #df5a49;
}
.block:nth-of-type(6),
.legend li:nth-of-type(6):before {
  background-color: #962d3e;
}

.val {
  display: block;
  line-height: 1vh;
  // vertical-align: middle;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%);
}
.x-axis {
  text-align: center;
  display: flow-root;
  // padding: 0.5em 0 2em;
}
.y-axis {
  height: 20px;
  transform: translate(-32px, 170px) rotate(270deg);
  position: absolute;
  left: 0;
}
.legend {
  margin: 0 auto;
  padding: 0;
  font-size: 0.9em;
}
.legend li {
  display: inline-block;
  padding: 0.25em 1em;
  line-height: 1em;
}
.legend li:before {
  content: "";
  margin-right: 0.5em;
  display: inline-block;
  width: 8px;
  height: 8px;
  background-color: #334d5c;
}
@media screen and (min-width: 768px) {
  h6 {
    padding: 0;
    width: 75px;
    float: left;
    line-height: 100px;
  }
  .block {
    font-size: 1em;
  }
  .legend {
    width: 100%;
  }
  .colored{
    background-color: red!important;
  }
}
</style>
