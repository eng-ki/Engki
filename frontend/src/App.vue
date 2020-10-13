<template>
  <div id="app">
    <div id="nav">
      <button class="audio-button" id="audio-id" @click="audioPlay(isAudio)">
        <img v-if="isAudio" src="../public/img/icon/music7.png" />
        <img v-else src="../public/img/icon/stopmusic2.png" />
      </button>
      <b-tooltip  placement="top" target="audio-id" triggers="hover">
       <span v-if="isAudio"
              style="
                font-family: GmarketSansMedium !important;
                color: #f2f2f2;
                font-size: 1.9vh;
                font-weight:100;
              "
              >배경음악 <span style="color:#665E66; 
                font-size: 2vh;">OFF</span></span
            >
        <span v-else
          style="
            font-family: GmarketSansMedium !important;
            color: #f2f2f2;
            font-size: 1.9vh;
            font-weight:100;
          "
          >배경음악 <span style="color:#F4B759; 
                font-size: 2vh;">ON</span></span
        >
      </b-tooltip>
      <!-- <router-link to="/">Home</router-link> | -->
      <!-- <router-link to="/about">About</router-link> -->
      <router-view />
    </div>
  </div>
</template>
<script>
var audio = new Audio('https://j3a510.p.ssafy.io/audio/bgm.mp3')
export default {
  data: () => {
    return {
      isAudio: false,
    }
  },
  methods: {
    audioPlay() {
      this.isAudio = !this.isAudio
      if (this.isAudio) {
        audio.play()
        audio.loop = true
        audio.volume = 0.2
      } else {
        audio.volume = 0
      }
    },
  },
}
</script>
<style>

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

/* #nav {
  padding: 30px;
} */

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
.audio-button {
  z-index: 5000;
  position: absolute;
  bottom: 10px;
  left: 10px;
}

.audio-button img {
  width: 3.5vw;
  opacity: 0.99;
  margin: 1vh;
}
</style>
