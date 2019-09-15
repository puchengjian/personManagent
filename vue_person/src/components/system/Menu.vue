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
                <el-option label="菜单名称" value="menu_name"></el-option>
              </el-select>
            </el-input>
          </div>
        </div>
        <div class="btnswiper">
          <el-button
            size="medium"
            :type="isTable ? 'primary' : 'info'"
            @click="handleIsTable(0)"
          >
            {{ isTable ? "添加菜单" : "返回列表" }}
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
            default-expand-all
            row-key="id"
            border
            style="width: 100%"
            :tree-props="treeProps"
            :max-height="500"
          >
            <el-table-column prop="text" label="菜单名称" width="180">
            </el-table-column>
            <el-table-column prop="path" label="路径" width="160">
            </el-table-column>
            <el-table-column prop="component" label="组件" width="140">
            </el-table-column>
            <el-table-column prop="folder" label="文件夹" width="140">
            </el-table-column>
            <el-table-column prop="icon" label="图标" width="200">
            </el-table-column>
            <el-table-column prop="type" label="类型" width="120">
            </el-table-column>
            <el-table-column prop="perms" label="权限" width="140">
            </el-table-column>
            <el-table-column label="状态" width="140">
              <template slot-scope="scope">
                <span>{{ scope.row.enabled }}</span>
              </template>
            </el-table-column>
            <el-table-column label="需要登录" width="140">
              <template slot-scope="scope">
                <span>{{ scope.row.meta.requireAuth }}</span>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" min-width="250">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  v-show="scope.row.type === '菜单'"
                  @click="handleIsTable(scope.row.id)"
                  size="mini"
                  >添加</el-button
                >
                <el-button
                  @click="handleIsEdit(scope.row.id)"
                  size="mini"
                  v-show="scope.row.type === '菜单'"
                  >编辑</el-button
                >
                <el-button
                  @click="handleDel(scope.row.id)"
                  v-show="scope.row.type === '菜单'"
                  type="danger"
                  size="mini"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>

        <div class="add-content" v-show="!isTable">
          <el-form
            label-position="right"
            label-width="140px"
            :model="editFormData"
          >
            <el-form-item label="菜单名称">
              <el-input v-model="editFormData.menuName"></el-input>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item
              label="权限"
              v-show="editFormData.type === 1 ? false : true"
            >
              <el-input v-model="editFormData.perms"></el-input>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item
              v-show="editFormData.type === 2 ? false : true"
              label="组件"
            >
              <el-input
                :disabled="isEdit"
                v-model="editFormData.component"
              ></el-input>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item
              v-show="editFormData.type === 2 ? false : true"
              label="文件夹"
            >
              <el-input
                :disabled="isEdit"
                v-model="editFormData.folder"
              ></el-input>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item
              label="图标"
              v-show="editFormData.type === 2 ? false : true"
            >
              <el-input v-model="editFormData.icon"></el-input>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item
              label="路径"
              v-show="editFormData.type === 2 ? false : true"
            >
              <el-input v-model="editFormData.path"></el-input>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item label="是否需要登录">
              <el-select
                v-model="editFormData.requireAuth"
                placeholder="请选择"
              >
                <el-option :value="true" label="true"></el-option>
                <el-option :value="false" label="false"></el-option>
              </el-select>
              <span class="important">*</span>
            </el-form-item>
            <el-form-item label="类型">
              <el-radio-group :disabled="isEdit" v-model="editFormData.type">
                <el-radio :label="1">菜单</el-radio>
                <el-radio :label="2">按钮</el-radio>
              </el-radio-group>
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
      queryListParam: { // 查询列表参数列表
        searchKey: 'menu_name',
        searchValue: ''
      },
      treeProps: {
        children: 'children'
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
    handleSearch () { // 搜索回调
      this.page = 1
      this.size = 10
      this.getDataList()
    },
    initEditFormData () { // 初始化表单默认数据结构
      this.editFormData = {
        parentId: '',
        menuName: '',
        component: '',
        folder: '',
        icon: '',
        path: '',
        requireAuth: true,
        type: 1
      }
    },
    async getDataList () { // 获取表格集合
      this.handleLoading(true)
      const res = await this.$get('/api/system/menu', this.queryListParam)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.dataList = res.data
      // this.total = res.total
    },
    async handleIsEdit (id) { // 获取修改的菜单数据
      this.handleLoading(true)
      const res = await this.$get('/api/system/menu/' + id)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.editFormData = res.data
      this.isTable = false
      this.isEdit = true
    },
    async handleIsTable (id) {
      this.initEditFormData()
      this.isTable = !this.isTable
      this.isEdit = false
      this.editFormData.parentId = id
    },
    async handleSave () {
      if (this.isEdit) {
        this.handleEditSave() // 修改菜单
        return
      }
      // 新增菜单
      this.handleLoading(true)
      const res = await this.$post('/api/system/menu', this.editFormData)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
      this.isTable = true
      this.initEditFormData()
    },
    async handleEditSave () { // 修改函数
      this.handleLoading(true)
      const res = await this.$put('/api/system/menu', this.editFormData)
      this.handleLoading(false)
      if (res.status !== 200) return
      this.getDataList()
      this.isTable = true
      this.initEditFormData()
    },
    handleDel (id) { // 删除菜单提示
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.enterDel(id)
      }).catch(() => { })
    },
    async enterDel (id) { // 删除菜单
      this.handleLoading(true)
      const res = await this.$delete('/api/system/menu/' + id)
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
