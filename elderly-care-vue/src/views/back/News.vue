<template>
  <div class="news-page">
    <!-- 搜索框 -->
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-input v-model="query.title" placeholder="请输入护工名" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><Postcard /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch" class="search-btn">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <!-- 数据展示 -->
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" @click="handleAdd" class="add-btn">
          <el-icon><CirclePlus /></el-icon>
          新增护工
        </el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" class="custom-table">
        <el-table-column v-if="false" prop="id" label="ID" sortable />
        <el-table-column prop="title" label="护工名" min-width="180" align="center">
          <template #default="scope">
            <span class="news-title">
              <el-icon><Postcard /></el-icon>
              {{ scope.row.title }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="封面" min-width="120" align="center">
          <template #default="scope">
            <el-image v-if="scope.row.cover" class="table-td-thumb" :src="scope.row.cover" :preview-src-list="[scope.row.cover]">
              <template #error>
                <div class="image-placeholder">
                  <el-icon><Picture /></el-icon>
                </div>
              </template>
            </el-image>
            <span v-else class="no-image">
              <el-icon><Picture /></el-icon>
              --
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="clickNum" label="点击量" min-width="100" align="center">
          <template #default="scope">
            <el-tag type="info" effect="plain" class="count-tag">
              <el-icon><View /></el-icon>
              {{ scope.row.clickNum }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="likeNum" label="点赞量" min-width="100" align="center">
          <template #default="scope">
            <el-tag type="success" effect="plain" class="count-tag">
              <el-icon><Star /></el-icon>
              {{ scope.row.likeNum }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="commentNum" label="评论量" min-width="100" align="center">
          <template #default="scope">
            <el-tag type="warning" effect="plain" class="count-tag">
              <el-icon><ChatLineRound /></el-icon>
              {{ scope.row.commentNum }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="添加时间" min-width="180" align="center">
          <template #default="scope">
            <span class="time-text">
              <el-icon><Clock /></el-icon>
              {{ scope.row.createTime }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" min-width="140" align="center">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="handleEdit(scope.row)" class="edit-btn">
                <el-icon><Edit /></el-icon>
                编辑
              </el-button>
              <el-button type="danger" size="small" @click="handleDelete(scope.row)" class="delete-btn">
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页组件 -->
      <div class="page-box">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" class="custom-pagination">
        </el-pagination>
      </div>
    </div>

    <!-- 对话框 -->
    <el-dialog v-model="dialogVisible" width="65%" class="custom-dialog">
      <template #header>
        <div class="dialog-header">
          <el-icon><Edit /></el-icon>
          <span>{{ dialogTitle }}</span>
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="form" label-position="right" label-width="70px" class="custom-form">
        <el-row :gutter="20">
          <el-col :span="16">
            <el-form-item label="护工名" prop="title">
              <el-input v-model="form.title" placeholder="请输入护工名" ref="title"></el-input>
            </el-form-item>
            <el-form-item label="简介" prop="brief">
              <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 8}" v-model.lazy="form.brief" placeholder="请输入护工简介" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="封面" prop="cover">
              <el-upload class="avatar-uploader" action="/api/files/upload/3" :show-file-list="false" :on-success="handleUploadSuccess" :before-upload="beforeUpload">
                <img v-if="form.cover" :src="form.cover" class="avatar">
                <el-icon v-else class="avatar-uploader-icon">
                  <Plus />
                </el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="内容" prop="content">
              <div id="div1"></div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false" class="cancel-btn">
            <el-icon><Close /></el-icon>
            取消
          </el-button>
          <el-button type="primary" @click="handleSave" class="confirm-btn">
            <el-icon><Check /></el-icon>
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import E from 'wangeditor'
export default {
  name: 'AdminNews',
  data() {
    return {
      loading: true,
      query: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      tableData: [],
      categoryList: [],
      form: {},
      dialogVisible: false,
      dialogTitle: '',
      rules: {
        title: [
          { required: true, message: '请输入护工名', trigger: 'blur' }
        ],
        brief: [
          { required: true, message: '请输入护工简介', trigger: 'blur' }
        ],
        cover: [
          { required: true, message: '请上传护工封面', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入护工内容', trigger: 'blur' }
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
  created() {
    this.load();
    this.loadCategory();
  },
  methods: {
    load() {
      this.loading = true
      this.$http.get("/news", { params: this.query }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    handleSearch() {
      this.query.pageNum = 1
      this.load();
    },

    handleAdd() {
      this.form = {}
      this.dialogTitle = "护工信息发布"
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
        if (!this.editor) {
          this.editor = new E('#div1')
          this.editor.config.excludeMenus = ['video']
          this.editor.config.uploadFileName = 'file'
          this.editor.config.uploadImgAccept = ['jpg', 'jpeg', 'png', 'gif']
          this.editor.config.uploadImgServer = "/api/files/upload/3";
          this.editor.config.uploadImgHooks = {
            customInsert: function (insertImgFn, result) {
              console.log('customInsert', result)
              insertImgFn(result.data.filePath)
            }
          }
          this.editor.create()
        }
        this.editor.txt.html("")
      })
    },

    handleEdit(row) {
      this.form = row;
      this.dialogTitle = "护工信息编辑"
      this.dialogVisible = true;
      this.$nextTick(() => {
        if (!this.editor) {
          this.editor = new E('#div1')
          this.editor.config.excludeMenus = ['video']
          this.editor.config.uploadFileName = 'file'
          this.editor.config.uploadImgAccept = ['jpg', 'jpeg', 'png', 'gif']
          this.editor.config.uploadImgServer = "/api/files/upload/3";
          this.editor.config.uploadImgHooks = {
            customInsert: function (insertImgFn, result) {
              console.log('customInsert', result)
              insertImgFn(result.data.filePath)
            }
          }
          this.editor.create()
        }
        this.editor.txt.html(row.content)
      })
    },

    handleSave() {
      const content = this.editor.txt.html()
      this.form.content = content
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.$http.put("/news", this.form).then(res => {
              console.log(res)
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: res.msg
                })
                this.load()
                this.dialogVisible = false
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          } else {
            this.$http.post("/news", this.form).then(res => {
              console.log(res)
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: res.msg
                })
                this.load()
                this.dialogVisible = false
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

    handleDelete(row) {
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.$http.delete("/news/" + row.id).then(res => {
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
          this.load()
        })
      })
    },

    loadCategory() {
      this.$http.get('/category/all').then(res => {
        this.categoryList = res.data
      })
    },

    handleUploadSuccess(res) {
      this.form.cover = res.data.filePath;
      this.$message.success("上传成功")
    },

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
    },

    handleSizeChange(pageSize) {
      this.query.pageSize = pageSize;
      this.load()
    },

    handleCurrentChange(pageNum) {
      this.query.pageNum = pageNum
      this.load();
    }
  }
}
</script>

<style lang="scss" scoped>
.news-page {
  padding: 20px;
}

.search-box {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
  
  :deep(.el-form-item) {
    margin-bottom: 0;
    margin-right: 15px;
  }
  
  :deep(.el-input__wrapper) {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 6px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }
  
  :deep(.el-input__inner) {
    color: #333;
    height: 38px;
    font-size: 14px;
  }
  
  .search-icon {
    color: #667eea;
    font-size: 16px;
    margin-right: 4px;
  }
  
  .search-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 6px;
    padding: 10px 20px;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
    
    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(102, 126, 234, 0.5);
    }
  }
}

.container {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.handle-box {
  margin-bottom: 15px;
  
  .add-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 6px;
    padding: 10px 20px;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
    
    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(102, 126, 234, 0.5);
    }
  }
}

.custom-table {
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
  
  :deep(.el-table__header) th {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    font-weight: 600;
    text-align: center;
    padding: 14px 0;
    border-bottom: none;
  }
  
  :deep(.el-table__body) td {
    padding: 12px 0;
    text-align: center;
    border-bottom: 1px solid #eee;
  }
  
  :deep(.el-table__row:hover) {
    background-color: #f5f7fa;
  }
  
  :deep(.el-table__row:last-child) td {
    border-bottom: none;
  }
  
  :deep(.el-table--border .el-table__cell) {
    border-right: 1px solid #ebe8f0;
  }
}

.table-td-thumb {
  width: 100px;
  height: 50px;
  border-radius: 4px;
  overflow: hidden;
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.image-placeholder {
  width: 100px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
  color: #c0c4cc;
  font-size: 20px;
}

.no-image {
  color: #909399;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
}

.news-title {
  font-weight: 500;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
}

.count-tag {
  border-radius: 4px;
  font-weight: 500;
  
  .el-icon {
    margin-right: 4px;
  }
}

.time-text {
  color: #606266;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  font-size: 13px;
}

.operation-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
  flex-wrap: nowrap;
  
  .el-button {
    padding: 6px 12px;
    border-radius: 6px;
    font-size: 12px;
    border: none;
    transition: all 0.3s ease;
  }
  
  .edit-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    
    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
      transform: translateY(-1px);
      box-shadow: 0 2px 8px rgba(102, 126, 234, 0.4);
    }
  }
  
  .delete-btn {
    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
    color: #fff;
    
    &:hover {
      background: linear-gradient(135deg, #ff5252 0%, #dd4949 100%);
      transform: translateY(-1px);
      box-shadow: 0 2px 8px rgba(255, 107, 107, 0.4);
    }
  }
}

.page-box {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.custom-pagination {
  :deep(.el-pager) li {
    border-radius: 6px;
    margin: 0 4px;
  }
  
  :deep(.el-pager) li:not(.is-disabled).is-active {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  }
  
  :deep(.el-pagination__jump) {
    margin-left: 16px;
  }
}

.custom-dialog {
  :deep(.el-dialog) {
    border-radius: 12px;
    overflow: hidden;
  }
  
  :deep(.el-dialog__header) {
    padding: 0;
    margin-right: 0;
  }
  
  .dialog-header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    padding: 16px 20px;
    font-size: 16px;
    font-weight: 600;
    display: flex;
    align-items: center;
    gap: 8px;
  }
  
  :deep(.el-dialog__body) {
    padding: 24px 20px;
  }
  
  :deep(.el-dialog__footer) {
    padding: 12px 20px;
    border-top: 1px solid #ebeef5;
  }
}

.custom-form {
  :deep(.el-form-item__label) {
    font-weight: 500;
  }
  
  :deep(.el-input__wrapper),
  :deep(.el-textarea__inner) {
    border-radius: 6px;
  }
  
  :deep(.el-input__inner:focus),
  :deep(.el-textarea__inner:focus) {
    border-color: #667eea;
    box-shadow: 0 0 0 1px rgba(102, 126, 234, 0.2);
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  
  .cancel-btn {
    border-radius: 6px;
    padding: 8px 16px;
  }
  
  .confirm-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 6px;
    padding: 8px 16px;
    transition: all 0.3s ease;
    
    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
    }
  }
}

::v-deep .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.3s ease;
}

.avatar-uploader .el-upload:hover {
  border-color: #667eea;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 140px;
  line-height: 140px;
  text-align: center;
  background: #f5f7fa;
}

.avatar {
  width: 200px;
  height: 140px;
  display: block;
}

::v-deep .table-td-thumb img {
  width: 100px;
  height: 50px;
}
</style>
