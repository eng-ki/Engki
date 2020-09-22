<template>
  <div>
    <set-profile-image />
    <inventory />
  </div>
</template>

<script>
import SetProfileImage from '@/components/SetProfileImage.vue';
import Inventory from '@/components/Inventory.vue';

export default {
  name: 'KidPage',
  components: {
    SetProfileImage,
    Inventory,
  },
};
</script>
