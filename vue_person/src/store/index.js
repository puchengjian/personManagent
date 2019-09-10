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
    friendList: [],
    nfDot: false
  },
  mutations: {
    initMenu (state, menus) {
      state.routes = menus
    },
    updateNfDot (state, nfDot) {
      state.nfDot = nfDot
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
      const sock = new SockJS('/ws/endpoint?token=' + sessionStorage.getItem('token'))
      const state = context.state
      state.stomp = Stomp.over(sock)
      state.stomp.connect({}, frame => {
        state.stomp.subscribe('/user/queue/friend/msg', message => { // 监听好友聊天消息
          const msg = JSON.parse(message.body)
          if (msg.friendUserId === state.currentFriend.id || msg.myUserId === state.currentFriend.id) { state.msgList.push(msg) }
        })
        state.stomp.subscribe('/user/queue/friend/chat/list', message => { // 监听好友聊天列表
          context.commit('updateFriendList', JSON.parse(message.body))
        })
        state.stomp.subscribe('/user/queue/friend/msg/read', message => { // 监听发送信息后，消息已读状态
          const chatFormat = JSON.parse(message.body)
          // 当前聊天人等于监听的朋友Id，说明正在聊天，更新阅读状态
          if (state.currentFriend.id === chatFormat.friendUserId) put('/api/friend/chat/read/friend', chatFormat)
        })
        state.stomp.subscribe('/user/queue/read/friend', message => { // 监听消息是否有未读
          context.commit('updateNfDot', JSON.parse(message.body))
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
