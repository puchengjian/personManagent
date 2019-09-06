<template>
  <div class="mainer">
    <el-container>
      <el-aside width="160px" style="background-color: rgb(240, 248, 248)">
        <div
          v-for="friend in friendList"
          :key="friend.id"
          class="friendListDiv"
          @click="toggleFriend(friend)"
          v-bind:class="{
            currentChatFriend: friend.id == currentFriend.id
          }"
        >
          <img :src="friend.photo" class="userfaceImg" />
          <el-badge :is-dot="!friend.read">{{ friend.userName }}</el-badge>
        </div>
        <div style="background-color: #20a0ff;height: 1px;width: 160px;" />
      </el-aside>
      <el-main style="padding-top: 0px;padding-bottom: 0px">
        <div class="chatDiv" ref="chatDiv" id="chatDiv">
          <p v-show="currentFriend.userName">
            与
            <el-tag
              type="primary"
              size="small"
              style="margin-left: 5px;margin-right: 5px"
              >{{ currentFriend.userName }}
            </el-tag>
            聊天中
          </p>
          <template v-for="(msg, index) in msgList">
            <!--发送来的消息-->
            <div
              :key="index"
              style="display: flex;justify-content: flex-start;align-items: center;box-sizing: border-box; margin-top: 6px;"
              v-if="msg.myUserId == currentFriend.id"
            >
              <img :src="currentFriend.photo" class="userfaceImg" />
              <div
                style="display: inline-flex;border-style: solid;border-width: 1px;border-color: #20a0ff;border-radius: 5px;padding: 5px 8px 5px 8px"
              >
                {{ msg.msg }}
              </div>
            </div>
            <!--发出去的消息-->
            <div
              :key="index"
              v-if="msg.myUserId != currentFriend.id"
              style="display: flex;justify-content: flex-end;align-items: center;box-sizing: border-box; margin-top: 6px;"
            >
              <div
                style="display: inline-flex;border-style: solid;border-width: 1px;border-color: #20a0ff;border-radius: 5px;padding: 5px 8px 5px 8px;margin-right: 3px;background-color: #9eea6a"
              >
                {{ msg.msg }}
              </div>
              <img :src="currentUser.photo" class="userfaceImg" />
            </div>
          </template>
        </div>

        <div style="text-align: left;margin-top: 10px">
          <el-input
            v-model="chatFormat.msg"
            placeholder="请输入内容"
            size="mini"
            style="width: 600px;"
            type="textarea"
            autosize
          ></el-input>
          <el-button
            :disabled="!currentFriend.id"
            size="small"
            type="primary"
            class="sendBtn"
            @click="sendMsg"
            ><i class="fa fa-send" style="margin-right: 15px"></i>发送
          </el-button>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  data () {
    return {
      currentUser: this.$store.state.user,
      chatFormat: {
        myUserId: this.$store.state.user.id,
        friendUserId: '',
        msg: ''
      }
    }
  },
  created () {
    this.getDataList()
  },
  computed: { // 数据没有变化，不会调用，返回之前的数据，性能更好
    msgList: {
      get: function () {
        return this.$store.state.msgList
      }
    },
    friendList: {
      get: function () {
        return this.$store.state.friendList
      }
    },
    currentFriend: {
      get: function () {
        return this.$store.state.currentFriend
      }
    }
  },
  watch: { // 监听，异步操作
    msgList () {
      document.getElementById('chatDiv').scrollTop = document.getElementById('chatDiv').scrollHeight
    }
  },
  methods: {
    handleLoading (val) { // loading
      this.loading = val
    },
    async toggleFriend (friend) {
      // 切换数据
      if (friend === this.currentFriend) {
        return
      }
      this.$store.commit('updateFriend', friend)
      this.chatFormat.friendUserId = friend.id
      this.handleMsgList(friend.id)
      this.handleEditRead()
      // console.log(res)
    },
    async handleMsgList (friendUserId) { // 获取聊天消息
      await this.$get('/api/chat/friend/msg', { friendUserId: friendUserId }).then((res) => {
        this.$store.commit('updateMsgList', res.data)
      })
      document.getElementById('chatDiv').scrollTop = document.getElementById('chatDiv').scrollHeight
    },
    async getDataList () { // 获取好友列表
      this.handleLoading(true)
      const res = await this.$get('/api/chat/friend')
      this.handleLoading(false)
      this.$store.commit('updateFriendList', res.data)
    },
    sendMsg () { // 发送消息
      this.$store.state.stomp.send('/ws/chat', {}, JSON.stringify(this.chatFormat))
      this.chatFormat.msg = ''
      this.handleEditRead()
    },
    async handleEditRead () { // 修改信息阅读状态
      await this.$put('/api/chat/friend', this.chatFormat)
    }
  }
}
</script>
<style>
.userfaceImg {
  width: 37px;
  height: 37px;
  border-radius: 30px;
  margin-right: 10px;
  margin-left: 5px;
}

.friendListDiv {
  display: flex;
  justify-content: flex-start;
  padding-left: 20px;
  box-sizing: border-box;
  align-items: center;
  width: 160px;
  height: 40px;
  border-color: #20a0ff;
  border-left-style: solid;
  border-top-style: solid;
  border-right-style: solid;
  border-width: 1px;
  cursor: pointer;
}

.chatDiv {
  border-color: #20a0ff;
  border-style: solid;
  border-radius: 5px;
  border-width: 1px;
  box-sizing: border-box;
  width: 700px;
  height: 450px;
  overflow-y: auto;
  padding-bottom: 50px;
}

.sendBtn {
  padding-left: 25px;
  padding-right: 25px;
}

.currentChatFriend {
  background-color: #dcdfe6;
}
</style>
