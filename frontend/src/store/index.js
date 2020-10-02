import Vue from 'vue'
import Vuex from 'vuex'
import http from '../utils/http-common.js'
import jwt_decode from 'jwt-decode'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {},
    token: null,
  },
  getters: {
    getUser: function (state) {
      return state.user
    },
    getToken: function (state) {
      return state.token
    },
  },
  mutations: {
    setUser(state, payload) {
      state.user = payload
    },
    setToken(state, payload) {
      state.token = payload
    },
  },
  actions: {
    kakaoLogin(context, { access_token }) {
      http
        .post('/parents', { headers: { Authorization: access_token } })
        .then(({ data }) => {
          context.commit('setToken', data)
          // 'sub' 라는 key로 jwt token 디코딩해서 parents ID 받아오기
          var token = data
          var decoded = jwt_decode(token)
          parents_id = '123'
          http
            .get('/parents/' + parent_id, { headers: { Authorization: data } })
            .then(({ data }) => {
              context.commit('setUser', data)
            })
        })
    },
  },
  modules: {},
})
