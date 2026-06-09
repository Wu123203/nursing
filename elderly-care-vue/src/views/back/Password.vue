<template>
  <div>
    <el-row>
      <el-col :span="9">
        <el-card>
          <template #header>
            <div class="clearfix">
              <span>密码修改</span>
            </div>
          </template>
          <el-form ref="form" :model="form" label-width="80px" :rules="rules">
            <el-form-item label="原始密码" prop="oldPwd">
              <el-input v-model="form.oldPwd" show-password placeholder="请输入原始密码"></el-input>
            </el-form-item>
            <el-form-item label="新的密码" prop="password">
              <el-input v-model="form.password" show-password placeholder="请输入新的密码"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="repeatPwd">
              <el-input v-model="form.repeatPwd" show-password placeholder="请输入确认密码"></el-input>
            </el-form-item>
            <el-form-item label="">
              <el-button type="primary" @click="handleSave">保存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  name: "AdminPassword",
  data() {
    return {
      form: {},
      rules: {
        oldPwd: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入新的密码', trigger: 'blur' },
        ],
        repeatPwd: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
        ],
      },
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  methods: {
    handleSave() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.password != this.form.repeatPwd) {
            this.$message.error('确认密码必须和密码一致')
            return
          }
          if (this.form.oldPwd != this.user.password) {
            this.$message.error('原始密码错误')
            return
          }
          this.form.id = this.user.id
          let url
          if (this.user.role === 0) {
            url = '/admin'
          } else {
            url = '/employee'
          }
          this.$http.put(url, this.form).then(res => {
            if (res.code === 200) {
              this.$message.success('密码修改成功，重新登录生效')
              this.$store.commit('LOGOUT')
              this.$router.push("/login")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
