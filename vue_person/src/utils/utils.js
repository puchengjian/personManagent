/* eslint-disable standard/object-curly-even-spacing */
import {get } from '../request/api'
import { Message } from 'element-ui'

export const isNotNullORBlank = (...args) => {
  for (var i = 0; i < args.length; i++) {
    var argument = args[i]
    if (argument == null || argument === '' || argument === undefined) {
      Message.warning({ message: '数据不能为空!' })
      return false
    }
  }

  return true
}

export const initMenu = (router, store) => {
  if (store.state.routes.length > 0) {
    return
  }
  get('/api/auth/nav/menu').then(resp => {
    if (resp.status === 200) {
      let fmtRoutes = formatRoutes(resp.data)
      router.addRoutes(fmtRoutes)
      store.commit('initMenu', fmtRoutes)
    }
  })
}

export const formatRoutes = (routes) => {
  let fmRoutes = []
  routes.forEach(router => {
    let {
      path,
      component,
      folder,
      text,
      meta,
      icon,
      children
    } = router
    if (children && children instanceof Array) {
      children = formatRoutes(children)
    }
    let fmRouter = {
      path: path,
      component (resolve) {
        if (folder.startsWith('Home')) {
          require(['@/components/' + component + '.vue'], resolve)
        } else if (folder.startsWith('sys')) {
          require(['@/components/system/' + component + '.vue'], resolve)
        }
      },
      name: text,
      icon: icon,
      meta: meta,
      children: children
    }
    fmRoutes.push(fmRouter)
  })

  return fmRoutes
}
