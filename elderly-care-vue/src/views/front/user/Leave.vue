<template>
  <div>
    <el-row :gutter="15">
      <el-col :span="4">
        <left-menu></left-menu>
      </el-col>
      <el-col :span="20">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>外出报备</span>
              </div>
            </template>
            <div>
              <el-form :inline="true" :model="query" @submit.prevent>
                <el-form-item>
                  <el-input v-model="query.title" placeholder="请输入请假主题" clearable @keydown.enter="handleSearch"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" icon="Search" @click="handleSearch">搜索</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" icon="Plus" @click="handleAdd">新增报备</el-button>
                </el-form-item>
              </el-form>
            </div>
            <el-table v-loading="loading" :data="tableData" border stripe>
              <el-table-column v-if="false" prop="id" label="ID" width="55" align="center" />
              <el-table-column prop="title" label="请假主题" width="150" />
              <el-table-column prop="roomName" label="房间名称" width="120" />
              <el-table-column prop="bedNo" label="床位编号" width="120" />
              <el-table-column prop="name" label="老人姓名" width="120" />
              <el-table-column prop="beginTime" label="开始时间" width="150" />
              <el-table-column prop="endTime" label="结束时间" width="150" />
              <el-table-column prop="content" label="外出事由" width="150" />
              <el-table-column prop="status" label="状态" width="100">
                <template #default="scope">
                  <el-tag type="info" v-if="scope.row.status === 0">审核中</el-tag>
                  <el-tag type="primary" v-else-if="scope.row.status === 1">已通过</el-tag>
                  <el-tag type="warning" v-else>已驳回</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="remark" label="审核备注" width="120">
                <template #default="scope">
                  {{scope.row.remark ? scope.row.remark : '--'}}
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="提交时间" width="180" />
              <el-table-column label="操作" fixed="right" width="110">
                <template #default="scope">
                  <el-dropdown size="small" split-button>
                    操作
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item icon="Edit" @click="handleEdit(scope.row)" v-if="scope.row.status == 0">编辑</el-dropdown-item>
                        <el-dropdown-item icon="Delete" class="danger" @click="handleDelete(scope.row)">删除</el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </template>
              </el-table-column>
            </el-table>
            <div class="page-box">
              <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
              </el-pagination>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>

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
          <el-date-picker v-model="leaveDialog.form.beginTime" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm" type="datetime" placeholder="请选择开始时间" style="width:100%" autocomplete="off" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="leaveDialog.form.endTime" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm" type="datetime" placeholder="请选择结束回来" style="width:100%" autocomplete="off" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="外出事由" prop="content">
          <el-input type="textarea" :autosize="{ minRows: 6, maxRows: 8}" v-model.lazy="leaveDialog.form.content" placeholder="请输入外出事由"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="leaveDialog.dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleLeaveSave">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import LeftMenu from '@/layout/front/LeftMenu.vue'

export default {
  name: "UserLeave",
  components: {
    LeftMenu
  },
  data() {
    return {
      loading: true,
      query: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      tableData: [],
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
            { required: true, message: '请输入外出事由', trigger: 'blur' },
          ]
        }
      },
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
      this.query.userId = this.user.id
      this.$http.get("/leave", {
        params: this.query
      }).then(res => {
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
      this.leaveDialog.form = { 'userId': this.user.id }
      this.leaveDialog.dialogTitle = "新增报备"
      this.leaveDialog.dialogVisible = true;
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      })
    },

    handleEdit(row) {
      this.leaveDialog.form = row;
      this.leaveDialog.dialogTitle = "编辑报备"
      this.leaveDialog.dialogVisible = true;
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      })
    },

    handleLeaveSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.leaveDialog.form.id) {
            this.$http.put("/leave", this.leaveDialog.form).then(res => {
              if (res.code === 200) {
                this.$message({ type: "success", message: res.msg })
                this.load()
                this.leaveDialog.dialogVisible = false
              } else {
                this.$message({ type: "error", message: res.msg })
              }
            })
          } else {
            this.$http.post("/leave", this.leaveDialog.form).then(res => {
              if (res.code === 200) {
                this.$message({ type: "success", message: res.msg })
                this.load()
                this.leaveDialog.dialogVisible = false
              } else {
                this.$message({ type: "error", message: res.msg })
              }
            })
          }
        } else {
          return false;
        }
      });
    },

    handleDelete(row) {
      this.$confirm('确定要删除吗？', '提示', { type: 'warning' }).then(() => {
        this.$http.delete("/leave/" + row.id).then(res => {
          if (res.code === 200) {
            this.$message({ type: "success", message: res.msg })
            this.load()
          } else {
            this.$message({ type: "error", message: res.msg })
          }
        })
      })
    },

    handleSizeChange(pageSize) {
      this.query.pageSize = pageSize;
      this.load()
    },

    handleCurrentChange(pageNum) {
      this.query.pageNum = pageNum
      this.load()
    }
  }
}
</script>

<style lang="scss" scoped>
.box-card {
  :deep(.el-card__header) {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    font-weight: 600;
    padding: 15px 20px;
    border-bottom: none;
    
    .clearfix {
      display: flex;
      align-items: center;
      justify-content: space-between;
      
      span {
        font-size: 16px;
      }
    }
  }
  
  :deep(.el-card__body) {
    padding: 20px;
  }
}

.el-table {
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
  margin-top: 15px;
  
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
}

.page-box {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.handle-box {
  margin-bottom: 16px;
}

.el-dialog__title {
  display: flex;
  align-items: center;
  gap: 8px;
}

.dialog-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 600;
}

.dialog-icon {
  color: #667eea;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  
  .el-button--primary {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 6px;
    
    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
    }
  }
}

.handle-box {
  margin-bottom: 16px;
}
</style>
