<template>
  <div>
    <login :isNew="isNew" v-if="isNew == false" @child="showSetEmail"></login>
    <set-email v-if="isNew == true"  v-on:isNew="showSetEmail()"></set-email>
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
  data: () => {
    return {
      isNew: false,
    }
  },
   methods : {
    showSetEmail(){
      this.isNew=true;
    }
   },
};
</script>
