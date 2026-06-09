<template>
  <el-card :body-style="{ padding: '0px' }">
    <template #header>
      <div class="clearfix">
        <span>用户中心</span>
      </div>
    </template>
    <ul class="menu-nav">
      <li class="menu">
        <router-link to="/user/like"><el-icon>
            <ArrowRight />
          </el-icon> 护工点赞</router-link>
      </li>
      <li class="menu">
        <router-link to="/user/comment"><el-icon>
            <ArrowRight />
          </el-icon> 护工评论</router-link>
      </li>
      <li class="menu">
        <router-link to="/user/live"><el-icon>
            <ArrowRight />
          </el-icon> 入住管理</router-link>
      </li>
      <li class="menu">
        <router-link to="/user/leave"><el-icon>
            <ArrowRight />
          </el-icon> 出入报备</router-link>
      </li>
      <li class="menu">
        <router-link to="/user/visit"><el-icon>
            <ArrowRight />
          </el-icon> 外来访客记录</router-link>
      </li>
      <li class="menu">
        <router-link to="/user/bill"><el-icon>
            <ArrowRight />
          </el-icon> 缴费订单</router-link>
      </li>
      <li class="menu">
        <router-link to="/user/feedback"><el-icon>
            <ArrowRight />
          </el-icon> 用户意见</router-link>
      </li>
      <li class="menu">
        <router-link to="/user/profile"><el-icon>
            <ArrowRight />
          </el-icon> 个人信息</router-link>
      </li>
      <li class="menu">
        <router-link to="/user/password"><el-icon>
            <ArrowRight />
          </el-icon> 修改密码</router-link>
      </li>
    </ul>
  </el-card>
</template>

<script>
export default {
  name: "LeftMenu"
}
</script>

<style lang="scss" scoped>
.menu-nav {
  width: 100%;
  height: 520px;
}

.menu-nav li {
  display: block;
  border-bottom: 1px solid #eee;
}

.menu a {
  display: block;
  height: 40px;
  line-height: 20px;
  padding: 10px 20px;
}
.menu a i {
  vertical-align: text-top;
}
</style>