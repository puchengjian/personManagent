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
                <el-option label="角色姓名" value="role_name"></el-option>
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
            {{ isTable ? "添加角色" : "返回列表" }}
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
            <!-- <el-table-column type="selection" width="55"> </el-table-column> -->
            <el-table-column prop="roleName" label="角色名称" width="140">
            </el-table-column>
            <el-table-column prop="description" label="角色描述" width="140">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="200">
            </el-table-column>
            <el-table-column prop="updateTime" label="最后修改时间" width="200">
            </el-table-column>
            <el-table-column label="操作" fixed="right" min-width="250">
              <template slot-scope="scope">
                <el-button
                  :disabled="scope.row.admin"
                  @click="handleIsEdit(scope.row.id)"
                  size="mini"
                  >编辑</el-button
                >
                <el-button
                  :disabled="scope.row.admin"
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
            <el-form-item label="角色名称">
              <el-input v-model="editFormData.roleName"></el-input>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item label="角色名称">
              <el-input v-model="editFormData.description"></el-input>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item class="last-el-form-item" label="权限选择">
              <el-tree
                style="padding-top: 7px;"
                :data="authTreeList"
                :props="defaultProps"
                show-checkbox
                node-key="id"
                :default-checked-keys="defaultCheckArr"
                :check-strictly="true"
                @check-change="handleCheckChange"
              ></el-tree>
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
        searchKey: 'role_name',
        searchValue: '',
        page: 1,
        size: 10
      },
      defaultCheckArr: [], // 默认选中
      defaultProps: {
        children: 'children',
        label: 'text'
      },
      authTreeList: [] // 权限树形结构数据
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
    handleCheckChange (data, checked, indeterminate) { // 选择权限
      if (checked) { // 选中
        this.defaultCheckArr.push(data.id)
      } else if (!checked) { // 取消选中
        let index = this.defaultCheckArr.findIndex(item => item === data.id)
        if (index >= 0) {
          this.defaultCheckArr.splice(index, 1)
        }
      }
      this.editFormData.menuIdList = Array.from(new Set(this.defaultCheckArr))
    },
    async getDataList () {
      this.handleLoading(true)
      const res = await this.$get('/api/system/role', this.queryListParam)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.dataList = res.data
      this.total = res.total
    },
    initEditFormData () {
      this.editFormData = {
        roleName: '',
        description: '',
        menuIdList: []
      }
      this.defaultCheckArr = []
      this.handleTreeList()
    },
    async handleIsEdit (id) {
      this.handleLoading(true)
      const res = await this.$get('/api/system/role/' + id)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.editFormData = res.data
      this.defaultCheckArr = res.data.menuIdList
      this.isTable = false
      this.isEdit = true
    },
    async handleIsTable () {
      this.initEditFormData()
      this.isTable = !this.isTable
      this.isEdit = false
    },
    async handleSave () {
      if (this.isEdit) {
        this.handleEditSave()
        return
      }
      this.handleLoading(true)
      const res = await this.$post('/api/system/role', this.editFormData)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
      this.isTable = true
    },
    async handleEditSave () {
      this.handleLoading(true)
      const res = await this.$put('/api/system/role', this.editFormData)
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
      const res = await this.$delete('/api/system/role/' + id)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
    },
    async handleTreeList () {
      const res = await this.$get('/api/system/menu')
      this.authTreeList = res.data
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
