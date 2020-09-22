import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {},
    token: null,
  },
  getters: {
    getUser: function (state) {
      return state.user;
    },
    getToken: function (state) {
      return state.token;
    },
  },
  mutations: {
    setUser(state, payload) {
      state.user = payload;
    },
    setToken(state, payload) {
      state.token = payload;
    },
  },
  actions: {
    kakaoLogin(context, { access_token }) {},
  },
  modules: {},
});
