import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/system/Login'
import Home from '@/components/Home'
import FriendChat from '@/components/friend/FriendChat'

Vue.use(Router)

export default new Router({
  mode: 'history', // 去掉url的#
  routes: [{
    path: '/',
    name: '主页',
    component: Home,
    meta: {
      requireAuth: true
    },
    children: [{
      path: '/friend/chat',
      name: '好友聊天',
      component: FriendChat,
      hidden: true,
      meta: {
        requireAuth: true
      }
    }]
  }, {
    path: '/login',
    name: 'Login',
    component: Login
  }]
})
