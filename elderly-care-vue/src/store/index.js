import Vue from 'vue'
import { createStore } from 'vuex';
import { userRoutes, adminRoutes } from '@/router'
import {filterAsyncRoutes} from '@/utils/permission'

// 创建 store
const store = createStore({
  state: {
    collapse: localStorage.getItem('collapse') ? !!+localStorage.getItem('collapse') : false,
    user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {},
    routes: [],
  },

  mutations: {
    SET_COLLAPSE: (state) => {
      state.collapse = !state.collapse
      localStorage.setItem("collapse",state.collapse === true ? 1 : 0)
    },
    SET_ROUTES: (state, routes) => {
      state.routes = routes
    },
    LOGIN: (state,user) => {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user))
    },
    SET_USER: (state,user) => {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user))
    },
    LOGOUT: (state) => {
      state.user = {};
      state.routes = [];
      localStorage.removeItem('user')
    }
  },


  actions: {
    generateRoutes({ commit }, role) {
      return new Promise(resolve => {
        let accessedRoutes = filterAsyncRoutes(userRoutes.concat(adminRoutes), [role])
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      })
    }
  },
});

export default store

