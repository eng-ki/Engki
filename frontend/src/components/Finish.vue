<template>
  <div>
    <gold-box />
    <get-item />
  </div>
</template>

<script>
import GoldBox from '@/components/GoldBox.vue';
import GetItem from '@/components/GetItem.vue';

export default {
  name: 'Finish',
  components: {
    GoldBox,
    GetItem,
  },
};
</script>
