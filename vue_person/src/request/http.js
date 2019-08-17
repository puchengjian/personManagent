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
  // const token = sessionStorage.getItem('token')
  // config.headers.Authorization = token
  return config
}, () => {
  Message.error({ message: '请求超时！' })
})

axios.interceptors.response.use(data => {
  if (data.data != null && data.data !== '') {
    if (data.status === 200 && data.data.status === 401) {
      Message.warning('您未登录,将在三秒后跳转至登录页')
      sessionStorage.removeItem('token')
      setTimeout(() => {
        router.push('/login')
        get('/logout')
      }, 3000)
      return data
    }
    if (data.headers['content-type'].startsWith('application/json')) {
      if (data.status === 200 && data.data.status !== 200) {
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
    this.$message.warning('您未登录,将在三秒后跳转至登录页')
    sessionStorage.removeItem('token')
    setTimeout(() => {
      router.push('/login')
      get('/logout')
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
