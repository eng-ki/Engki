<template>
  <div id="app">
    <div id="nav">
      <button class="audio-button" @click="audioPlay(isAudio)">
        <img v-if="isAudio" src="../public/img/icon/music.png" />
        <img v-else src="../public/img/icon/stopmusic.png" />
      </button>
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
        audio.volume = 0.5
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
  bottom: 0px;
  left: 0px;
}

.audio-button img {
  width: 4vw;
  opacity: 0.8;
  margin: 1vh;
}
</style>
