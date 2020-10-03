import Vue from 'vue';
import VueRouter from 'vue-router';
import LoginPage from '../views/LoginPage.vue';
import SelectKidPage from '../views/SelectKidPage.vue';
import ParentPage from '../views/ParentPage.vue';
import KidPage from '../views/KidPage.vue';
import SelectQuizPage from '../views/SelectQuizPage.vue';
import QuizPage from '../views/QuizPage.vue';
// import Test from '../views/Test.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'LoginPage',
    component: LoginPage,
  },
  {
    path: '/selectkid',
    name: 'SelectKidPage',
    component: SelectKidPage,
  },
  {
    path: '/parent',
    name: 'ParentPage',
    component: ParentPage,
  },
  {
    path: '/kid',
    name: 'KidPage',
    component: KidPage,
  },
  {
    path: '/selectquiz',
    name: 'SelectQuizPage',
    component: SelectQuizPage,
  },
  {
    path: '/quiz',
    name: 'QuizPage',
    component: QuizPage,
  },
  // {
  //   path: '/test',
  //   name: 'Test',
  //   component: Test,
  // },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
