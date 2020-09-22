<template>
  <div class="background">
    <div class="box">
      <div class="innerbox">
        <div class="inventory"></div>
      </div>
    </div>
    <v-btn rounded large dark @click="returnKidPage()">돌아가기</v-btn>
  </div>
</template>
<script>
export default {
  name: 'Inventory',
  methods: {
    returnKidPage() {
      this.$emit('returnKidPage')
    },
  },
}
</script>
