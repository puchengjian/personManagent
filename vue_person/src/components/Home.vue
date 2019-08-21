<template>
  <div>
    <el-container class="home-container">
      <el-header class="home-header">
        <span class="home_title">管理系统</span>
        <div style="display: flex;align-items: center;margin-right: 7px">
          <el-badge
            style="margin-right: 30px"
            :is-dot="this.$store.state.nfDot"
          >
            <i class="fa fa-bell-o" style="cursor: pointer"></i>
          </el-badge>
          <el-dropdown @command="handleCommand">
            <span
              class="el-dropdown-link home_userinfo"
              style="display: flex;align-items: center"
            >
              {{ user.userName }}
              <i>
                <img
                  v-if="user.photo != ''"
                  :src="user.photo"
                  style="width: 45px;height: 45px;margin-right: 5px;margin-left: 5px;border-radius: 45px"
                />
              </i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>设置</el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>

      <el-container>
        <el-aside width="180px" class="home-aside">
          <div
            style="display: flex;justify-content: flex-start;width: 180px;text-align: left;"
          >
            <el-menu style="width: 180px;" unique-opened router>
              <template
                v-for="(item, index) in this.routes"
                :v-if="!item.hidden"
              >
                <el-submenu :key="index" :index="index + ''">
                  <template slot="title">
                    <i
                      :class="item.icon"
                      style="color: #20a0ff;width: 14px;"
                    ></i>
                    <span slot="title">{{ item.name }}</span>
                  </template>
                  <el-menu-item
                    width="180px"
                    style="padding-left: 30px;padding-right:0px;margin-left: 0px;width: 170px;text-align: left"
                    v-for="child in item.children"
                    :index="child.path"
                    :key="child.path"
                    >{{ child.name }}
                  </el-menu-item>
                </el-submenu>
              </template>
            </el-menu>
          </div>
        </el-aside>
        <el-main style="background-color: #f0f8f8">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/index' }"
              >首页</el-breadcrumb-item
            >
            <el-breadcrumb-item
              v-text="this.$router.currentRoute.name"
            ></el-breadcrumb-item>
          </el-breadcrumb>
          <keep-alive>
            <router-view v-if="this.$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!this.$route.meta.keepAlive"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
export default {
  methods: {
    isLogin () {
      if (sessionStorage.getItem('token') === '' || sessionStorage.getItem('token') === null || sessionStorage.getItem('token') === 'undefined' || sessionStorage.getItem('token') === undefined) {
        this.$message.warning('您未登录,将在三秒后跳转至登录页')
        setTimeout(() => {
          this.$router.push('/login')
          this.$get('/api/logout')
        }, 3000)
        return false
      }
    },
    handleCommand (cmd) {
      var _this = this
      if (cmd === 'logout') {
        this.$confirm('注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.$get('/api/logout')
          _this.$store.commit('logout')
          _this.$router.replace({ path: '/login' })
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '取消'
          })
        })
      }
    }
  },
  data () {
    return {
      isDot: false
    }
  },
  computed: {
    user () {
      return this.$store.state.user
    },
    routes () {
      return this.$store.state.routes
    }
  }
}
</script>
<style>
.home-container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}
.home-header {
  background-color: #20a0ff;
  color: #333;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: content-box;
  padding: 0px;
}

.home-main {
  background-color: #fff;
  color: #000;
  text-align: center;
  margin: 0px;
  padding: 0px;
}

.el-aside {
  bottom: 0;
  background: #fff;
}

.home_title {
  color: #fff;
  font-size: 22px;
  display: inline;
  margin-left: 8px;
}

.home_userinfo {
  color: #fff;
  cursor: pointer;
}

.home_userinfoContainer {
  display: inline;
  margin-right: 20px;
}

.el-submenu .el-menu-item {
  width: 180px;
  min-width: 175px;
}
</style>
