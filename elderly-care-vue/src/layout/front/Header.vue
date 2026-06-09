<template>
  <div class="header">
    <el-row type="flex" align="left" justify="space-between">
      <el-col :span="4">
        <div class="logo">
          <h3>
            <router-link to="/index">
              <img src="@/assets/images/logo.png" />
              智能护工平台
            </router-link>
          </h3>
        </div>
      </el-col>
      <el-col :span="12">
        <ul class="nav">
          <li>
            <router-link to="/index">首页</router-link>
          </li>
          <li>
            <router-link to="/resthome">入院指南</router-link>
          </li>
          <li>
            <router-link to="/news">护工咨询</router-link>
          </li>
          <li>
            <router-link to="/feedback">用户意见</router-link>
          </li>
          <li>
            <router-link to="/notice">通知公告</router-link>
          </li>
        </ul>
      </el-col>
      <el-col :span="8">
        <ul class="nav-right">
          <li v-if="user.id">
            <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                <img :src="user.avatar ? user.avatar : require('@/assets/images/avatar.png')" />
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item icon="Setting" command="admin" v-if="user.role != 2">后台中心</el-dropdown-item>
                  <el-dropdown-item icon="Edit" command="profile" v-if="user.role == 2">个人资料</el-dropdown-item>
                  <el-dropdown-item icon="lock" command="password" v-if="user.role == 2">修改密码</el-dropdown-item>
                  <el-dropdown-item icon="SwitchButton" command="logout">退出系统</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </li>
          <template v-else>
            <li>
              <router-link to="/login">
                <el-button plain>登录</el-button>
              </router-link>
            </li>
            <li>
              <router-link to="/register">
                <el-button type="info" plain>注册</el-button>
              </router-link>
            </li>
          </template>
        </ul>
      </el-col>
    </el-row>
  </div>

</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      type: '',
      kd: '',
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  methods: {

    handleCommand(command) {
      if (command == 'admin') {
        this.$router.push('/admin');
      } else if (command == 'profile') {
        this.$router.push('/user/profile');
      } else if (command == 'password') {
        this.$router.push('/user/password');
      } else {
        this.$store.commit('LOGOUT');
        this.$router.go(0);
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  font-size: 14px;
  position: fixed;
  width: 100%;
  left: 0;
  top: 0;
  z-index: 1500;
  box-shadow: 0 2px 12px rgba(102, 126, 234, 0.1);
  background: #fff;
}

.logo img {
  width: 32px;
  height: 32px;
  vertical-align: middle;
  margin-right: 8px;
  border-radius: 8px;
}

.logo h3 {
  font-size: 18px;
  height: 64px;
  line-height: 64px;
  text-align: center;
  font-weight: 600;
  margin: 0;

  a {
    display: flex;
    align-items: center;
    justify-content: center;
    color: #333;
    text-decoration: none;
    transition: color 0.3s;

    &:hover {
      color: #667eea;
    }
  }
}

.nav {
  width: 100%;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav li {
  display: inline-block;
  margin: 0 4px;
}

.nav li a {
  height: 64px;
  line-height: 64px;
  display: block;
  padding: 0 24px;
  color: #555;
  text-decoration: none;
  font-size: 15px;
  font-weight: 500;
  position: relative;
  transition: all 0.3s ease;

  &::before {
    content: '';
    position: absolute;
    bottom: 12px;
    left: 50%;
    transform: translateX(-50%) scaleX(0);
    width: 20px;
    height: 3px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border-radius: 2px;
    transition: transform 0.3s ease;
  }

  &:hover {
    color: #667eea;

    &::before {
      transform: translateX(-50%) scaleX(1);
    }
  }

  &.router-link-active {
    color: #667eea;

    &::before {
      transform: translateX(-50%) scaleX(1);
    }
  }
}

.nav-right {
  text-align: right;
  height: 64px;
  line-height: 64px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.nav-right li {
  display: inline-block;
  margin-left: 12px;
}

.nav-right li a {
  height: 64px;
  line-height: 64px;
  display: block;
}

.nav-right :deep(.el-button) {
  height: 38px;
  line-height: 38px;
  padding: 0 20px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;
  border: 1px solid #667eea;
  color: #667eea;

  &:hover {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff !important;
    border-color: transparent;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
  }
}

.nav-right :deep(.el-button--info) {
  border-color: #764ba2;
  color: #764ba2;

  &:hover {
    background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
    box-shadow: 0 4px 12px rgba(118, 75, 162, 0.3);
  }
}

.el-dropdown {
  cursor: pointer;
  vertical-align: middle;
  padding: 4px;
  border-radius: 50%;
  transition: all 0.3s ease;

  &:hover {
    background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
  }
}

.el-dropdown img {
  width: 40px;
  height: 40px;
  display: inline-block;
  vertical-align: middle;
  border-radius: 50%;
  padding: 3px;
  background: linear-gradient(135deg, #f5f5f5 0%, #e8e8e8 100%);
  border: 2px solid transparent;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;

  &:hover {
    border-color: #667eea;
    box-shadow: 0 4px 16px rgba(102, 126, 234, 0.35);
    transform: scale(1.08);
  }
}

.el-menu-item i {
  color: #fff !important;
}

:deep(.el-dropdown-menu__item) {
  padding: 10px 20px;
  font-size: 14px;

  &:hover {
    background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
    color: #667eea;
  }
}

:deep(.el-select .el-input) {
  width: 80px;
}

:deep(.input-with-select .el-input-group__prepend) {
  background-color: #fff;
}

:deep(.nav-right .el-input .el-input__inner) {
  padding-right: 0;
}
</style>