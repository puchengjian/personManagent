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

export const EXPORT = (url, params) => {
  return new Promise((resolve, reject) => {
    axios.get(url, { params: params, responseType: 'arraybuffer' }).then((res) => {
      let data = res.data
      if (!data) {
        return
      }
      let fileName = res.headers['content-disposition']
      // 获取文件名
      if (fileName && fileName.length >= 2) {
        fileName = fileName.split('=')[1]
      }
      fileName = decodeURIComponent(fileName)
      let url = window.URL.createObjectURL(new Blob([data]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.download = fileName
      document.body.appendChild(link)
      link.click()
      window.URL.revokeObjectURL(link.href)
      resolve(res.data)
    }).catch(err => {
      reject(err.data)
    })
  })
}
