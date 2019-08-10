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
              suffix-icon="el-icon-search"
              class="input-with-select"
            >
              <el-select slot="prepend" placeholder="请选择">
                <el-option label="车牌号" value="plate"></el-option>
                <el-option label="姓名" value="personName"></el-option>
                <el-option
                  label="主入口名称"
                  value="primaryInDeviceName"
                ></el-option>
                <el-option
                  label="入口值班员"
                  value="primaryInDutyName"
                ></el-option>
              </el-select>
            </el-input>
          </div>
        </div>
        <div class="btnswiper">
          <el-button size="medium" type="primary">
            导出数据
          </el-button>
          <el-button
            size="medium"
            type="danger"
            v-show="false"
            @click="handleSave"
            >批量删除</el-button
          >
        </div>
      </div>
      <div>
        <div class="table-content" v-show="true">
          <el-table :data="tableData" border style="width: 100%">
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column fixed prop="date" label="日期" width="150">
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="120">
            </el-table-column>
            <el-table-column prop="province" label="省份" width="120">
            </el-table-column>
            <el-table-column prop="city" label="市区" width="120">
            </el-table-column>
            <el-table-column prop="address" label="地址" width="300">
            </el-table-column>
            <el-table-column prop="zip" label="邮编" width="120">
            </el-table-column>
            <el-table-column fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button @click="handleClick(scope.row)" size="mini"
                  >编辑</el-button
                >
                <el-button type="danger" size="mini">删除</el-button>
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
        <div class="add-content" v-show="false">
          <div class="formbox">
            <el-form label-position="right" label-width="140px">
              <el-form-item label="车牌号">
                <el-input placeholder="如：京A12B45"></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item label="姓名">
                <el-input :disabled="true" placeholder="如：张三"></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item label="主入口名称">
                <el-input
                  :disabled="true"
                  placeholder="如：东门入口"
                ></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item label="计费规则">
                <el-input
                  :disabled="true"
                  placeholder="如：东门入口"
                ></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item label="入场时间">
                <el-date-picker
                  type="datetime"
                  :disabled="true"
                  value-format="timestamp"
                  placeholder="如：2019-01-01 00:01:01"
                >
                </el-date-picker>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item label="操作员">
                <el-input :disabled="true" placeholder="如：张三"></el-input>
                <span class="important">*</span>
              </el-form-item>
              <el-form-item class="last-content" label="图片">
                <div class="picBox">
                  <img class="inImage" src="" alt="" />
                </div>
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
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-04',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1517 弄',
        zip: 200333
      }, {
        date: '2016-05-01',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1519 弄',
        zip: 200333
      }, {
        date: '2016-05-03',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1516 弄',
        zip: 200333
      }]
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
