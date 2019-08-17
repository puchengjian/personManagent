// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css'
import axios from 'axios'
import { post, get, put, del, EXPORT } from './request/api'
import './request/http'
import { initMenu } from './utils/utils'
import '../src/assets/styles/variable.styl'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.prototype.$axios = axios
Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.$put = put
Vue.prototype.$delete = del
Vue.prototype.$export = EXPORT

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    return next()
  }
  var token = sessionStorage.getItem('token')
  if (document.cookie === '') {
    return next('/login')
  }

  if (!token) {
    return next('/login')
  }

  initMenu(router, store)
  if (to.path === '/') {
    next('/index')
  }
  next()
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
