import Vue from 'vue';
import Vuex from 'vuex';
import http from '../utils/http-common.js';
import jwt_decode from 'jwt-decode';
import createPersistedState from 'vuex-persistedstate';
Vue.use(Vuex);

const answer = new Audio('https://j3a510.p.ssafy.io/audio/answer.wav')
const wrong = new Audio('https://j3a510.p.ssafy.io/audio/wrong.wav')
const click = new Audio('https://j3a510.p.ssafy.io/audio/click.wav')

export default new Vuex.Store({
  state: {
    isNew: null,
    parent: null,
    token: null,
    theme: null,
    quiz: null,
    quiz_b: null,
    quiz_c: null,
    quiz_adv: null,
    kid: null,
    exp: 0,
    selected_kid: null,
    is_test: null,
  },
  getters: {
    getParent: function (state) {
      return state.parent;
    },
    getToken: function (state) {
      return state.token;
    },
    getTheme: function (state) {
      return state.theme;
    },
    getQuiz: function (state) {
      return state.quiz;
    },
    getQuizB: function (state) {
      return state.quiz_b;
    },
    getQuizC: function (state) {
      return state.quiz_c;
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
    getIsTest: function (state) {
      return state.is_test;
    },
  },
  mutations: {
    setParent(state, payload) {
      state.parent = payload;
    },
    setToken(state, payload) {
      state.token = payload;
    },
    setTheme(state, payload) {
      state.theme = payload;
    },
    setQuiz(state, payload) {
      state.quiz = payload;
    },
    setQuizB(state, payload) {
      state.quiz_b = payload;
    },
    setQuizC(state, payload) {
      state.quiz_c = payload;
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
    setIsTest(state, payload) {
      state.is_test = payload;
    },
    playAnswer() {
      answer.play()
    },
    playClick() {
      click.play()
    },
    playWrong() {
      wrong.play()
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

          console.log('parent_id : ' + parent_id);
          console.log('jwt : ' + data.token);

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
