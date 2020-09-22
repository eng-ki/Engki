<template>
  <div>
    <set-kid />
    <report />
    <upload-picture />
    <set-email />
  </div>
</template>

<script>
import SetKid from '@/components/SetKid.vue';
import Report from '@/components/Report.vue';
import UploadPicture from '@/components/UploadPicture.vue';
import SetEmail from '@/components/SetEmail.vue';

export default {
  name: 'ParentPage',
  components: {
    SetKid,
    Report,
    UploadPicture,
    SetEmail,
  },
};
</script>
