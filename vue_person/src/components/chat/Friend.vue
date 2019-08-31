<template>
  <div>
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
      style="margin-top: 10px"
    >
      <el-tab-pane label="好友管理" name="friendManagent"
        ><fm></fm
      ></el-tab-pane>
      <el-tab-pane label="好友聊天" name="friendChat"><fc></fc></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import FriendChat from '@/components/chat/FriendChat'
import FriendManagent from '@/components/chat/FriendManagent'
export default {
  data () {
    return {
      activeName: 'friendManagent'
    }
  },
  methods: {
    handleClick (tab, event) {
      console.log(tab, event)
    }
  },
  components: {
    'fc': FriendChat,
    'fm': FriendManagent
  }
}
</script>
