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
              suffix-icon="el-icon-search"
              class="input-with-select"
            >
              <el-select
                v-model="queryListParam.searchKey"
                slot="prepend"
                placeholder="请选择"
              >
                <el-option label="用户名" value="account"></el-option>
                <el-option label="姓名" value="userName"></el-option>
              </el-select>
            </el-input>
          </div>
        </div>
        <div class="btnswiper">
          <el-button
            type="primary"
            v-show="isTable"
            @click="handleIsSave"
            size="medium"
            >添加成员</el-button
          >
          <el-button
            size="medium"
            :type="isTable ? 'primary' : 'info'"
            @click="handleIsTable"
          >
            {{ isTable ? "导出数据" : "返回列表" }}
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
      <div>
        <div class="table-content" v-show="isTable">
          <el-table :data="dataList" border style="width: 100%">
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="account" label="用户名" width="140">
            </el-table-column>
            <el-table-column prop="userName" label="姓名" width="140">
            </el-table-column>
            <el-table-column prop="userAge" label="年龄" width="80">
            </el-table-column>
            <el-table-column prop="phone" label="手机号" width="140">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="140">
            </el-table-column>
            <el-table-column prop="createTime" label="日期" width="200">
            </el-table-column>
            <el-table-column fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button @click="handleIsEdit(scope.row.id)" size="mini"
                  >编辑</el-button
                >
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
            :total="1000"
          >
          </el-pagination>
        </div>

        <div class="add-content" v-show="!isTable">
          <div class="formbox">
            <el-form
              label-position="right"
              label-width="140px"
              :model="editFormData"
            >
              <el-form-item label="用户名">
                <el-input
                  v-model="editFormData.account"
                  :disabled="isEdit"
                  placeholder="如：pzy"
                ></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item v-show="!isEdit" label="密码">
                <el-input
                  v-model="editFormData.password"
                  placeholder="如：123456"
                ></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item label="姓名">
                <el-input
                  v-model="editFormData.userName"
                  placeholder="如：张三"
                ></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item label="年龄">
                <el-input
                  v-model="editFormData.userAge"
                  placeholder="如：18"
                ></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item label="手机号">
                <el-input
                  v-model="editFormData.phone"
                  placeholder="如：152xxxx1414"
                ></el-input>
                <span class="important">*</span>
              </el-form-item>

              <el-form-item label="邮箱">
                <el-input
                  v-model="editFormData.email"
                  placeholder="如：152xxxx1414@163.com"
                ></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item v-show="isEdit" label="创建时间">
                <el-date-picker
                  v-model="editFormData.createTime"
                  type="datetime"
                  :disabled="true"
                  placeholder="如：2019-01-01 00:01:01"
                >
                </el-date-picker>
                <span class="important">*</span>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      input: '',
      isTable: true,
      isEdit: true,
      dataList: [],
      editFormData: {},
      queryListParam: { // 查询列表参数列表
        searchKey: 'userName',
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
    getDataList () {
      this.$get('/api/auth/user').then((res) => {
        this.dataList = res.data
      }).catch((err) => {
        this.$message.error(err.msg)
      })
    },
    initEditFormData () {
      this.editFormData = {
        plate: '',
        personName: '',
        account: '',
        userName: '',
        password: '',
        userAge: 20,
        phone: '',
        photo: '',
        email: '',
        enabled: true
      }
    },
    async handleIsSave () {
      this.isTable = false
      this.isEdit = false
    },
    async handleIsEdit (id) {
      await this.$get('/api/auth/user/' + id).then((res) => {
        if (res.status === 200) {
          this.editFormData = res.data
          this.isTable = false
          this.isEdit = true
        }
      }).catch((err) => {
        this.$message.error(err.msg)
      })
    },
    async handleIsTable () {
      if (this.isTable) {
        await this.$export('/api/auth/user/export').then((res) => {
          this.$message.success('下载成功~')
          // eslint-disable-next-line handle-callback-err
        }).catch((err) => {
          this.$message.error('下载失败~')
        })
        return
      }
      this.initEditFormData()
      this.isTable = !this.isTable
    },
    async handleSave () {
      if (this.isEdit) {
        this.handleEditSave()
        return
      }
      await this.$post('/api/auth/user', this.editFormData).then((res) => {
        if (res.status === 200) {
          this.getDataList()
          this.isTable = true
          this.initEditFormData()
        }
      }).catch((err) => {
        this.$message.error(err.msg)
      })
    },
    async handleEditSave () {
      await this.$put('/api/auth/user', this.editFormData).then((res) => {
        if (res.status === 200) {
          this.getDataList()
          this.isTable = true
          this.initEditFormData()
        }
      }).catch((err) => {
        this.$message.error(err.msg)
      })
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
      await this.$delete('/api/auth/user/' + id).then((res) => {
        if (res.status === 200) {
          this.getDataList()
        }
      }).catch((err) => {
        this.$message.error(err.msg)
      })
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
