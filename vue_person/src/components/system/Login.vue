<template>
  <el-form
    class="login-continer"
    :rules="rules"
    :model="loginForm"
    v-loading="loading"
    label-position="left"
    label-width="0px"
  >
    <h3 class="login_title">系统登录</h3>
    <el-form-item prop="account">
      <el-input
        v-model="loginForm.account"
        type="text"
        placeholder="请输入用户名"
      ></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
        v-model="loginForm.password"
        auto-complete="off"
        type="password"
        placeholder="请输入密码"
      ></el-input>
    </el-form-item>
    <el-checkbox class="login_remember" v-model="checked" label-position="left"
      >记住密码</el-checkbox
    >
    <el-form-item style="width: 100%">
      <el-button @click="login" type="primary" style="width: 100%"
        >登录</el-button
      >
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data () {
    return {
      rules: {
        account: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      loginForm: {
        account: 'pzy',
        password: '123456'
      },
      checked: true,
      loading: false
    }
  },
  methods: {
    login: function () {
      this.loading = true
      var _this = this
      _this.$post('/api/login', this.loginForm)
        .then(resp => {
          if (resp.status === 200) {
            sessionStorage.setItem('token', resp.token)
            this.setCookie()
            _this.$store.commit('login', resp.data)
            _this.$router.replace({ path: '/index' })
          }
        })
      _this.loading = false
    },
    setCookie () { // 设置 token和cookie
      let date = new Date()
      // console.log(date.getTime() + 60 * 60 * 1000)
      date.setTime(date.getTime() + 60 * 60 * 1000) // 设置date为当前时间+60分
      document.cookie = 'key=seesionTime; expires=' + date.toGMTString()
    }
  }
}
</script>

<style>
.login-continer {
  width: 350px;
  margin: 180px auto;
  border-radius: 15px;
  border: 1px solid #eaeaea;
  padding: 35px 35px 15px 35px;
  box-shadow: 0 0 25px #cac6c6;
  background-clip: padding-box;
}
.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
.login_remember {
  margin: 0px 0px 35px 0px;
  text-align: left;
}
</style>
