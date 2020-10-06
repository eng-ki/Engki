import Vue from 'vue';
import Vuex from 'vuex';
import http from '../utils/http-common.js';
import jwt_decode from 'jwt-decode';
import createPersistedState from 'vuex-persistedstate';
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isNew: null,
    parent: null,
    token: null,
    quiz: null,
    quiz_adv: null,
    kid: null,
    exp: 0,
    selected_kid: null,
  },
  getters: {
    getParent: function (state) {
      return state.parent;
    },
    getToken: function (state) {
      return state.token;
    },
    getQuiz: function (state) {
      return state.quiz;
    },
    getQuizAdv: function (state) {
      return state.quiz_adv;
    },
    getKid: function (state) {
      return state.kid;
    },
    getExp: function (state) {
      return state.exp;
    },
    getIsNew: function (state) {
      return state.isNew;
    },
    getSelectedKid: function (state) {
      return state.selected_kid;
    },
  },
  mutations: {
    setParent(state, payload) {
      state.parent = payload;
    },
    setToken(state, payload) {
      state.token = payload;
    },
    setQuiz(state, payload) {
      state.quiz = payload;
    },
    setQuizAdv(state, payload) {
      state.quiz_adv = payload;
    },
    setKid(state, payload) {
      state.kid = payload;
    },
    setExp(state, payload) {
      state.exp = state.exp + payload;
    },
    setIsNew(state, payload) {
      state.isNew = payload;
    },
    setSelectedKid(state, payload) {
      state.selected_kid = payload;
    },
  },
  actions: {
    kakaoLogin(context, { access_token }) {
      http
        .post('parents/login', { accessToken: access_token })
        .then(({ data }) => {
          context.commit('setIsNew', data.new);
          context.commit('setToken', data.token);
          var token = data.token;
          var parent_id = jwt_decode(token).sub;

          // console.log('parent_id : ' + parent_id);
          // console.log('jwt : ' + data.token);

          http
            .get('parents/' + parent_id, {
              headers: { 'X-AUTH-TOKEN': data.token },
            })
            .then(({ data }) => {
              context.commit('setParent', data);
            });
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  plugins: [createPersistedState()],
});
