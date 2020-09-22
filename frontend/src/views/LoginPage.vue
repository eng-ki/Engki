<template>
  <div>
    <login class="hello"></login>
    <set-email></set-email>
  </div>
</template>

<script>
import Login from '@/components/Login.vue';
import SetEmail from '@/components/SetEmail.vue';

export default {
  name: 'LoginPage',
  components: {
    Login,
    SetEmail,
  },
};
</script>

<style lang="scss" scoped>
.hello {
  background-color: blue;
}
</style>
