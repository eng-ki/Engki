<template>
  <div>
    <gold-box v-on:goGetItem="goGetItem" v-if="isGoldBox" />
    <get-item v-else-if="isGetItem" />
  </div>
</template>

<script>
import GoldBox from '@/components/GoldBox.vue'
import GetItem from '@/components/GetItem.vue'

export default {
  name: 'Finish',
  components: {
    GoldBox,
    GetItem,
  },
  data: () => {
    return {
      isGoldBox: true,
      isGetItem: false,
    }
  },
  methods: {
    goGetItem() {
      ;(this.isGoldBox = false), (this.isGetItem = true)
    },
  },
}
</script>
