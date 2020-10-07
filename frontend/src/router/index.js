import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from '../views/LoginPage.vue'
import SelectKidPage from '../views/SelectKidPage.vue'
import ParentPage from '../views/ParentPage.vue'
import KidPage from '../views/KidPage.vue'
import SelectQuizPage from '../views/SelectQuizPage.vue'
import QuizPage from '../views/QuizPage.vue'
import WebCam from '../views/WebCam.vue'
// import Test from '../views/Test.vue';
import store from '../store/index.js'

Vue.use(VueRouter)

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
    path: '/webcam',
    name: 'WebCam',
    component: WebCam,
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
  {
    path: '*',
    redirect: '/',
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

// 로그인 후에 이용 가능
router.beforeEach((to, from, next) => {
  if (to.name !== 'LoginPage' && store.getters.getToken == null)
    next({ name: 'LoginPage' })
  else next()
})

export default router
