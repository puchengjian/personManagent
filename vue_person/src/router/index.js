import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/system/Login'
import Home from '@/components/Home'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/index',
    name: 'Home',
    component: Home,
    meta: {
      requireAuth: true
    }

  }, {
    path: '/login',
    name: 'Login',
    component: Login
  }]
})
