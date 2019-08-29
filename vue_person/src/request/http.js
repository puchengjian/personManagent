/* eslint-disable standard/object-curly-even-spacing */
import axios from 'axios'
import { Message } from 'element-ui'
import router from '../router'
import {get } from './api'

const baseURL = ''
axios.defaults.timeout = 10000 // 响应时间10s  提示用户超时请刷新
axios.defaults.baseURL = baseURL
axios.defaults.withCredentials = true

axios.interceptors.request.use(config => {
  const token = sessionStorage.getItem('token')
  config.headers.token = token
  return config
}, () => {
  Message.error({ message: '请求超时！' })
})

axios.interceptors.response.use(data => {
  if (data.data != null && data.data !== '') {
    // 身份认证，要求登录
    if (data.status === 200 && data.data.status === 401) {
      Message.warning(data.data.msg)
      sessionStorage.removeItem('token')
      setTimeout(() => {
        router.push('/login')
        get('/api/logout')
      }, 3000)
      return data
    }
    // 服务器理解请求客户端的请求，但是拒绝执行此请求
    if (data.status === 200 && data.data.status === 403) {
      Message.warning(data.data.msg)
      return data
    }
    if (data.headers['content-type'].startsWith('application/json')) {
      if (data.status === 200 && data.data.status === 500) {
        Message.error({ message: data.data.msg })
        return data
      }
      // 客户端请求语法错误，服务端无法理解
      if (data.status === 200 && data.data.status === 400) {
        Message.error({ message: data.data.msg })
        return data
      }
      if (data.data.msg) {
        Message.success({ message: data.data.msg })
      }
    }
  }
  return data
}, err => {
  if (err.response.status === 401) {
    this.$message.warning(err.response.msg)
    sessionStorage.removeItem('token')
    setTimeout(() => {
      router.push('/login')
      get('/api/logout')
    }, 3000)
    return false
  } else if (err.response.status !== 200) {
    Message.error({ message: err.response.msg })
  } else {
    if (err.response.data.msg) {
      Message.error({ message: err.response.msg })
    } else {
      Message.error({ message: '未知错误!' })
    }
  }
})
