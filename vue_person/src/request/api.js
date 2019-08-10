import axios from 'axios'

export const get = (url, params) => {
  return new Promise((resolve, reject) => {
    axios.get(url, { params: params }, { withCredentials: true }).then(res => {
      resolve(res.data)
    }).catch(err => {
      reject(err.data)
    })
  })
}

export const post = (url, params) => {
  return new Promise((resolve, reject) => {
    axios.post(url, params, { withCredentials: true }).then(res => {
      resolve(res.data)
    }).catch(err => {
      reject(err.data)
    })
  })
}

export const put = (url, params) => {
  return new Promise((resolve, reject) => {
    axios.put(url, params).then(res => {
      resolve(res.data)
    }).catch(err => {
      reject(err.data)
    })
  })
}

export const del = (url, params) => {
  return new Promise((resolve, reject) => {
    axios.delete(url, { params: params }).then(res => {
      resolve(res.data)
    }).catch(err => {
      reject(err.data)
    })
  })
}
