<template>
  <div class="app-wrapper">
    <Header></Header>
    <div class="login-wrapper">
      <div class="login-box">
        <div class="login-left">
          <img src="@/assets/images/login-bg.png">
        </div>
        <div class="login-right">
          <div class="login-title">系统注册</div>
          <el-form ref="form" :model="form" :rules="rules" label-width="0px" class="login-content">
            <el-form-item prop="username">
              <el-input prefix-icon="User" type="text" v-model="form.username" placeholder="请输入账号" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item prop="nickname">
              <el-input prefix-icon="User" type="text" v-model="form.nickname" placeholder="请输入姓名" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input prefix-icon="Lock" type="password" v-model="form.password" show-password placeholder="请输入密码" autocomplete="new-password"></el-input>
            </el-form-item>
            <el-form-item prop="repeatPwd">
              <el-input prefix-icon="Lock" type="password" v-model="form.repeatPwd" show-password placeholder="请输入确认密码" autocomplete="new-password"></el-input>
            </el-form-item>
            <el-form-item>
              <div class="login-btn">
                <el-button type="primary" @click="handleRegister">点击注册</el-button>
              </div>
            </el-form-item>
            <el-form-item>
              <div class="login-btn">
                <router-link class="register" to="/login">已有账号？去登录</router-link>
              </div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";
import Header from "@/layout/front/Header";
import { ElMessage } from 'element-plus';

export default {
  name: "UserRegister",
  components: {
    Header
  },
  data() {
    var repeatPwd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入确认密码'));
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        nickname: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        repeatPwd: [
          { validator: repeatPwd, trigger: 'blur' }
        ],
      },
    }
  },
  methods: {

    handleRegister() {
      this.$refs['form'].validate((valid) => {
        if (!valid) return;

        request.post("/user", this.form).then(res => {
          if (res && res.code === 200) {
            ElMessage.success(res.msg || "注册成功");
            this.$router.push("/login");
          } else {
            ElMessage.error(res && res.msg ? res.msg : '注册失败');
          }
        }).catch(err => {
          const msg = (err && err.msg) ? err.msg : '注册失败，请稍后重试';
          ElMessage.error(msg);
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.app-wrapper {
  height: 100%;
}

.login-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
  width: 90%;
  margin: 0 auto;
  padding-top: 80px;
}

.login-top {
  background: #fff;
}

.login-top h2 {
  height: 60px;
  margin-left: 20px;
  line-height: 60px;
  font-size: 20px;
}

.login-top h2 img {
  width: 35px;
  height: 35px;
  margin-right: 3px;
  vertical-align: middle;
  margin-top: -4px;
}

.login-box {
  overflow: hidden;
  width: 72%;
  margin: 80px auto;
  background: #fff;
  border-radius: 20px;
  height: 430px;
  border: solid 1px #e6e6e6;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-left {
  float: left;
  width: 68%;
  height: 100%;
  border-right: 1px solid #eee;
}

.login-left img {
  width: 100%;
  height: 100%;
}
.login-right {
  width: 32%;
  min-height: 360px;
  max-width: 420px;
  background-color: #ffffff;
  box-sizing: border-box;
  padding: 40px;
  float: right;
}

.login-title {
  width: 100%;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: 700;
}

.login-content .el-form-item {
  margin-bottom: 20px;
}

.login-btn {
  width: 100%;
  text-align: center;
}
::v-deep .login-btn .el-button {
  width: 100%;
}

.register {
  float: right;
  cursor: pointer;
  font-size: 12px;
  color: #409eff;
  display: block;
  height: 20px;
}
</style>
