<template>
  <div id="app">
    <v-app id="inspire">
      <v-row style="background-color: transparent; height: 65vh">
        <v-col cols="12" style="padding: 5px">
          <v-row class="row-title">
            <div class="cal-left">
              <v-btn icon class="ma-2" @click="$refs.calendar.prev()">
                <v-icon>mdi-chevron-left</v-icon>
               
              </v-btn>
            </div>
            <div class="cal">
              <v-toolbar-title v-if="$refs.calendar">
                {{ $refs.calendar.title }}
              </v-toolbar-title>
            </div>
            <div class="cal-right">
              <v-btn icon class="ma-2" @click="$refs.calendar.next()">
                <v-icon>mdi-chevron-right</v-icon>
              </v-btn>
            </div>
          </v-row>
          <v-row>
            <v-col cols="12" style="text-align: center">
              <v-sheet height="50vh">
                <v-calendar
                  ref="calendar"
                  type="week"
                  v-model="value"
                  :weekdays="weekday"
                  :events="events"
                  @change="getEvents"
                ></v-calendar>
              </v-sheet>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <div class="showemotion">
        <div class="x-axis">
          <div style="display: inline-block; float: left">
            <h3 style="text-align: left">감정 그래프</h3>
          </div>
          <div style="display: inline-block; float: right">
            <ul class="legend">
              <li v-for="(emotion,index) in emotions" v-bind:key="index">{{emotion.text}}</li>
            </ul>
          </div>
        </div>
        <div class="graphic">
          <div class="row">
            <div class="chart">
              <span v-for="(eachemotion,index) in edu[0].emotion" v-bind:key="index" class="block" :title=index>
                <span class="val" v-text="eachemotion"></span>
              </span>
            </div>


            <!-- {{edu[0].emotion}} -->
            <!-- <div class="chart">
              <span v-for="(eachemotion,index) in edu[0].emotion" v-bind:key="index" class="block" :title=index>
                 <span class="value">{{eachemotion}}</span>
              </span>
            </div> -->

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
    kid: null,
  },
  data: function () {
    return {
      type: "week",
      mode: "stack",
      weekday: [1, 2, 3, 4, 5, 6, 0],
      value: "",
      events: [],
      colors: [
        "cyan",
      ],
      names: [
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
      ],
      emotions:
      [],
      // emo:
      // {'angry':'분노','disgusting':'역겨움'},
      edu: [
        {date:'',emotion:'', words:' '},
      ],
    };
  },
  watch:{
    kid:function(){
      http
        .get('/kids/'+this.$store.state.selected_kid+'/week/0', {
          headers: { 'X-AUTH-TOKEN': this.$store.state.token },
        })
        .then(({ data }) => {
          // $(".legend li").remove();
          this.emotions=[];
          this.edu = data
          for(var val in data[0].emotion){
            // console.log(val + ' ' + data[0].emotion[val])
            this.emotions.push({text:val, value:data[0].emotion[val]})
          }
          console.log(this.emotions)
          setTimeout(() => {
            $(function () {
            $(".val").each(function () {
              var text = $(this).text()+'%';
              $(this).parent().css("width", text);
            });
          });
          }, 100);
        })
    }
  },
  created(){    
     http
        .get('/kids/'+this.$store.state.selected_kid+'/week/0', {
          headers: { 'X-AUTH-TOKEN': this.$store.state.token },
        })
        .then(({ data }) => {
          this.edu = data
          for(var val in data[0].emotion){
            this.emotions.push({text:val, value:data[0].emotion[val]})
          }
          console.log(this.edu)
          setTimeout(() => {
            $(function () {
            $(".val").each(function () {
              var text = $(this).text()+'%';
              $(this).parent().css("width", text);
            });
          });
          }, 5);
        })
  }
  ,mounted() {
    $(".v-calendar-daily__intervals-head").remove();
    $(".v-calendar-daily__body").remove();
  },
  methods: {
    getEvents({ start, end }) {
      this.events.push({
          name: "A",
          start: "2020-10-06",
          color: "red",
      });
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
  height: 4.5vh;;
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

.value {
  display: block;
  line-height: 1vh;
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
}
</style>
