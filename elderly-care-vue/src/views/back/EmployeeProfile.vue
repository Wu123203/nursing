<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>个人信息</span>
              </div>
            </template>
            <el-form :model="form" :rules="rules" ref="form" label-position="right" label-width="80px">
              <el-row :gutter="20">
                <el-col :span="12">
                  <el-form-item label="工号" prop="username">
                    <el-input v-model="form.username" placeholder="请输入工号" autocomplete="off" disabled></el-input>
                  </el-form-item>
                  <el-form-item label="姓名" prop="nickname">
                    <el-input v-model="form.nickname" placeholder="请输入姓名" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="性别" prop="sex">
                    <el-radio v-model="form.sex" :label="0">男</el-radio>
                    <el-radio v-model="form.sex" :label="1">女</el-radio>
                  </el-form-item>
                  <el-form-item label="生日" prop="birthday">
                    <el-date-picker v-model="form.birthday" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" placeholder="请选择生日" style="width:100%" autocomplete="off">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="电话" prop="telephone">
                    <el-input v-model="form.telephone" placeholder="请输入电话" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入邮箱" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="入职" prop="entryTime">
                    <el-date-picker v-model="form.entryTime" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" placeholder="请选择入职时间" style="width:100%" autocomplete="off">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="地址" prop="address">
                    <el-input type="textarea" v-model.lazy="form.address" placeholder="请输入地址" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="">
                    <el-button type="primary" @click="handleSave">保存</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </el-card>
        </div>

      </el-col>

    </el-row>
  </div>
</template>

<script>

export default {
  name: "EmployeeProfile",
  data() {
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入工号', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' }
        ],
        birthday: [
          { required: true, message: '请选择生日', trigger: 'blur' }
        ],
        telephone: [
          { required: true, message: '请输入电话', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ],
        entryTime: [
          { required: true, message: '请选择入职时间', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入籍贯', trigger: 'blur' }
        ],
      },
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    }
  },
  mounted() {
    this.getInfo();
  },

  methods: {
    //初始化信息
    getInfo() {
      this.$http.get('/employee/' + this.user.id).then(res => {
        if (res.code === 200) {
          this.form = res.data;
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },

    //保存更新
    handleSave() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$http.put("/employee", this.form).then(res => {
            console.log(res)
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: "更新成功"
              })
              this.user.nickname = this.form.nickname
              this.user.avatar = this.form.avatar
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
    },

  }
}
</script>

<style lang="scss" scoped>
::v-deep .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 110px;
  height: 110px;
  line-height: 110px;
  text-align: center;
}
.avatar {
  width: 110px;
  height: 110px;
  display: block;
}

::v-deep .cert-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.cert-uploader .el-upload:hover {
  border-color: #409eff;
}
.cert-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 150px;
  height: 140px;
  line-height: 140px;
  text-align: center;
}
.cert {
  width: 150px;
  height: 140px;
  display: block;
}
</style>