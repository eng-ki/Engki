import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import VueSweetalert2 from 'vue-sweetalert2';
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
// Install BootstrapVue
Vue.use(BootstrapVue);
Vue.config.productionTip = false;
Vue.use(VueSweetalert2);
Vue.use(router);

new Vue({
  router,
  store,
  vuetify,
  BootstrapVue,
  render: (h) => h(App),
}).$mount('#app');
