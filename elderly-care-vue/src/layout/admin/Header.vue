<template>
  <div class="header">
    <div class="logo" :class="!collapse ? '':'collapse'">
      <img src="@/assets/images/logo.png" />
      <h2 v-if="!collapse">智能护工平台</h2>
    </div>
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="collapseChage">
      <el-icon v-if="!collapse">
        <fold />
      </el-icon>
      <el-icon v-else>
        <expand />
      </el-icon>
    </div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
          <span class="el-dropdown-link">
            {{user.nickname}}
            <el-icon>
              <arrow-down-bold />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item icon="homeFilled" command="index">前台首页</el-dropdown-item>
              <el-dropdown-item icon="Edit" command="profile">个人信息</el-dropdown-item>
              <el-dropdown-item icon="unlock" command="password">修改密码</el-dropdown-item>
              <el-dropdown-item icon="circle-close" command="logout">退出系统</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
    };
  },
  computed: {
    collapse() {
      return this.$store.state.collapse
    },
    user() {
      return this.$store.state.user
    }
  },
  methods: {
    // 侧边栏折叠
    collapseChage() {
      this.$store.commit('SET_COLLAPSE')
    },

    //处理下拉菜单
    handleCommand(command) {
      if (command == 'index') {
        this.$router.push('/')
      } else if (command == 'invest') {
        this.$router.push('/user/invest')
      } else if (command == 'profile') {
        if (this.user.role == 0) {
          this.$router.push('/admin/profile')
        } else {
          this.$router.push('/admin/employee/profile')
        }
      } else if (command == 'password') {
        this.$router.push('/admin/password');
      } else if (command == 'logout') {
        this.$store.commit('LOGOUT')
        this.$router.push('/login');
      }
    }
  }
};
</script>
<style scoped>
.header {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  border-bottom: solid 1px #e6e6e6;
  box-shadow: 0 5px 10px #ddd;
}
.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}
.collapse-btn .el-icon {
  vertical-align: middle;
}
.header .logo {
  float: left;
  width: 250px;
  height: 70px;
  line-height: 70px;
  text-align: center;
  border-right: solid 1px #e6e6e6;
}

.collapse {
  width: 64px !important;
}

.collapse img {
  margin-right: 0 !important;
}

.header .logo h2 {
  display: inline-block;
  font-size: 18px;
}

.header .logo img {
  width: 30px;
  height: 30px;
  vertical-align: middle;
  margin-right: 4px;
  margin-top: -4px;
}

.header-right {
  float: right;
  padding-right: 20px;
}
.header-user-con {
  display: flex;
  height: 70px;
  align-items: center;
}
.user-name {
  margin-left: 30px;
}

.el-dropdown-link {
  cursor: pointer;
}
.el-dropdown-menu__item {
  text-align: center;
}
</style>
