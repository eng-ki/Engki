import Vue from 'vue'
import Vuex from 'vuex'
import http from '../utils/http-common.js'
import jwt_decode from 'jwt-decode'
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    parent: null,
    token: null,
    quiz: null,
    quiz_adv: null,
    kid: null,
  },
  getters: {
    getParent: function (state) {
      return state.parent
    },
    getToken: function (state) {
      return state.token
    },
    getQuiz: function (state) {
      return state.quiz
    },
    getQuizAdv: function (state) {
      return state.quiz_adv
    },
    getKid: function (state) {
      return state.kid
    },
  },
  mutations: {
    setParent(state, payload) {
      state.parent = payload
    },
    setToken(state, payload) {
      state.token = payload
    },
    setQuiz(state, payload) {
      state.quiz = payload
    },
    setQuizAdv(state, payload) {
      state.quiz_adv = payload
    },
    setKid(state, payload) {
      state.kid = payload
    },
  },
  actions: {
    kakaoLogin(context, { access_token }) {
      http
        .post('parents/login', { accessToken: access_token })
        .then(({ data }) => {
          console.log('jwt : ' + data)
          context.commit('setToken', data)
          // 'sub' 라는 key로 jwt token 디코딩해서 parents ID 받아오기
          var token = data
          var parent_id = jwt_decode(token).sub
          console.log('parent_id : ' + parent_id)
          http
            .get('parents/' + parent_id, { headers: { 'X-AUTH-TOKEN': data } })
            .then(({ data }) => {
              context.commit('setParent', data)
            })
        })
        .catch((err) => {
          console.error(err)
        })
    },
  },
  plugins: [createPersistedState()],
})
