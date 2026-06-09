<template>
  <div>
    <el-row>
      <el-col :span="16">
        <el-card class="box-card">
          <template #header>
            <div class="clearfix">
              <span>入院指南</span>
            </div>
          </template>
          <el-form :model="form" :rules="rules" ref="form" label-position="right" label-width="100px">
            <el-row :gutter="15">
              <el-col :span="12">
                <el-form-item label="场所名称" prop="title">
                  <el-input v-model="form.title" placeholder="请输入场所名称"></el-input>
                </el-form-item>
                <el-form-item label="开放时间" prop="beginTime">
                  <el-time-picker v-model="form.beginTime" format="HH:mm" value-format="HH:mm" placeholder="请选择开放时间" style="width:100%" autocomplete="off">
                  </el-time-picker>
                </el-form-item>
                <el-form-item label="关闭时间" prop="endTime">
                  <el-time-picker v-model="form.endTime" format="HH:mm" value-format="HH:mm" placeholder="请选择关闭时间" style="width:100%" autocomplete="off">
                  </el-time-picker>
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="form.phone" placeholder="请输入联系电话"></el-input>
                </el-form-item>
                <el-form-item label="场所地址" prop="address">
                  <el-input v-model="form.address" placeholder="请输入场所地址"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="场所照片" prop="cover">
                  <el-upload class="avatar-uploader" action="/api/files/upload/2" :show-file-list="false" :on-success="handleUploadSuccess" :before-upload="beforeUpload">
                    <img v-if="form.cover" :src="form.cover" class="avatar">
                    <el-icon v-else class="avatar-uploader-icon">
                      <Plus />
                    </el-icon>
                  </el-upload>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="场所简介" prop="brief">
                  <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 8}" v-model.lazy="form.brief" placeholder="请输入园区简介" ref="title"></el-input>
                </el-form-item>
                <el-form-item label="场所详情" prop="content">
                  <div id="div1"></div>
                </el-form-item>
                <el-form-item label="">
                  <el-button type="primary" @click="handleSave" round>保存</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>

</template>
<script>
import E from 'wangeditor'

export default {
  name: "AdminPark",
  data() {
    return {
      form: {},
      rules: {
        title: [
          { required: true, message: '请输入园区名称', trigger: 'blur' }
        ],
        beginTime: [
          { required: true, message: '请输入开放时间', trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '请输入关闭时间', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入园区地址', trigger: 'blur' }
        ],
        cover: [
          { required: true, message: '请上传园区照片', trigger: 'blur' }
        ],
        brief: [
          { required: true, message: '请输入园区简介', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入园区详情', trigger: 'blur' }
        ]
      },
      editor: null,
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  mounted() {
    this.info();
  },
  methods: {
    //初始化信息
    info() {
      if (!this.editor) {
        this.editor = new E('#div1')
        this.editor.config.excludeMenus = ['video']
        // 配置文件上传
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgAccept = ['jpg', 'jpeg', 'png', 'gif']
        this.editor.config.uploadImgServer = "/api/files/upload/2";
        this.editor.config.uploadImgHooks = {
          // 图片上传并返回了结果，想要自己把图片插入到编辑器中
          customInsert: function (insertImgFn, result) {
            // result 即服务端返回的接口
            console.log('customInsert', result)
            // insertImgFn 可把图片插入到编辑器，传入图片 src ，执行函数即可
            insertImgFn(result.data.filePath)
          }
        }
        this.editor.create()
      }
      this.$http.get('/resthome/1').then(res => {
        if (res.code === 200) {
          if (res.data == null) {
            this.form = {}
          } else {
            this.form = res.data;
            this.editor.txt.html(res.data.content)
          }
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },

    //保存按钮事件
    handleSave() {
      //获取详情内容
      const content = this.editor.txt.html()
      this.form.content = content
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.$http.put("/resthome", this.form).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: res.msg
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          } else {
            this.$http.post("/resthome", this.form).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: res.msg
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          }
        } else {
          return false;
        }
      });
    },

    //el-upload图片上传回调：封面上传
    handleUploadSuccess(res) {
      this.form.cover = res.data.filePath
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
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 220px;
  height: 200px;
  line-height: 200px;
  text-align: center;
}
.avatar {
  width: 220px;
  height: 200px;
  display: block;
}

::v-deep .table-td-thumb img {
  width: 100px;
  height: 50px;
}
</style>