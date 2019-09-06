/* eslint-disable standard/object-curly-even-spacing */
/* eslint-disable no-undef */
import Vue from 'vue'
import Vuex from 'vuex'
import { put } from '../request/api'
import SockJS from 'sockjs-client'
import '../lib/stomp'

Vue.use(Vuex)

export default new Vuex.Store({
  state: { // 共享的数据
    show: false,
    user: JSON.parse(window.localStorage.getItem('user')),
    currentFriend: null,
    routes: [],
    stomp: null,
    msgList: [],
    friendList: []
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
      state.stomp = null
      state.msgList = []
      state.friendList = []
    },
    updateMsgList (state, msgList) {
      state.msgList = msgList
    },
    updateFriendList (state, friendList) {
      state.friendList = friendList
    },
    updateFriend (state, friend) {
      state.currentFriend = friend
    },
    disconnect (state) {
      if (state.stomp !== null) state.stomp.disconnect()
    }

  },
  actions: { // 异步方法
    connect (context) {
      context.commit('disconnect')
      console.log('执行连接')
      const sock = new SockJS('/ws/endpointChat?token=' + sessionStorage.getItem('token'))
      const state = context.state
      state.stomp = Stomp.over(sock)
      state.stomp.connect({}, frame => {
        state.stomp.subscribe('/user/queue/chat', message => { // 监听好友聊天消息
          const msg = JSON.parse(message.body)
          if (msg.friendUserId === state.currentFriend.id || msg.myUserId === state.currentFriend.id) { state.msgList.push(msg) }
        })
        state.stomp.subscribe('/user/queue/friend', message => { // 监听好友聊天列表
          state.friendList = JSON.parse(message.body)
        })
        state.stomp.subscribe('/user/queue/chat/read', message => { // 监听发送信息，消息已读状态变化
          const chatFormat = JSON.parse(message.body)
          if (state.currentFriend.id === chatFormat.friendUserId) put('/api/chat/friend', chatFormat)
        })
        state.stomp.debug = function () {

        }
      }, failedMsg => {
        // console.log('失败' + failedMsg)
        connect(context)
      })
    }

  }
})
