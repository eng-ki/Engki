<template>
  <div>
    <quiz-a-c />
    <quiz-b />
    <quiz-c />
    <quiz-d />
    <quiz-e />
    <quiz-f />
    <etc />
  </div>
</template>

<script>
import QuizAC from '@/components/QuizAC.vue';
import QuizB from '@/components/QuizB.vue';
import QuizD from '@/components/QuizD.vue';
import QuizE from '@/components/QuizE.vue';
import QuizF from '@/components/QuizF.vue';
import Etc from '@/components/Etc.vue';

export default {
  name: 'ParentPage',
  components: {
    QuizAC,
    QuizB,
    QuizD,
    QuizE,
    QuizF,
    Etc,
  },
};
</script>
