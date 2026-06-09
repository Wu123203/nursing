<template>
  <div>
    <el-row :gutter="15">
      <el-col :span="4">
        <left-menu></left-menu>
      </el-col>
      <el-col :span="12">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>个人信息</span>
              </div>
            </template>
            <el-form :model="form" :rules="rules" ref="form" label-position="right" label-width="80px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="账号" prop="username">
                    <el-input v-model="form.username" placeholder="请输入账号" autocomplete="off"></el-input>
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
                  <el-form-item label="电话" prop="telephone">
                    <el-input v-model="form.telephone" placeholder="请输入电话" autocomplete="off"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="头像" prop="avatar">
                    <el-upload class="avatar-uploader" action="/api/files/upload/1" :show-file-list="false" :on-success="handleUploadSuccess" :before-upload="beforeUpload">
                      <img v-if="form.avatar" :src="form.avatar" class="avatar">
                      <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                      </el-icon>
                    </el-upload>
                  </el-form-item>
                  <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入邮箱" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="余额" prop="amount">
                    <el-input placeholder="请输入余额" v-model="form.amount" class="input-with-select" autocomplete="off" disabled>
                      <template #append>
                        <el-button icon="Position" @click="handleInvest">充值</el-button>
                      </template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="地址" prop="address">
                    <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 8}" v-model.lazy="form.address" placeholder="请输入地址" autocomplete="off"></el-input>
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
    <!-- 充值 -->
    <el-dialog v-model="investDialog.dialogVisible" width="25%">
      <template #header>
        <span class="el-dialog__title">
          <el-icon>
            <edit />
          </el-icon>
          {{investDialog.dialogTitle}}
        </span>
      </template>
      <el-form ref="investForm" :model="investDialog.form" :rules="investDialog.rules" label-position="right" label-width="80px">
        <el-form-item label="充值金额" prop="amount">
          <el-input v-model="investDialog.form.amount" placeholder="请输入充值金额" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="investDialog.dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleInvestSave">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import LeftMenu from '@/layout/front/LeftMenu.vue'
export default {
  name: "UserProfile",
  components: {
    LeftMenu
  },
  data() {
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' }
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
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' }
        ],
      },
      investDialog: {
        form: {},
        dialogVisible: false,
        dialogTitle: '',
        rules: {
          amount: [
            { required: true, message: '请输入充值金额', trigger: 'blur' }
          ]
        }
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
    //初始化信息
    getById() {
      this.$http.get('/user/' + this.user.id).then(res => {
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

    //更新个人信息
    handleSave() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$http.put("/user", this.form).then(res => {
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

    //充值
    handleInvest() {
      this.investDialog.form = { 'id': this.user.id };
      this.investDialog.dialogTitle = "我要充值"
      this.investDialog.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs.investForm.clearValidate();
      })
    },
    //充值
    handleInvestSave() {
      this.$refs.investForm.validate((valid) => {
        if (valid) {
          var totalAmount = parseInt(this.form.amount) + parseInt(this.investDialog.form.amount)
          this.investDialog.form.amount = totalAmount;
          this.$http.put("/user", this.investDialog.form).then(res => {
            console.log(res)
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: "充值成功！"
              })
              this.form.amount = totalAmount;
              this.investDialog.dialogVisible = false
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

    //el-upload图片上传回调
    handleUploadSuccess(res, file) {
      this.form.avatar = res.data.filePath;
      this.$message.success("上传成功")
    },

    //图片上传前校验
    beforeUpload(file) {
      const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
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
  width: 120px;
  height: 120px;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
.el-input-group__append .el-button {
  background: #66b1ff;
  border-color: #66b1ff;
  color: #fff;
}

.el-input-group__append .el-button:hover {
  background: #66b1ff;
  border-color: #66b1ff;
  color: #fff;
}
</style>