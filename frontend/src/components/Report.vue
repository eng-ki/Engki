<template>
  <div id="app">
    <v-app id="inspire">
      <v-row style="background-color: transparent">
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
              <v-sheet height="40vh">
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

      <!-- <v-row style="background-color:transparent">
        <v-col cols="12">
          <v-row class="row-title">
            <v-col cols="3">
            </v-col>
            <v-col cols="6">
              감정분포도
            </v-col>
            <v-col cols="3">
            </v-col>
          </v-row>
          <v-row class="row-title">
            <v-col cols="12">
              <canvas id="myChart" style="height:10vh; width:70vw"></canvas>
            </v-col>
          </v-row>
        </v-col>
      </v-row> -->

      <div class="showemotion">
        <div>감정분포도</div>
        <canvas id="myChart" width="800" height="100"></canvas>
      </div>

      <!--       
      <div class="cal-right">
        <v-btn icon class="ma-2" @click="$refs.calendar.next()">
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </div> -->
    </v-app>
  </div>
</template>

<script>
import VueCharts from "vue-chartjs";
// import { Bar, Line } from 'vue-chartjs'
export default {
  name: "Report",
  props: {
    kid: null,
  },
  data: function () {
    return {
      // value: [423, 446, 675, 510, 590, 610, 760],
      // 위클리
      // today: "2019-01-08",
      // events: [
      //   {
      //     name: "Weekly Meeting",
      //     start: "2019-01-07 09:00",
      //     end: "2019-01-07 10:00",
      //   },
      //   {
      //     name: "Thomas' Birthday",
      //     start: "2019-01-10",
      //   },
      //   {
      //     name: "Mash Potatoes",
      //     start: "2019-01-09 12:30",
      //     end: "2019-01-09 15:30",
      //   },
      // ],
      //먼슬리
      type: "week",
      mode: "stack",
      weekday: [1, 2, 3, 4, 5, 6, 0],
      value: "",
      events: [],
      colors: [
        "blue",
        "indigo",
        "deep-purple",
        "cyan",
        "green",
        "orange",
        "grey darken-1",
      ],
      names: [
        "Meeting",
        "Holiday",
        "PTO",
        "Travel",
        "Event",
        "Birthday",
        "Conference",
        "Party",
      ],
    };
  },
  mounted() {
    $(".v-calendar-daily__intervals-head").remove();
    $(".v-calendar-daily__body").remove();
    $(function () {
      var ctx = document.getElementById("myChart").getContext("2d");
      var chart = new Chart(ctx, {
        type: "horizontalBar",
        data: {
          labels: ["감정"],
          datasets: [
            {
              label: "행복",
              data: [727],
              backgroundColor: "rgba(63,103,126,1)",
            },
            {
              label: "슬픔",
              data: [368],
              backgroundColor: "rgba(163,103,126,1)",
            },
            {
              label: "놀람",
              data: [1238],
              backgroundColor: "rgba(63,203,226,1)",
            },
            {
              label: "분노",
              data: [200],
              backgroundColor: "rgba(63,24,126,1)",
            },
            {
              label: "무표정",
              data: [368],
              backgroundColor: "rgba(1,129,77,1)",
            },
            {
              label: "역겨움",
              data: [1238],
              backgroundColor: "rgba(399,7,2,1)",
            },
          ],
        },

        options: {
          tooltips: {
            enabled: false,
          },
          responsive: false,
          legend: {
            display: false,
          },
          animation: {
            onComplete: function () {
              var chartInstance = this.chart;
              var ctx = chartInstance.ctx;
              ctx.textAlign = "left";
              ctx.font = "15px Open Sans";
              ctx.fillStyle = "#fff";

              Chart.helpers.each(
                this.data.datasets.forEach(function (dataset, i) {
                  var meta = chartInstance.controller.getDatasetMeta(i);
                  Chart.helpers.each(
                    meta.data.forEach(function (bar, index) {
                      data = dataset.data[index];
                      if (i == 0) {
                        ctx.fillText(data);
                      } else {
                        ctx.fillText(data, bar._model.x - 25, bar._model.y + 4);
                      }
                    }),
                    this
                  );
                }),
                this
              );
            },
          },
          scales: {
            xAxes: [
              {
                stacked: true,
                display: false,
              },
            ],
            yAxes: [
              {
                stacked: true,
                display: false,
              },
            ],
          },
        },
      });
    });
    // $(
    //   '<div class="v-calendar-daily__intervals-head" style="width: 60px;"></div>'
    // ).insertAfter($(".v-calendar-daily_head-day").last());
  },
  methods: {
    getEvents({ start, end }) {
      const events = [];
      const min = new Date(`${start.date}T00:00:00`);
      const max = new Date(`${end.date}T23:59:59`);
      const days = (max.getTime() - min.getTime()) / 86400000;
      const eventCount = this.rnd(days, days + 20);
      for (let i = 0; i < eventCount; i++) {
        const allDay = this.rnd(0, 3) === 0;
        const firstTimestamp = this.rnd(min.getTime(), max.getTime());
        const first = new Date(firstTimestamp - (firstTimestamp % 900000));
        events.push({
          name: this.names[this.rnd(0, this.names.length - 1)],
          start: first,
          end: first,
          color: this.colors[this.rnd(0, this.colors.length - 1)],
          timed: !allDay,
        });
      }
      this.events = events;
    },
    getEventColor(event) {
      return event.color;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
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
  height: auto;
}

.v-calendar-daily {
  .v-event {
    width: 90% !important;
    margin: 5%;
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
  background-color: transparent;
  position: absolute;
  bottom: 10px;
}
</style>
