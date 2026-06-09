<template>
  <div>
    <el-row>
      <el-col :span="9">
        <el-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>个人信息</span>
            </div>
          </template>
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="账号" prop="username">
              <el-input v-model="form.username" placeholder="请输入账号" disabled></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="nickname">
              <el-input v-model="form.nickname" placeholder="请输入姓名"></el-input>
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
  name: "AdminProfile",
  data() {
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  mounted() {
    this.getById();
  },
  methods: {
    //初始化
    getById() {
      this.$http.get('/admin/' + this.user.id).then(res => {
        if (res.code === 200) {
          this.form = res.data
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },

    //更新个人信息
    handleSave() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$http.put("/admin", this.form).then(res => {
            console.log(res)
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: "更新成功"
              })
              this.user.nickname = this.form.nickname
              this.$store.commit('SET_USER', this.user)
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    }
  }
}
</script>
