<template>
  <div class="leave-page">
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-input v-model="query.name" placeholder="请输入老人姓名" @keydown.enter="handleSearch" clearable class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="query.status" placeholder="请选择审核状态" clearable class="search-select">
            <template #prefix>
              <el-icon><Select /></el-icon>
            </template>
            <el-option label="待审核" :value="0"></el-option>
            <el-option label="已通过" :value="1"></el-option>
            <el-option label="已驳回" :value="2"></el-option>
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
    <div class="container">
      <div class="handle-box" v-if="user.role == 2">
        <el-button type="primary" @click="handleAdd" class="add-btn">
          <el-icon><CirclePlus /></el-icon>
          新增请假
        </el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" class="custom-table" ref="multipleTable">
        <el-table-column v-if="false" prop="id" label="ID" width="55" align="center" />
        <el-table-column prop="roomName" label="房间名称" min-width="100" align="center">
          <template #default="scope">
            <span class="room-name">
              <el-icon><House /></el-icon>
              {{ scope.row.roomName }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="bedNo" label="床位编号" min-width="100" align="center">
          <template #default="scope">
            <span class="bed-no">
              <el-icon><Grid /></el-icon>
              {{ scope.row.bedNo }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="老人姓名" min-width="100" align="center">
          <template #default="scope">
            <span class="elder-name">
              <el-icon><User /></el-icon>
              {{ scope.row.name }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="beginTime" label="开始时间" min-width="140" align="center">
          <template #default="scope">
            <span class="time-text">
              <el-icon><Clock /></el-icon>
              {{ scope.row.beginTime }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="endTime" label="结束时间" min-width="140" align="center">
          <template #default="scope">
            <span class="time-text">
              <el-icon><Clock /></el-icon>
              {{ scope.row.endTime }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="外出事由" min-width="120" align="center">
          <template #default="scope">
            <span class="content-text">
              <el-icon><ChatLineRound /></el-icon>
              {{ scope.row.content }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" min-width="90" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 0" type="info" class="status-tag">
              <el-icon><Clock /></el-icon>
              审核中
            </el-tag>
            <el-tag v-else-if="scope.row.status === 1" type="success" class="status-tag">
              <el-icon><CircleCheck /></el-icon>
              已通过
            </el-tag>
            <el-tag v-else type="warning" class="status-tag">
              <el-icon><CircleClose /></el-icon>
              已驳回
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="审核备注" min-width="100" align="center">
          <template #default="scope">
            <span class="remark-text">
              <el-icon><ChatDotSquare /></el-icon>
              {{ scope.row.remark ? scope.row.remark : '--' }}
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
        <el-table-column label="操作" width="150" fixed="right" align="center">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="handleCheck(scope.row)" class="check-btn">
                <el-icon><CircleCheck /></el-icon>
                审核
              </el-button>
              <el-button type="danger" size="small" @click="handleDelete(scope.row)" class="delete-btn">
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[5, 10, 20]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog v-model="leaveDialog.dialogVisible" width="30%">
      <template #header>
        <div class="dialog-header">
          <el-icon class="dialog-icon"><EditPen /></el-icon>
          {{ leaveDialog.dialogTitle }}
        </div>
      </template>
      <el-form ref="form" :model="leaveDialog.form" :rules="leaveDialog.rules" label-position="right" label-width="90px">
        <el-form-item label="请假主题" prop="title">
          <el-input v-model="leaveDialog.form.title" placeholder="请输入请假主题" autocomplete="off">
            <template #prefix>
              <el-icon><Postcard /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-date-picker v-model="leaveDialog.form.beginTime" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm" type="datetime" placeholder="请选择开始时间" style="width: 100%" autocomplete="off" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="leaveDialog.form.endTime" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm" type="datetime" placeholder="请选择结束时间" style="width: 100%" autocomplete="off" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假内容" prop="content">
          <el-input type="textarea" :autosize="{ minRows: 6, maxRows: 8 }" v-model.lazy="leaveDialog.form.content" placeholder="请输入请假内容"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="leaveDialog.dialogVisible = false" class="cancel-btn">取 消</el-button>
          <el-button type="primary" @click="handleLeaveSave" class="confirm-btn">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="checkDialog.dialogVisible" width="30%">
      <template #header>
        <div class="dialog-header">
          <el-icon class="dialog-icon"><CircleCheck /></el-icon>
          {{ checkDialog.dialogTitle }}
        </div>
      </template>
      <el-form ref="checkForm" :model="checkDialog.form" :rules="checkDialog.rules" label-position="right" label-width="90px">
        <el-form-item label="审核状态" prop="status">
          <el-radio-group v-model="checkDialog.form.status">
            <el-radio :label="1" class="radio-item">
              <el-icon><CircleCheck /></el-icon>
              通过
            </el-radio>
            <el-radio :label="2" class="radio-item">
              <el-icon><CircleClose /></el-icon>
              驳回
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见" prop="remark">
          <el-input type="textarea" :autosize="{ minRows: 6, maxRows: 8 }" v-model.lazy="checkDialog.form.remark" placeholder="请输入审核意见"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="checkDialog.dialogVisible = false" class="cancel-btn">取 消</el-button>
          <el-button type="primary" @click="handleCheckSave" class="confirm-btn">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Leave',
  data() {
    return {
      loading: true,
      query: {
        pageNum: 1,
        pageSize: 5
      },
      total: 0,
      tableData: [],
      userList: [],
      leaveDialog: {
        dialogVisible: false,
        dialogTitle: '',
        form: {},
        rules: {
          title: [
            { required: true, message: '请输入请假主题', trigger: 'blur' },
          ],
          beginTime: [
            { required: true, message: '请选择开始时间', trigger: 'blur' },
          ],
          endTime: [
            { required: true, message: '请选择结束时间', trigger: 'blur' },
          ],
          content: [
            { required: true, message: '请输入请假内容', trigger: 'blur' },
          ]
        }
      },
      checkDialog: {
        dialogVisible: false,
        dialogTitle: '',
        form: {},
        rules: {
          status: [
            { required: true, message: '请选择审核状态', trigger: 'blur' },
          ],
          remark: [
            { required: true, message: '请输入审核意见', trigger: 'blur' },
          ]
        }
      }
    };
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  mounted() {
    this.load();
  },
  methods: {
    // 获取table数据
    load() {
      this.loading = true
      if (this.user.role == 2) {
        this.query.userId = this.user.id
      }
      this.$http.get("/leave", {
        params: this.query
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    // 搜索
    handleSearch() {
      this.query.pageNum = 1
      this.load();
    },

    // 添加操作
    handleAdd() {
      this.leaveDialog.form = { 'userId': this.user.id }
      this.leaveDialog.dialogTitle = "请假新增"
      this.leaveDialog.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs.form.clearValidate();
      })
    },

    // 编辑操作
    handleEdit(row) {
      this.leaveDialog.dialogTitle = "请假编辑"
      this.leaveDialog.form = row;
      this.leaveDialog.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs.form.clearValidate();
      })
    },

    // 请假保存
    handleLeaveSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.leaveDialog.form.id) {
            this.$http.put("/leave", this.leaveDialog.form).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: res.msg
                })
                this.load()
                this.leaveDialog.dialogVisible = false
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }

            })
          } else {
            this.$http.post("/leave", this.leaveDialog.form).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: res.msg
                })
                this.load()
                this.leaveDialog.dialogVisible = false
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },


    // 请假审核
    handleCheck(row) {
      this.checkDialog.dialogTitle = "请假审核"
      this.checkDialog.form = { 'id': row.id };
      this.checkDialog.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs['checkForm'].clearValidate();
      })
    },

    // 审核保存
    handleCheckSave() {
      this.$refs['checkForm'].validate((valid) => {
        if (valid) {
          this.$http.put("/leave", this.checkDialog.form).then(res => {
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: res.msg
              })
              this.load()
              this.checkDialog.dialogVisible = false
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    // 删除操作
    handleDelete(row) {
      // 二次确认删除
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.$http.delete("/leave/" + row.id).then(res => {
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

    // 切换每页几条
    handleSizeChange(pageSize) {
      this.query.pageSize = pageSize;
      this.load()
    },
    // 分页导航
    handleCurrentChange(pageNum) {
      this.query.pageNum = pageNum
      this.load();
    }
  }
};
</script>

<style lang="scss" scoped>
.leave-page {
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
  
  :deep(.el-select .el-input__wrapper) {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 6px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
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
  
  :deep(.el-table__row):hover > td {
    background: #f5f7fa !important;
  }
  
  .room-name, .bed-no, .elder-name, .time-text, .content-text, .remark-text {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
  }
  
  .status-tag {
    border-radius: 4px;
    padding: 4px 8px;
  }
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.dialog-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 600;
  
  .dialog-icon {
    color: #667eea;
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  
  .cancel-btn {
    border-radius: 6px;
  }
  
  .confirm-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 6px;
    
    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
    }
  }
}

.radio-item {
  display: flex;
  align-items: center;
  gap: 4px;
}
</style>
