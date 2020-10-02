<template>
  <div>
    <!-- <button @click="stopCapture()">끝끝끝</button>
    <video style="display: none" id="player" controls autoplay></video>
    <canvas
      style="display: none"
      id="snapshot"
      width="320"
      height="240"
    ></canvas> -->
  </div>
</template>

<script>
// export default {
//   data: function () {
//     return {
//       player: null,
//       snapshotCanvas: null,
//       camTimer: null,
//     }
//   },
//   mounted() {
//     var handleSuccess = function (stream) {
//       player.srcObject = stream
//     }
//     // 5초마다 웹캠 키고, getEmotion() 함수 호출하기
//     this.camTimer = setInterval(() => {
//       navigator.mediaDevices
//         .getUserMedia({ video: true })
//         .then(handleSuccess, this.getEmotion())
//     }, 5000)
//   },
//   methods: {
//     stopCapture() {
//       clearInterval(this.camTimer)
//     },
//     getEmotion() {
//       // 캡처 이미지 base64 생성
//       var context = snapshot.getContext('2d')
//       context.drawImage(player, 0, 0, 320, 240)
//       var mydataURL = snapshot.toDataURL('image/jpg')

//       // 웹캠 끄기
//       // this.stopCamera()

//       // base64 데이터 디코딩
//       console.log(mydataURL)
//       var blobBin = atob(mydataURL.split(',')[1])
//       var array = []
//       for (var i = 0; i < blobBin.length; i++) {
//         array.push(blobBin.charCodeAt(i))
//       }
//       var file = new Blob([new Uint8Array(array)], { type: 'image/png' }) // Blob 생성
//       var formdata = new FormData() // formData 생성
//       formdata.append('file', file) // file data 추가

//       //여기서 backend로 formdata 보내고 return 값 true : 쉬는시간 false : 그냥 하기~
//     },
//   },
// }
</script>
