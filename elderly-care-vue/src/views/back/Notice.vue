<template>
  <div class="notice-page">
    <!-- 搜索框 -->
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-input v-model="query.title" placeholder="请输入公告主题" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><Bell /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch" class="search-btn">
            <el-icon><Search /></el-icon>
            查询
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据展示 -->
    <div class="container">
      <div class="handle-box" v-if="user.role == 0">
        <el-button type="primary" @click="handleAdd" class="add-btn">
          <el-icon><CirclePlus /></el-icon>
          新增公告
        </el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" class="custom-table" :header-cell-style="{background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', color: '#fff', fontWeight: '600'}">
        <el-table-column v-if="false" prop="id" label="ID" width="80" align="center" />
        <el-table-column prop="title" label="公告主题" min-width="200" align="center">
          <template #default="scope">
            <span class="notice-title">
              <el-icon><Bell /></el-icon>
              {{ scope.row.title }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="发布日期" min-width="180" align="center">
          <template #default="scope">
            <span class="date-cell">
              <el-icon><Calendar /></el-icon>
              {{ scope.row.createTime }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="200" align="center">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button v-if="user.role == 0" type="primary" size="small" @click="handleEdit(scope.row)" class="edit-btn">
                <el-icon><Edit /></el-icon>
                编辑
              </el-button>
              <el-button type="primary" size="small" @click="handleDetail(scope.row)" class="detail-btn">
                <el-icon><View /></el-icon>
                详情
              </el-button>
              <el-button v-if="user.role == 0" type="danger" size="small" @click="handleDelete(scope.row)" class="delete-btn">
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页组件 -->
      <div class="pagination">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>
    </div>

    <!-- 添加对话框 -->
    <el-dialog v-model="dialogVisible" width="60%" class="notice-dialog">
      <template #header>
        <div class="dialog-header">
          <el-icon><Bell /></el-icon>
          <span>{{ dialogTitle }}</span>
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="form" label-position="right" label-width="80px" v-if="user.role == 0" class="notice-form">
        <el-form-item label="主题" prop="title">
          <el-input v-model="form.title" placeholder="请输入公告主题" ref="title"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <div id="div1" class="editor-container"></div>
        </el-form-item>
      </el-form>
      <el-card v-else class="notice-detail-card">
        <article class="article">
          <h1 class="page-title">{{ notice.title }}</h1>
          <div class="post-info">
            <span class="date">
              <el-icon><Calendar /></el-icon>
              {{ notice.createTime }}
            </span>
          </div>
          <div class="article-content" v-html="notice.content">
          </div>
        </article>
      </el-card>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleSave" class="confirm-btn" v-if="user.role == 0">确定</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script>

import E from 'wangeditor'
export default {
  name: 'AdminNotice',
  components: {},
  data() {
    return {
      loading: true,
      query: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      tableData: [],
      form: {},
      dialogVisible: false,
      dialogTitle: '',
      rules: {
        title: [
          { required: true, message: '请输入公告主题', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入公告内容', trigger: 'blur' }
        ]
      },
      editor: null,
      notice: {}
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    }
  },
  mounted() {
    this.load()
  },
  methods: {
    //获取数据
    load() {
      this.loading = true
      this.$http.get("/notice", { params: this.query }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    // 搜索按钮
    handleSearch() {
      this.query.pageNum = 1
      this.load();
    },

    //添加按钮事件
    handleAdd() {
      this.form = {}
      this.dialogTitle = "公告发布"
      this.dialogVisible = true
      this.$nextTick(() => {
        //清除提示
        this.$refs.form.clearValidate();
        if (!this.editor) {
          this.editor = new E('#div1')
          this.editor.config.excludeMenus = ['image', 'video']
          this.editor.create()
        }
        this.editor.txt.html("")
      })
    },

    //编辑按钮事件
    handleEdit(row) {
      this.form = row;
      this.dialogTitle = "公告编辑"
      this.dialogVisible = true;
      this.$nextTick(() => {
        if (!this.editor) {
          this.editor = new E('#div1')
          this.editor.config.excludeMenus = ['image', 'video']
          this.editor.create()
        }
        this.editor.txt.html(row.content)
      })
    },

    //保存按钮事件
    handleSave() {
      //获取公告内容
      const content = this.editor.txt.html()
      this.form.content = content
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.$http.put("/notice", this.form).then(res => {
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
            this.$http.post("/notice", this.form).then(res => {
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

    //删除按钮事件
    handleDelete(row) {
      // 二次确认
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.$http.delete("/notice/" + row.id).then(res => {
          if (res.code === 200) {
            this.$message({
              type: "success",
              message: res.msg
            })
            this.load()
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      })
    },

    //详情
    handleDetail(row) {
      this.dialogTitle = '公告详情'
      this.notice = row;
      this.dialogVisible = true;
    },

    //切换每页数据条数
    handleSizeChange(pageSize) {
      this.query.pageSize = pageSize;
      this.load()
    },

    //切换页码
    handleCurrentChange(pageNum) {
      this.query.pageNum = pageNum
      this.load()
    }
  }
}
</script>
<style lang="scss" scoped>
.notice-page {
  padding: 20px;
}

.search-box {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);

  :deep(.el-input__wrapper),
  :deep(.el-select .el-input__wrapper) {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 6px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
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
  margin-bottom: 16px;

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

  :deep(.el-table__row):hover > td {
    background-color: rgba(102, 126, 234, 0.05) !important;
  }
}

.notice-title {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-weight: 500;
  color: #333;

  .el-icon {
    color: #667eea;
    font-size: 16px;
  }
}

.date-cell {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  color: #666;

  .el-icon {
    color: #764ba2;
  }
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;

  :deep(.el-pagination) {
    font-weight: 500;
  }

  :deep(.el-pagination__sizes) .el-input__inner {
    border-radius: 4px;
  }

  :deep(.el-pager) li {
    border-radius: 4px;
    margin: 0 2px;
  }

  :deep(.el-pager) li.is-active {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
  }
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

  .detail-btn {
    background: linear-gradient(135deg, #48c774 0%, #36a357 100%);
    color: #fff;

    &:hover {
      background: linear-gradient(135deg, #3cb86b 0%, #2e914d 100%);
      transform: translateY(-1px);
      box-shadow: 0 2px 8px rgba(72, 199, 116, 0.4);
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

.notice-dialog {
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
    padding: 20px;
    display: flex;
    align-items: center;
    gap: 10px;
    font-size: 18px;
    font-weight: 600;

    .el-icon {
      font-size: 22px;
    }
  }

  :deep(.el-dialog__body) {
    padding: 24px;
  }

  :deep(.el-dialog__footer) {
    padding: 16px 24px;
    border-top: 1px solid #eee;
  }
}

.notice-form {
  :deep(.el-form-item__label) {
    font-weight: 500;
    color: #333;
  }

  :deep(.el-input__wrapper),
  :deep(.el-textarea__inner) {
    border-radius: 6px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);

    &:hover {
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.12);
    }

    &:focus-within {
      box-shadow: 0 2px 12px rgba(102, 126, 234, 0.25);
    }
  }
}

.editor-container {
  border: 1px solid #dcdfe6;
  border-radius: 6px;
  overflow: hidden;
}

.notice-detail-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);

  :deep(.el-card__body) {
    padding: 30px;
  }
}

.article {
  .page-title {
    margin-bottom: 24px;
    text-align: center;
    color: #202935;
    font-size: 24px;
    font-weight: bold;
  }

  .post-info {
    margin: 12px 0;
    text-align: center;
    color: #8d8f96;

    .date {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      margin: 0 16px 8px 0;
      line-height: 1;
      font-size: 14px;
      color: #764ba2;

      .el-icon {
        font-size: 16px;
      }
    }
  }

  .article-content {
    padding-top: 16px;
    font-size: 16px;
    line-height: 1.8;
    word-wrap: break-word;
    color: #202935;

    h1, h2, h3, h4, h5, h6, p {
      margin: 0 0 24px;
    }

    img {
      display: block;
      max-width: 100%;
      height: auto;
    }
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;

  .cancel-btn {
    border-radius: 6px;
    padding: 10px 20px;
    transition: all 0.3s ease;

    &:hover {
      background: #f5f7fa;
    }
  }

  .confirm-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 6px;
    padding: 10px 20px;
    transition: all 0.3s ease;

    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
    }
  }
}
</style>
