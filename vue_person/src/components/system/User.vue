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
                <el-option label="用户名" value="account"></el-option>
                <el-option label="姓名" value="user_name"></el-option>
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
      <div v-loading="loading">
        <div class="table-content" v-show="isTable">
          <el-table
            :data="dataList"
            border
            :max-height="480"
            style="width: 100%"
          >
            <el-table-column type="index" fixed width="80"> </el-table-column>
            <el-table-column label="头像" width="80">
              <template slot-scope="scope">
                <img :src="scope.row.photo" alt="" class="photo-img" />
              </template>
            </el-table-column>
            <el-table-column prop="account" label="用户名" width="140">
            </el-table-column>
            <el-table-column prop="userName" label="姓名" width="140">
            </el-table-column>
            <el-table-column prop="roleName" label="角色" width="140">
            </el-table-column>
            <el-table-column label="状态" width="80">
              <template slot-scope="scope">
                <span>{{ scope.row.enabled ? "启用" : "禁用" }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="userAge" label="年龄" width="80">
            </el-table-column>
            <el-table-column prop="phone" label="手机号" width="140">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="140">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="200">
            </el-table-column>
            <el-table-column prop="updateTime" label="最后修改时间" width="200">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="250">
              <template slot-scope="scope">
                <el-button @click="handleIsEdit(scope.row.id)" size="mini"
                  >编辑</el-button
                >
                <el-button
                  @click="handleDel(scope.row.id)"
                  :disabled="scope.row.admin"
                  type="danger"
                  size="mini"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :page-sizes="[10, 20, 40, 60, 80, 100]"
            prev-text="上一页"
            next-text="下一页"
            :page-size="queryListParam.size"
            :current-page="queryListParam.page"
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
            <el-form-item label="角色">
              <el-select
                :disabled="editFormData.admin"
                v-model="editFormData.roleId"
                placeholder="请选择"
              >
                <el-option
                  v-for="role in roleList"
                  :disabled="role.admin"
                  :key="role.id"
                  :label="role.roleName"
                  :value="role.id"
                >
                </el-option>
              </el-select>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="editFormData.enabled" placeholder="请选择">
                <el-option :value="true" label="启用"></el-option>
                <el-option :value="false" label="禁用"></el-option>
              </el-select>
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
            <el-form-item v-show="isEdit" class="last-content" label="头像">
              <div>
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  action
                  :file-list="fileList"
                  :http-request="handleUpload"
                  multiple
                  :on-change="handleImgChange"
                  list-type="picture"
                  accept="image/jpeg,image/gif,image/png"
                >
                  <el-button size="small" type="primary">上传</el-button>
                </el-upload>
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
      loading: false, // 加载框
      isEdit: true, // 新增和编辑操作表示
      dataList: [], // 表格集合
      editFormData: {}, // 表单默认数据结构
      roleList: [], // 角色集合
      total: 0,
      queryListParam: { // 查询列表参数列表
        searchKey: 'user_name',
        searchValue: '',
        page: 1,
        size: 10
      },
      fileList: []
    }
  },
  created () {
    this.getDataList()
    this.initEditFormData()
    this.handleRoleList()
  },
  methods: {
    handleLoading (val) { // loading
      this.loading = val
    },
    handleSearch () { // 搜索回调
      this.page = 1
      this.size = 10
      this.getDataList()
    },
    initEditFormData () { // 初始化表单默认数据结构
      this.editFormData = {
        account: '',
        userName: '',
        roleId: '',
        password: '',
        userAge: 20,
        phone: '',
        photo: '',
        email: '',
        enabled: true
      }
      this.fileList = []
    },
    handleFileList (fileUrl) { // 头像图片更换
      this.fileList = [{ name: '头像图片', url: fileUrl }]
    },
    async getDataList () { // 获取表格集合
      this.handleLoading(true)
      const res = await this.$get('/api/system/user', this.queryListParam)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.dataList = res.data
      this.total = res.total
    },
    handleSizeChange (val) { // 改变每页大小时触发
      this.queryListParam.size = val
      this.getDataList()
    },
    handleCurrentChange (val) { // 改变第几页时 触发
      this.queryListParam.page = val
      this.getDataList()
    },
    async handleIsSave () { // 区分新增和修改操作
      this.isTable = false
      this.isEdit = false
    },
    async handleIsEdit (id) { // 获取修改的用户数据
      this.handleLoading(true)
      const res = await this.$get('/api/system/user/' + id)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.editFormData = res.data
      this.handleFileList(this.editFormData.photo)
      this.isTable = false
      this.isEdit = true
    },
    handleImgChange (file) { // 图片change事件
      this.$refs.upload.clearFiles()
      this.handleFileList(file.url)
    },
    async handleUpload (content) { // 修改用户头像
      const form = new FormData()
      form.append('file', content.file)
      form.append('userId', this.editFormData.id)
      this.handleLoading(true)
      const res = await this.$put('/api/system/photo/user', form)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
    },
    async handleIsTable () { // 导出excel
      if (this.isTable) {
        await this.$export('/api/system/user/export', this.queryListParam).then((res) => {
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
        this.handleEditSave() // 修改用户
        return
      }
      // 新增用户
      this.handleLoading(true)
      const res = await this.$post('/api/system/user', this.editFormData)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
      this.isTable = true
      this.initEditFormData()
    },
    async handleEditSave () { // 修改函数
      this.handleLoading(true)
      const res = await this.$put('/api/system/user', this.editFormData)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
      this.isTable = true
      this.initEditFormData()
    },
    handleDel (id) { // 删除用户提示
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.enterDel(id)
      }).catch(() => { })
    },
    async enterDel (id) { // 删除用户
      this.handleLoading(true)
      const res = await this.$delete('/api/system/user/' + id)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
    },
    async handleRoleList () { // 获取角色下拉框集合
      const res = await this.$get('/api/system/role')
      this.roleList = res.data
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
