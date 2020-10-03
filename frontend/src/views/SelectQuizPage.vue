<template>
  <div>
    <select-sth option="quiz" />
  </div>
</template>

<script>
import SelectSth from '@/components/SelectSth.vue';

import http from '../utils/http-common.js';
export default {
  name: 'SelectQuizPage',
  components: {
    SelectSth,
  },
};
</script>
