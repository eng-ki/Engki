<template>
  <div>
    <break-time />
    <finish />
  </div>
</template>

<script>
import BreakTime from '@/components/BreakTime.vue';
import Finish from '@/components/Finish.vue';

export default {
  name: 'Etc',
  components: {
    BreakTime,
    Finish,
  },
};
</script>
