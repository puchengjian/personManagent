/* eslint-disable handle-callback-err */
<template>
  <div class="content">
    <div class="mainer">
      <div class="search-bar">
        <div class="search-swiper">
          <div class="search-item">
            <el-input
              placeholder="请输入搜索内容"
              size="medium"
              clearable
              v-model="queryListParam.searchValue"
              @input="handleSearch()"
              suffix-icon="el-icon-search"
              class="input-with-select"
            >
              <el-select
                v-model="queryListParam.searchKey"
                @change="handleSearch()"
                slot="prepend"
                placeholder="请选择"
              >
                <el-option label="好友姓名" value="user_name"></el-option>
              </el-select>
            </el-input>
          </div>
        </div>
        <div class="btnswiper">
          <el-button
            size="medium"
            :type="isTable ? 'primary' : 'info'"
            @click="handleIsTable"
          >
            {{ isTable ? "添加好友" : "返回列表" }}
          </el-button>
          <el-button
            :type="isTable ? 'danger' : 'primary'"
            size="medium"
            v-show="!isTable"
            @click="handleSave"
            >{{ isTable ? "导出数据" : "保存" }}</el-button
          >
        </div>
      </div>
      <div v-loading="loading">
        <div class="table-content" v-show="isTable">
          <el-table
            :data="dataList"
            border
            style="width: 100%"
            :max-height="580"
          >
            <el-table-column type="index" fixed width="80"> </el-table-column>
            <el-table-column prop="userName" label="好友名称" width="140">
            </el-table-column>
            <el-table-column label="头像" width="80">
              <template slot-scope="scope">
                <img :src="scope.row.photo" alt="" class="photo-img" />
              </template>
            </el-table-column>
            <el-table-column prop="userAge" label="年龄" width="200">
            </el-table-column>
            <el-table-column prop="phone" label="手机号" width="200">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="200">
            </el-table-column>
            <el-table-column label="操作" fixed="right" min-width="250">
              <template slot-scope="scope">
                <el-button
                  @click="handleDel(scope.row.id)"
                  type="danger"
                  size="mini"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            :page-sizes="[10, 20, 40, 60, 80, 100]"
            prev-text="上一页"
            next-text="下一页"
            :page-size="1"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          >
          </el-pagination>
        </div>

        <div class="add-content" v-show="!isTable">
          <el-form
            label-position="right"
            label-width="140px"
            :model="editFormData"
          >
            <el-form-item label="好友手机号">
              <el-input
                style="width: 69.5%"
                v-model="editFormData.phone"
              ></el-input>
              <el-button type="primary" @click="handleSearchFriend()"
                >查询</el-button
              >
            </el-form-item>
            <el-form-item label="好友姓名">
              <el-input v-model="editFormData.userName"></el-input>
            </el-form-item>
            <el-form-item class="last-content" label="头像">
              <div class="picBox">
                <img :src="editFormData.photo" alt="" />
              </div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      isTable: true,
      loading: false,
      isEdit: true,
      dataList: [],
      editFormData: {},
      total: 0,
      queryListParam: { // 查询列表参数列表
        searchKey: 'user_name',
        searchValue: '',
        page: 1,
        size: 10
      }
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
      const res = await this.$get('/api/chat/user/friend', this.queryListParam)
      this.handleLoading(false)
      this.dataList = res.data
      this.total = res.total
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
      const res = await this.$get('/api/chat/user/friend/' + this.editFormData.phone)
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
      const res = await this.$post('/api/chat/user/friend', this.editFormData)
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
      const res = await this.$delete('/api/chat/user/friend/' + id)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
    }
  }
}

</script>

<style lang="stylus">
.table-content {
  margin-top: 20px;
  border-radius: 8px 8px 0 0;
  overflow: hidden;
  box-shadow: 0px 1px 0px #ccc;
}

.photo-img {
  width: 45px;
  height: 45px;
  border-radius: 45px;
}

.add-content {
  border-radius: 8px px 0 0;
  overflow: hidden;
  background-color: #fff;
  padding: 40px 0;
  box-shadow: 0px 1px 0px #ccc;

  .el-form {
    width: 50%;
    margin: 0 auto;

    .el-input, .el-textarea {
      width: 85%;
    }

    .el-textarea__inner {
      height: 170px;
      resize: none;
    }

    .el-select {
      width: 85%;

      .el-input__inner {
        width: 118%;
      }

      .el-input__suffix {
        right: -15%;
      }
    }

    .el-input-number {
      width: 85%;

      .el-input {
        width: 100%;
      }
    }
  }

  .last-content {
    margin-bottom: 0;
  }
}
</style>
