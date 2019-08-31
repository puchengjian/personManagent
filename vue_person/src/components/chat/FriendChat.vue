<template>
  <div class="mainer">
    <el-container>
      <el-aside width="160px" style="background-color: rgb(240, 248, 248)">
        <!-- @click="toggleFriend(hr)"  -->
        <div
          v-for="user in dataList"
          :key="user.id"
          class="friendListDiv"
          v-bind:class="{ currentChatFriend: false }"
        >
          <img :src="user.photo" class="userfaceImg" />
          <el-badge is-dot="true">{{ user.userName }}</el-badge>
        </div>
        <div style="background-color: #20a0ff;height: 1px;width: 160px;" />
      </el-aside>
      <el-main style="padding-top: 0px;padding-bottom: 0px">
        <div class="chatDiv" ref="chatDiv" id="chatDiv">
          <p v-show="currentFriend.name">
            与
            <el-tag
              type="primary"
              size="small"
              style="margin-left: 5px;margin-right: 5px"
              >{{ currentFriend.name }}
            </el-tag>
            聊天中
          </p>
          <template v-for="msg in msgList">
            <!--发送来的消息-->
            <div
              :key="msg"
              style="display: flex;justify-content: flex-start;align-items: center;box-sizing: border-box;"
              v-if="msg.from == currentFriend.username"
            ></div>
            <!--发出去的消息-->
            <div
              :key="msg"
              v-if="msg.from != currentFriend.username"
              style="display: flex;justify-content: flex-end;align-items: center;box-sizing: border-box;"
            >
              <div
                style="display: inline-flex;border-style: solid;border-width: 1px;border-color: #20a0ff;border-radius: 5px;padding: 5px 8px 5px 8px;margin-right: 3px;background-color: #9eea6a"
              >
                {{ msg.msg }}
              </div>
              <img :src="currentUser.userface" class="userfaceImg" />
            </div>
          </template>
        </div>
        <div style="text-align: left;margin-top: 10px">
          <el-input
            v-model="msg"
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
      dataList: [],
      msg: '',
      currentUser: this.$store.state.user,
      currentFriend: {}
    }
  },
  created () {
    this.getDataList()
    this.initEditFormData()
  },
  methods: {
    handleLoading (val) { // loading
      this.loading = val
    },
    handleSearch () {
      this.page = 1
      this.size = 10
      this.getDataList()
    },
    async getDataList () {
      this.handleLoading(true)
      const res = await this.$get('/api/chat/friend')
      this.handleLoading(false)
      this.dataList = res.data
      // this.total = res.total
    },
    initEditFormData () {
      this.editFormData = {
        friendUserId: '',
        userName: '',
        phone: '',
        photo: ''
      }
    },
    async handleSearchFriend () {
      this.handleLoading(true)
      const res = await this.$get('/api/auth/friend/' + this.editFormData.phone)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.editFormData = res.data
      this.editFormData.friendUserId = res.data.id
      this.isTable = false
      this.isEdit = true
    },
    async handleIsTable () {
      this.initEditFormData()
      this.isTable = !this.isTable
      this.isEdit = false
    },
    async handleSave () {
      this.handleLoading(true)
      const res = await this.$post('/api/auth/friend', this.editFormData)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
      this.isTable = true
    },
    handleDel (id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.enterDel(id)
      }).catch(() => { })
    },
    async enterDel (id) {
      this.handleLoading(true)
      const res = await this.$delete('/api/auth/friend/' + id)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
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
