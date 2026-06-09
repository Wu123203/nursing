<template>
  <div class="app-wrapper">
    <Header></Header>
    <div class="login-wrapper">
      <div class="login-box">
        <div class="login-left">
          <img src="@/assets/images/login-bg.png">
        </div>
        <div class="login-right">
          <div class="login-title">系统登录</div>
          <el-form ref="form" :model="form" :rules="rules" label-width="0px" class="login-content">
            <el-form-item prop="username">
              <el-input prefix-icon="User" type="text" v-model="form.username" placeholder="请输入账号" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input prefix-icon="Lock" type="password" v-model="form.password" show-password placeholder="请输入密码" autocomplete="new-password"></el-input>
            </el-form-item>
            <el-form-item prop="role">
              <el-select v-model="form.role" placeholder="请选择身份" clearable>
                <template #prefix>
                  <el-icon>
                    <circle-check />
                  </el-icon>
                </template>
                <el-option label="用户" :value="2"></el-option>
                <el-option label="护工" :value="1"></el-option>
                <el-option label="管理员" :value="0"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="captcha">
              <el-input prefix-icon="key" class="captcha" @keydown.enter="handleLogin" v-model="form.captcha" placeholder="请输入验证码">
                <template #append>
                  <img :src="url" @click="randomCode" class="login-code-img" />
                </template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <div class="login-btn">
                <el-button type="primary" @click="handleLogin" style="width:100%">点击登录</el-button>
              </div>
            </el-form-item>
            <el-form-item>
              <div class="login-btn">
                <router-link class="register" to="/register">没有账号？去注册</router-link>
              </div>
            </el-form-item>
          </el-form>
        </div>

      </div>
    </div>
  </div>

</template>

<script>
import Header from "@/layout/front/Header";

export default {
  name: "UserLogin",
  components: {
    Header
  },
  data() {
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        role: [
          { required: true, message: '请选择身份', trigger: 'blur' },
        ],
        captcha: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ],
      },
      url: '',
      captchaKey: '',
    }
  },
  mounted() {
    this.randomCode();
  },
  methods: {
    randomCode() {
      this.$http.get("/captcha/4/100/32").then(res => {
        if (res.code === 200) {
          this.url = "data:image/gif;base64," + res.data.image;
          this.form.captchaKey = res.data.captchaKey;
        } else {
          console.error("验证码获取失败:", res.message);
        }
      }).catch(err => {
        console.error("验证码请求异常:", err);
      })
    },
    //登录
    handleLogin() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          console.log("登录请求数据:", this.form);
          this.$http.post("/login", this.form).then(res => {
            console.log("登录响应:", res);
            if (res && res.code === 200) {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              this.$store.commit('LOGIN', res.data)
              this.$router.push("/")
            } else {
              // 处理各种错误情况
              let errorMsg = '登录失败';
              if (res && res.msg) {
                errorMsg = res.msg;
              } else if (res && res.message) {
                errorMsg = res.message;
              } else if (res && res.error) {
                errorMsg = res.error;
              }
              this.$message({
                type: "error",
                message: errorMsg
              })
              // 刷新验证码
              this.randomCode();
            }
          }).catch(err => {
            console.error("登录异常:", err);
            this.$message({
              type: "error",
              message: '网络异常，请稍后重试'
            })
            // 刷新验证码
            this.randomCode();
          })
        }
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
  overflow: hidden;
}

.login-left img {
  width: 100%;
  height: 100%;
  object-fit: cover;
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

.login-content .el-form-item .el-select {
  width: 100% !important;
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

::v-deep .captcha .el-input {
  width: 280px;
}

::v-deep .captcha .el-input__wrapper {
  padding-right: 0;
}

::v-deep .captcha .el-input-group__append {
  padding: 4px;
  height: 40px;
  min-width: 120px;
  overflow: hidden;
  background: #f5f7fa;
}

::v-deep .captcha .el-input-group__append img {
  width: 100%;
  height: 100%;
  max-width: 120px;
  object-fit: contain;
  cursor: pointer;
  display: block;
}

::v-deep .el-form-item__error {
  color: #f56c6c;
  font-size: 12px;
  line-height: 1.5;
  padding-top: 4px;
  position: absolute;
  top: 100%;
  left: 0;
  background: #fff;
  padding: 4px 8px;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  z-index: 10;
}

::v-deep .el-form-item__error::before {
  content: '';
  position: absolute;
  top: -6px;
  left: 10px;
  border-width: 0 6px 6px;
  border-style: solid;
  border-color: transparent transparent #fff;
}
</style>
