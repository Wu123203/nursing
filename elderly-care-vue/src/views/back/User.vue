<template>
  <div class="user-page">
    <!-- 搜索框 -->
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-input v-model="query.username" placeholder="请输入账号" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="query.nickname" placeholder="请输入姓名" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><Postcard /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="query.status" placeholder="请选择状态" clearable class="search-select">
            <template #prefix>
              <el-icon class="search-icon"><Select /></el-icon>
            </template>
            <el-option key="0" label="启用" :value="0"></el-option>
            <el-option key="1" label="冻结" :value="1"></el-option>
          </el-select>
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
          新增用户
        </el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" class="custom-table">
        <el-table-column v-if="false" prop="id" label="ID" sortable />
        <el-table-column prop="username" label="账号" min-width="120">
          <template #default="scope">
            <span class="user-name">
              <el-icon><User /></el-icon>
              {{ scope.row.username }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="nickname" label="姓名" min-width="120">
          <template #default="scope">
            <span class="user-name">
              <el-icon><Postcard /></el-icon>
              {{ scope.row.nickname }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="性别" min-width="80" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.sex === 0 ? 'primary' : 'success'" class="gender-tag">
              <el-icon><UserFilled v-if="scope.row.sex === 0" /><Female v-else /></el-icon>
              {{ scope.row.sex === 0 ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="birthday" label="生日" min-width="120" align="center">
          <template #default="scope">
            <span class="date-cell">
              <el-icon><Calendar /></el-icon>
              {{ scope.row.birthday }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="telephone" label="电话" min-width="140" align="center">
          <template #default="scope">
            <span class="phone-cell">
              <el-icon><Phone /></el-icon>
              {{ scope.row.telephone }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="180" show-overflow-tooltip>
          <template #default="scope">
            <span class="email-cell">
              <el-icon><Message /></el-icon>
              {{ scope.row.email }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="头像" align="center" min-width="150">
          <template #default="scope">
            <el-image v-if="scope.row.avatar" class="table-avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]" preview-teleported="true" fit="cover"></el-image>
            <span v-else class="no-avatar">
              <el-icon><Picture /></el-icon>
              暂无
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址" min-width="200" show-overflow-tooltip>
          <template #default="scope">
            <span class="address-cell" :title="scope.row.address">
              <el-icon><Location /></el-icon>
              {{ scope.row.address || '--' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="余额" min-width="100" align="center">
          <template #default="scope">
            <span class="amount-cell">
              <el-icon><Coin /></el-icon>
              ¥{{ scope.row.amount }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" min-width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'" class="status-tag">
              <el-icon><CircleCheck v-if="scope.row.status === 0" /><CircleClose v-else /></el-icon>
              {{ scope.row.status === 0 ? '启用' : '冻结' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="录入时间" min-width="180" align="center">
          <template #default="scope">
            <span class="time-cell">
              <el-icon><Clock /></el-icon>
              {{ scope.row.createTime }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" min-width="180" align="center">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="handleEdit(scope.row)" class="edit-btn">
                <el-icon><EditPen /></el-icon>
                编辑
              </el-button>
              <el-button v-if="scope.row.status == 0" type="warning" size="small" @click="handleOff(scope.row)" class="freeze-btn">
                <el-icon><CircleClose /></el-icon>
                冻结
              </el-button>
              <el-button v-else type="success" size="small" @click="handleOn(scope.row)" class="enable-btn">
                <el-icon><CircleCheck /></el-icon>
                启用
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
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>
    </div>

    <!-- 添加用户 -->
    <el-dialog v-model="dialogVisible" width="40%" class="user-dialog">
      <template #header>
        <div class="dialog-header">
          <el-icon class="dialog-header-icon"><UserFilled /></el-icon>
          <span>{{dialogTitle}}</span>
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="form" label-position="right" label-width="80px" class="user-form">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="账号" prop="username">
              <el-input v-model="form.username" placeholder="请输入账号" autocomplete="off">
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="form.password" placeholder="请输入密码" autocomplete="new-password">
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="nickname">
              <el-input v-model="form.nickname" placeholder="请输入姓名" autocomplete="off">
                <template #prefix>
                  <el-icon><Postcard /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex" class="gender-form-item">
              <el-radio v-model="form.sex" :label="0" class="gender-radio">
                <el-icon><UserFilled /></el-icon>
                男
              </el-radio>
              <el-radio v-model="form.sex" :label="1" class="gender-radio">
                <el-icon><Female /></el-icon>
                女
              </el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生日" prop="birthday">
              <el-date-picker v-model="form.birthday" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" placeholder="请选择生日" style="width:100%" autocomplete="off">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="电话" prop="telephone">
              <el-input v-model="form.telephone" placeholder="请输入电话" autocomplete="off">
                <template #prefix>
                  <el-icon><Phone /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" autocomplete="off">
                <template #prefix>
                  <el-icon><Message /></el-icon>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="地址" prop="address">
              <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 8}" v-model="form.address" placeholder="请输入地址" autocomplete="off">
                <template #prefix>
                  <el-icon><Location /></el-icon>
                </template>
              </el-input>
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


export default {
  name: 'AdminUser',
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
      //参数校验
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
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
    }
  },
  mounted() {
    this.load();
  },
  methods: {
    //获取数据
    load() {
      this.loading = true
      this.$http.get("/user", { params: this.query }).then(res => {
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
      this.dialogTitle = "用户新增"
      this.dialogVisible = true
      this.$nextTick(() => {
        //清除提示
        this.$refs.form.clearValidate();
      })
    },

    //编辑按钮事件
    handleEdit(row) {
      this.form = row;
      this.dialogTitle = "用户编辑"
      this.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs.form.clearValidate();
      })
    },

    //添加保存事件
    handleSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.$http.put("/user", this.form).then(res => {
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
            this.$http.post("/user", this.form).then(res => {
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

    //冻结
    handleOff(row) {
      // 二次确认
      this.$confirm('确定要冻结账户吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.$http.put("/user", { "id": row.id, "status": 1 }).then(res => {
          console.log(res)
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

    //启用
    handleOn(row) {
      // 二次确认
      this.$confirm('确定要启动账户吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.$http.put("/user", { "id": row.id, "status": 0 }).then(res => {
          console.log(res)
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


    //删除按钮事件
    handleDelete(row) {
      // 二次确认
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.$http.delete("/user/" + row.id).then(res => {
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
    }
  }

}

</script>

<style lang="scss" scoped>
.user-page {
  padding: 20px;
}

.search-box {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  border-radius: 10px;
  margin-bottom: 15px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);

  :deep(.el-input__wrapper),
  :deep(.el-select .el-input__wrapper) {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 6px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }
}

.search-input {
  width: 180px;
  border-radius: 8px;
  
  .search-icon {
    color: #667eea;
  }
}

.search-select {
  width: 140px;
  border-radius: 8px;
}

.search-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 6px;
  padding: 8px 20px;
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

.container {
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

.handle-box {
  margin-bottom: 15px;
}

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

.custom-table {
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
  
  :deep(.el-table__header) {
    th {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: #fff;
      font-weight: 600;
      text-align: center;
      padding: 12px 0;
    }
  }
  
  :deep(.el-table__body) {
    td {
      padding: 10px 0;
      text-align: center;
    }
    
    tr:hover > td {
      background: #f5f7fa !important;
    }
  }
}

.user-name {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-weight: 500;
  color: #303133;
  
  .el-icon {
    color: #667eea;
  }
}

.gender-tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 12px;
  border-radius: 20px;
  font-weight: 500;
}

.date-cell {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: #606266;
  
  .el-icon {
    color: #e6a23c;
  }
}

.phone-cell {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: #606266;
  font-family: 'Monaco', 'Consolas', monospace;
  
  .el-icon {
    color: #409eff;
  }
}

.email-cell {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: #606266;
  
  .el-icon {
    color: #67c23a;
  }
}

.table-avatar {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  object-fit: cover;
  border: 2px solid #e4e7ed;
}

.no-avatar {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  color: #c0c4cc;
  
  .el-icon {
    font-size: 18px;
  }
}

.address-cell {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  max-width: 180px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #606266;
  
  .el-icon {
    color: #909399;
    flex-shrink: 0;
  }
}

.amount-cell {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  font-weight: 600;
  color: #f56c6c;
  font-size: 14px;
  
  .el-icon {
    font-size: 16px;
  }
}

.status-tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 12px;
  border-radius: 20px;
  font-weight: 500;
}

.time-cell {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: #606266;
  font-size: 13px;
  
  .el-icon {
    color: #909399;
  }
}

.operation-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
  flex-wrap: nowrap;
}

.edit-btn {
  border-radius: 6px;
  padding: 4px 12px;
}

.freeze-btn {
  border-radius: 6px;
  padding: 4px 12px;
}

.enable-btn {
  border-radius: 6px;
  padding: 4px 12px;
}

.delete-btn {
  border-radius: 6px;
  padding: 4px 12px;
}

.page-box {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.user-dialog {
  :deep(.el-dialog) {
    border-radius: 12px;
    overflow: hidden;
  }
  
  :deep(.el-dialog__header) {
    padding: 0;
    margin: 0;
  }
  
  :deep(.el-dialog__body) {
    padding: 25px;
  }
}

.dialog-header {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 15px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  font-size: 18px;
  font-weight: 600;
}

.dialog-header-icon {
  font-size: 22px;
}

.user-form {
  :deep(.el-form-item) {
    margin-bottom: 18px;
  }
  
  :deep(.el-input__wrapper) {
    border-radius: 8px;
    padding: 4px 11px;
  }
  
  :deep(.el-textarea__inner) {
    border-radius: 8px;
  }
}

.gender-form-item {
  :deep(.el-form-item__content) {
    display: flex;
    align-items: center;
  }
}

.gender-radio {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  margin-right: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.cancel-btn {
  border-radius: 8px;
  padding: 8px 20px;
}

.confirm-btn {
  border-radius: 8px;
  padding: 8px 20px;
}
</style>