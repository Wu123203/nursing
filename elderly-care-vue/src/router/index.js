import { createRouter, createWebHistory } from 'vue-router';
import Index from "@/layout/front/Index"
import Home from "@/layout/admin/Home"


// 用户路由
export const userRoutes = [
  {
    path: '/login',
    name: 'Login',
    hidden: true,
    component: () => import("@/views/front/Login"),
    meta: {
      title: '登录',
    }
  },
  {
    path: '/register',
    name: 'Register',
    hidden: true,
    component: () => import("@/views/front/Register"),
    meta: {
      title: '注册',
    }
  },
  {
    path: '/',
    component: Index,
    hidden: true,
    redirect: '/index',
    children: [
      {
        path: 'index',
        name: 'Index',
        hidden: true,
        component: () => import("@/views/front/Index"),
        meta: {
          title: '首页',
        }
      },
      {
        path: 'resthome',
        name: 'Resthome',
        hidden: true,
        component: () => import("@/views/front/Resthome"),
        meta: {
          title: '入院指南',
        }
      },
      {
        path: 'news',
        name: 'FrontNews',
        hidden: true,
        component: () => import("@/views/front/News"),
        meta: {
          title: '护工咨询',
        }
      },
      {
        path: 'news/info',
        name: 'NewsInfo',
        hidden: true,
        component: () => import("@/views/front/NewsInfo"),
        meta: {
          title: '护工详情',
        }
      },
      {
        path: 'feedback',
        name: 'FrontFeedback',
        hidden: true,
        component: () => import("@/views/front/Feedback"),
        meta: {
          title: '用户评论',
        }
      },
      {
        path: 'notice',
        name: 'FrontNotice',
        hidden: true,
        component: () => import("@/views/front/Notice"),
        meta: {
          title: '通知公告',
        }
      },
      {
        path: 'notice/info',
        name: 'NoticeInfo',
        hidden: true,
        component: () => import("@/views/front/NoticeInfo"),
        meta: {
          title: '公告详情',
        }
      },
      {
        path: 'user/like',
        name: 'UserLike',
        hidden: true,
        component: () => import("@/views/front/user/Like"),
        meta: {
          title: '护工点赞',
          roles: ['user']
        }
      },
      {
        path: 'user/comment',
        name: 'Comment',
        hidden: true,
        component: () => import("@/views/front/user/Comment"),
        meta: {
          title: '护工评论',
          roles: ['user']
        }
      },
      {
        path: 'user/live',
        name: 'UserLive',
        hidden: true,
        component: () => import("@/views/front/user/Live"),
        meta: {
          title: '老人管理',
          roles: ['user']
        }
      },
      {
        path: 'user/leave',
        name: 'UserLeave',
        hidden: true,
        component: () => import("@/views/front/user/Leave"),
        meta: {
          title: '外出报备',
          roles: ['user']
        }
      },
      {
        path: 'user/visit',
        name: 'UserVisit',
        hidden: true,
        component: () => import("@/views/front/user/Visit"),
        meta: {
          title: '访客登记',
          roles: ['user']
        }
      },
      {
        path: 'user/bill',
        name: 'UserBill',
        hidden: true,
        component: () => import("@/views/front/user/Bill"),
        meta: {
          title: '缴费订单',
          roles: ['user']
        }
      },
      {
        path: 'user/feedback',
        name: 'UserFeedback',
        hidden: true,
        component: () => import("@/views/front/user/Feedback"),
        meta: {
          title: '我的意见',
          roles: ['user']
        }
      },
      {
        path: 'user/profile',
        name: 'UserProfile',
        hidden: true,
        component: () => import("@/views/front/user/Profile"),
        meta: {
          title: '个人资料',
          roles: ['user']
        }
      },
      {
        path: 'user/password',
        name: 'UserPassword',
        hidden: true,
        component: () => import("@/views/front/user/Password"),
        meta: {
          title: '密码修改',
          roles: ['user']
        }
      },
    ]
  },
  {
    path: '/404',
    hidden: true,
    component: () => import('@/views/front/404')
  }
]


export const adminRoutes = [
  {
    path: '/admin',
    component: Home,
    meta: {
      roles: ['admin', 'employee']
    },
    redirect: '/admin/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import("@/views/back/Home"),
        meta: {
          title: '首页',
          icon: 'HomeFilled',
          roles: ['admin', 'employee']
        }
      },
      {
        path: 'profile',
        name: 'AdminProfile',
        hidden: true,
        component: () => import("@/views/back/AdminProfile"),
        meta: {
          title: '个人资料',
          icon: 'HomeFilled',
          roles: ['admin']
        }
      },
      {
        path: 'employee/profile',
        name: 'EmployeeProfile',
        hidden: true,
        component: () => import("@/views/back/EmployeeProfile"),
        meta: {
          title: '个人资料',
          icon: 'HomeFilled',
          roles: ['employee']
        }
      },
      {
        path: 'password',
        name: 'AdminPassword',
        hidden: true,
        component: () => import("@/views/back/Password"),
        meta: {
          title: '修改密码',
          icon: 'HomeFilled',
          roles: ['admin', 'employee']
        }
      }
    ]
  },
  {
    path: '/admin/user',
    component: Home,
    name: "AdminUser",
    meta: {
      title: '用户管理',
      icon: 'User',
      roles: ['admin']
    },
    children: [
      {
        path: 'list',
        name: 'UserList',
        component: () => import("@/views/back/User"),
        meta: {
          title: '用户管理',
          icon: 'User',
          roles: ['admin']
        }
      },
    ]
  },
  {
    path: '/admin/employee',
    component: Home,
    name: "AdminEmployee",
    meta: {
      title: '护工管理',
      icon: 'UserFilled',
      roles: ['admin']
    },
    children: [
      {
        path: 'list',
        name: 'EmployeeList',
        component: () => import("@/views/back/Employee"),
        meta: {
          title: '护工管理',
          icon: 'UserFilled',
          roles: ['admin']
        }
      },
    ]
  },
  {
    path: '/admin/bed',
    component: Home,
    name: "AdminBed",
    meta: {
      title: '床位管理',
      icon: 'House',
      roles: ['admin', 'employee']
    },
    children: [
      {
        path: 'room',
        name: 'RoomList',
        component: () => import("@/views/back/Room"),
        meta: {
          title: '房间管理',
          icon: 'ArrowRight',
          roles: ['admin', 'employee']
        }
      },
      {
        path: 'list',
        name: 'BedList',
        component: () => import("@/views/back/Bed"),
        meta: {
          title: '床位管理',
          icon: 'ArrowRight',
          roles: ['admin', 'employee']
        }
      },
    ]
  },
  {
    path: '/admin/live',
    component: Home,
    name: "AdminLive",
    meta: {
      title: '入住登记',
      icon: 'DocumentChecked',
      roles: ['admin', 'employee']
    },
    children: [
      {
        path: 'list',
        name: 'LiveList',
        component: () => import("@/views/back/Live"),
        meta: {
          title: '入住登记',
          icon: 'DocumentChecked',
          roles: ['admin', 'employee']
        }
      },
    ]
  },
  {
    path: '/admin/visit',
    component: Home,
    name: "AdminVisit",
    meta: {
      title: '访客记录',
      icon: 'Postcard',
      roles: ['admin', 'employee']
    },
    children: [
      {
        path: 'list',
        name: 'VisitList',
        component: () => import("@/views/back/Visit"),
        meta: {
          title: '访客记录',
          icon: 'Postcard',
          roles: ['admin', 'employee']
        }
      },
    ]
  },
  {
    path: '/admin/leave',
    component: Home,
    name: "AdminLeave",
    meta: {
      title: '外出报备',
      icon: 'ScaleToOriginal',
      roles: ['admin', 'employee']
    },
    children: [
      {
        path: 'list',
        name: 'LeaveList',
        component: () => import("@/views/back/Leave"),
        meta: {
          title: '外出报备',
          icon: 'ScaleToOriginal',
          roles: ['admin', 'employee']
        }
      },
    ]
  },
  {
    path: '/admin/bill',
    component: Home,
    name: "AdminBill",
    meta: {
      title: '缴费管理',
      icon: 'Tickets',
      roles: ['admin', 'employee']
    },
    children: [
      {
        path: 'list',
        name: 'BillList',
        component: () => import("@/views/back/Bill"),
        meta: {
          title: '缴费管理',
          icon: 'Tickets',
          roles: ['admin', 'employee']
        }
      },
    ]
  },
  {
    path: '/admin/news',
    component: Home,
    name: "AdminNews",
    meta: {
      title: '护工咨询管理',
      icon: 'Reading',
      roles: ['admin']
    },
    children: [
      {
        path: 'list',
        name: 'NewsList',
        component: () => import("@/views/back/News"),
        meta: {
          title: '护工咨询管理',
          icon: 'ArrowRight',
          roles: ['admin']
        }
      },
      {
        path: 'comment',
        name: 'CommentList',
        component: () => import("@/views/back/Comment"),
        meta: {
          title: '护工评论',
          icon: 'ArrowRight',
          roles: ['admin']
        }
      },
    ]
  },
  {
    path: '/admin/feedback',
    component: Home,
    name: "AdminFeedback",
    meta: {
      title: '用户意见',
      icon: 'ChatDotSquare',
      roles: ['admin']
    },
    children: [
      {
        path: 'list',
        name: 'FeedbackList',
        component: () => import("@/views/back/Feedback"),
        meta: {
          title: '用户意见',
          icon: 'ChatDotSquare',
          roles: ['admin']
        }
      },
    ]
  },
  {
    path: '/admin/notice',
    component: Home,
    name: "AdminNotice",
    meta: {
      title: '公告信息',
      icon: 'Bell',
      roles: ['admin', 'employee']
    },
    children: [
      {
        path: 'list',
        name: 'NoticeList',
        component: () => import("@/views/back/Notice"),
        meta: {
          title: '公告信息',
          icon: 'Bell',
          roles: ['admin', 'employee']
        }
      },
    ]
  },
  {
    path: '/admin/resthome',
    component: Home,
    name: "AdminResthome",
    meta: {
      title: '入院指南',
      icon: 'Warning',
      roles: ['admin']
    },
    children: [
      {
        path: 'info',
        name: 'ResthomeList',
        component: () => import("@/views/back/Resthome"),
        meta: {
          title: '入院指南',
          icon: 'Warning',
          roles: ['admin']
        }
      }
    ]
  },
]



export const Route404 = {
  path: '/:catchAll(.*)*',
  hidden: true,
  component: Home,
  redirect: '/404',
  meta: {
    title: '404',
  },
}

const createRouters = () => createRouter({
  history: createWebHistory(),
  routes: userRoutes.concat(adminRoutes),
});

const router = createRouters()

export function resetRouter() {
  const newRouter = createRouters()
  router.matcher = newRouter.matcher // reset router
}

export default router