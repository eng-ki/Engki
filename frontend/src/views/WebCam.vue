<template>
  <div class="container">
    <div class="row">
      <div class="col-md-6">
        <h2>Current Camera</h2>
        <code v-if="device">{{ device.label }}</code>
        <div class="border">
          <vue-web-cam
            ref="webcam"
            :device-id="deviceId"
            width="100%"
            @cameras="onCameras"
            @camera-change="onCameraChange"
          />
        </div>

        <div class="row">
          <div class="col-md-12">
            <select v-model="camera">
              <option>-- Select Device --</option>
              <option
                v-for="device in devices"
                :key="device.deviceId"
                :value="device.deviceId"
              >
                {{ device.label }}
              </option>
            </select>
          </div>
          <div class="col-md-12">
            <button type="button" class="btn btn-primary" @click="onCapture">
              Capture Photo
            </button>
            <button type="button" class="btn btn-danger" @click="onStop">
              Stop Camera
            </button>
            <button type="button" class="btn btn-success" @click="onStart">
              Start Camera
            </button>
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <h2>Captured Image</h2>
        <figure class="figure">
          <img :src="img" class="img-responsive" />
        </figure>
      </div>
    </div>
  </div>
</template>

<script>
import { WebCam } from 'vue-web-cam';
export default {
  name: 'App',
  components: {
    'vue-web-cam': WebCam,
  },
  data() {
    return {
      img: null,
      camera: null,
      cameras: null,
      deviceId: null,
      devices: [],
      isCameraOn:false,
    };
  },
  computed: {
    device: function () {
      return this.devices.find((n) => n.deviceId === this.deviceId);
    },
  },
  watch: {
    camera: function (id) {
        this.deviceId = id;
    },
    devices: function () {
     
      // Once we have a list select the first one
      const [first, ...tail] = this.devices;
      if (first) {
        this.camera = first.deviceId;
        this.deviceId = first.deviceId;
      }
    },
  },
  methods: {
    onCapture() {
      this.img = this.$refs.webcam.capture();
    },
    onStop() {
      setTimeout(() => {
      this.isCameraOn = false
      this.$refs.webcam.stop();
      }, 600)
      
    },
    onStart() {
      this.isCameraOn = true
      this.onCameras(this.cameras)
      this.$refs.webcam.start();
    },
    onCameras(cameras) {
      if(this.isCameraOn){
      this.devices = cameras;
      }else{
        this.cameras = cameras
      }
      // console.log('On Cameras Event', cameras)
    },
    onCameraChange(deviceId) {
      this.deviceId = deviceId;
      this.camera = deviceId;
      // console.log('On Camera Change Event', deviceId)
    },
  },
};
</script>
