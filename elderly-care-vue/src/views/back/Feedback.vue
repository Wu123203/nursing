<template>
  <div class="feedback-page">
    <!-- 搜索框 -->
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-input v-model="query.nickname" placeholder="请输入用户姓名" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="query.status" placeholder="请选择回复状态" clearable class="search-select">
            <template #prefix>
              <el-icon><Select /></el-icon>
            </template>
            <el-option key="0" label="待回复" :value="0"></el-option>
            <el-option key="1" label="已回复" :value="1"></el-option>
          </el-select>
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
      <el-table v-loading="loading" :data="tableData" class="custom-table">
        <el-table-column v-if="false" prop="id" label="ID" sortable />
        <el-table-column prop="nickname" label="用户姓名" min-width="120" align="center">
          <template #default="scope">
            <span class="user-name">
              <el-icon><User /></el-icon>
              {{ scope.row.nickname }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="留言内容" min-width="200" align="center">
          <template #default="scope">
            <span class="content-text">
              <el-icon><ChatLineRound /></el-icon>
              {{ scope.row.content }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" min-width="100" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.status == 0" type="info" class="status-tag">
              <el-icon><Clock /></el-icon>
              待回复
            </el-tag>
            <el-tag v-else type="success" class="status-tag">
              <el-icon><CircleCheck /></el-icon>
              已回复
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reply" label="回复内容" min-width="180" align="center">
          <template #default="scope">
            <span class="reply-text">
              <el-icon><ChatDotSquare /></el-icon>
              {{ scope.row.reply ? scope.row.reply : '--' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="提交时间" min-width="150" align="center">
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
              <el-button type="primary" size="small" @click="handleReply(scope.row)" class="reply-btn">
                <el-icon><ChatDotSquare /></el-icon>
                回复
              </el-button>
              <el-button type="danger" size="small" @click="handleDelete(scope.row)" class="delete-btn">
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-box">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" width="30%">
      <template #header>
        <div class="dialog-header">
          <el-icon class="dialog-icon"><ChatDotRound /></el-icon>
          {{ dialogTitle }}
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="replyForm" label-position="right" label-width="80px">
        <el-form-item label="内容" prop="reply">
          <el-input type="textarea" :autosize="{ minRows: 6, maxRows: 8 }" v-model.lazy="form.reply" placeholder="请输入回复内容" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="saveReply" class="confirm-btn">确定</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script>

export default {
  name: 'AdminFeedback',
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
        reply: [
          { required: true, message: '请输入回复内容', trigger: 'blur' }
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
    this.load()
  },
  methods: {
    load() {
      this.loading = true
      this.$http.get("/feedback", {
        params: this.query
      }).then(res => {
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

    //留言回复
    handleReply(row) {
      this.form = row;
      this.dialogTitle = "回复"
      this.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs.form.clearValidate();
      })
    },

    //回复保存事件
    saveReply() {
      this.$refs['replyForm'].validate((valid) => {
        if (valid) {
          this.form.status = 1;
          this.$http.put("/feedback", this.form).then(res => {
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
        this.$http.delete("/feedback/" + row.id).then(res => {
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

    //切换每页数据条数
    handleSizeChange(pageSize) {
      this.query.pageSize = pageSize;
      this.load()
    },

    //切换页码
    handleCurrentChange(pageNum) {
      this.query.pageNum = pageNum
      this.load()
    },


  }
}
</script>

<style lang="scss" scoped>
.feedback-page {
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

  :deep(.el-form-item) {
    margin-bottom: 0;
    margin-right: 15px;
  }

  :deep(.el-input),
  :deep(.el-select) {
    width: 180px;
  }

  :deep(.el-input__inner) {
    height: 38px;
    line-height: 38px;
  }

  :deep(.el-input__prefix) {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 30px;
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
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
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

  :deep(.el-table__header-wrapper) .el-table__header {
    border-radius: 8px 8px 0 0;
  }

  :deep(.el-table__body-wrapper) {
    border-radius: 0 0 8px 8px;
  }
}

.user-name,
.content-text,
.reply-text,
.time-text {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  font-size: 14px;

  .el-icon {
    font-size: 16px;
    color: #667eea;
  }
}

.status-tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 10px;
  border-radius: 4px;
  font-size: 12px;
  border: none;
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

  .reply-btn {
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

  :deep(.el-pagination) {
    padding: 0;
  }
}

.dialog-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 600;
  color: #333;

  .dialog-icon {
    font-size: 22px;
    color: #667eea;
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;

  .cancel-btn {
    border-radius: 6px;
    padding: 10px 20px;
  }

  .confirm-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 6px;
    padding: 10px 20px;

    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
    }
  }
}

:deep(.el-dialog) {
  border-radius: 12px;

  .el-dialog__header {
    padding: 20px 20px 15px;
    border-bottom: 1px solid #eee;
    margin-bottom: 0;
  }

  .el-dialog__body {
    padding: 25px 20px;
  }

  .el-dialog__footer {
    padding: 15px 20px 20px;
    border-top: 1px solid #eee;
  }
}

:deep(.el-textarea__inner) {
  border-radius: 6px;
  padding: 10px 12px;
  font-size: 14px;

  &:focus {
    border-color: #667eea;
    box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.1);
  }
}

:deep(.el-tag--info) {
  background: #f4f4f5;
  color: #909399;
}

:deep(.el-tag--success) {
  background: rgba(103, 194, 58, 0.1);
  color: #67c23a;
}
</style>