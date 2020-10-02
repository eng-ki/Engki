<template>
  <div>
    <v-card class="mx-auto text-center report" color="green" max-width="55vw">
      <v-card-text>
        <v-sheet color="rgba(0, 0, 0, .12)">
          <v-sparkline
            :value="value"
            color="red"
            height="100"
            padding="24"
            stroke-linecap="round"
            smooth
          >
            <template v-slot:label="item"> ${{ item.value }} </template>
          </v-sparkline>
        </v-sheet>
      </v-card-text>
      <v-card-text>
        <div class="display-1 font-weight-thin">
          최근 24시간의 학습 중 감정변화 (대충 예시)
        </div>
      </v-card-text>
      <button></button>
      <v-divider></v-divider>

      <v-card-actions class="justify-center">
        <v-btn block text>{{ kid.name }}</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
export default {
  name: 'Report',
  props: {
    kid: null,
  },
  data: function () {
    return {
      value: [423, 446, 675, 510, 590, 610, 760],
    }
  },
}
</script>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
* {
  font-family: 'GmarketSansMedium';
  color: #4b4b4b;
}

.report {
  margin-left: 7vw;
  margin-top: -4vh;
}
</style>
