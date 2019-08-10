import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: { // 共享的数据
    show: false,
    user: JSON.parse(window.localStorage.getItem('user')),
    routes: []
  },
  mutations: {
    initMenu (state, menus) {
      state.routes = menus
    },
    login (state, user) {
      state.user = user
      window.localStorage.setItem('user', JSON.stringify(user))
    },
    logout (state) {
      window.localStorage.removeItem('user')
      sessionStorage.removeItem('token')
      state.routes = []
    }

  }
})
