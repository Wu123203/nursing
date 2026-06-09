<template>
  <div class="room-page">
    <!-- 搜索框 -->
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-input v-model="query.roomName" placeholder="请输入房间名称" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><Search /></el-icon>
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
      <div class="handle-box" v-if="user.role == 0">
        <el-button type="primary" @click="handleAdd" class="add-btn">
          <el-icon><CirclePlus /></el-icon>
          新增房间
        </el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" class="custom-table">
        <el-table-column v-if="false" prop="id" label="ID" sortable />
        <el-table-column prop="roomName" label="房间名称" min-width="120">
          <template #default="scope">
            <span class="room-name">
              <el-icon><House /></el-icon>
              {{ scope.row.roomName }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="bedNum" label="床位数量" min-width="120">
          <template #default="scope">
            <el-tag type="info" effect="plain" class="bed-tag">
              <el-icon><Bed /></el-icon>
              {{ scope.row.bedNum }} 床
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="房间备注" min-width="180">
          <template #default="scope">
            <el-tooltip :content="scope.row.remark" placement="top" :show-after="500">
              <span class="remark-text">{{ scope.row.remark }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="提交时间" min-width="160">
          <template #default="scope">
            <span class="time-text">
              <el-icon><Clock /></el-icon>
              {{ scope.row.createTime }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150" v-if="user.role == 0">
          <template #default="scope">
            <div class="operation-btns">
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

    <!-- 房间添加 -->
    <el-dialog v-model="dialogVisible" width="30%" class="custom-dialog">
      <template #header>
        <div class="dialog-header">
          <el-icon class="dialog-icon"><Edit /></el-icon>
          <span>{{dialogTitle}}</span>
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="form" label-position="top" class="custom-form">
        <el-form-item label="房间名称" prop="roomName">
          <el-input v-model="form.roomName" placeholder="请输入房间名称" prefix-icon="House">
          </el-input>
        </el-form-item>
        <el-form-item label="床位数量" prop="bedNum">
          <el-input v-model="form.bedNum" placeholder="请输入床位数量" prefix-icon="Grid">
          </el-input>
        </el-form-item>
        <el-form-item label="房间备注" prop="remark">
          <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 6}" v-model.lazy="form.remark" placeholder="请输入房间备注" autocomplete="off" maxlength="500" show-word-limit>
          </el-input>
        </el-form-item>
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

<style scoped>
.room-page {
  padding: 20px;
}

.search-box {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.search-input {
  width: 280px;
}

.search-input :deep(.el-input__wrapper) {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.search-icon {
  color: #667eea;
}

.search-btn {
  background: linear-gradient(135deg, #fff 0%, #f5f5f5 100%);
  border: none;
  border-radius: 8px;
  color: #667eea;
  font-weight: 600;
  transition: all 0.3s ease;
}

.search-btn:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.container {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.handle-box {
  margin-bottom: 20px;
}

.add-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 8px;
  padding: 12px 24px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.custom-table {
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
}

.custom-table :deep(.el-table__header) th {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  font-weight: 600;
  text-align: center;
  padding: 12px 0;
  border-bottom: none;
}

.custom-table :deep(.el-table__body) td {
  padding: 10px 0;
  text-align: center;
  border-bottom: 1px solid #eee;
}

.custom-table :deep(.el-table__row:hover) {
  background-color: #f5f7fa;
}

.custom-table :deep(.cell) {
  padding: 0 12px;
}

.room-name {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 500;
  color: #2c3e50;
  justify-content: center;
}

.room-name .el-icon {
  color: #667eea;
  font-size: 18px;
}

.bed-tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 4px 10px;
  font-weight: 500;
}

.remark-text {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #666;
  font-size: 13px;
}

.time-text {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #888;
  font-size: 13px;
  justify-content: center;
}

.time-text .el-icon {
  color: #999;
}

.operation-btns {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.edit-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 6px;
  padding: 6px 12px;
  transition: all 0.3s ease;
}

.edit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.4);
}

.delete-btn {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
  border: none;
  border-radius: 6px;
  padding: 6px 12px;
  transition: all 0.3s ease;
}

.delete-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(255, 107, 107, 0.4);
}

.page-box {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #f0f0f0;
}

.custom-pagination .el-pager li {
  border-radius: 6px;
  margin: 0 4px;
  transition: all 0.3s ease;
}

.custom-pagination .el-pager li:hover {
  color: #667eea;
}

.custom-pagination .el-pager li.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
}

.custom-dialog {
  border-radius: 12px;
}

.custom-dialog :deep(.el-dialog) {
  border-radius: 12px;
}

.custom-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 15px 20px;
  margin: 0;
}

.custom-dialog :deep(.el-dialog__title) {
  color: #fff;
  font-size: 18px;
  font-weight: 600;
}

.custom-dialog :deep(.el-dialog__headerbtn .el-dialog__close) {
  color: #fff;
}

.custom-dialog :deep(.el-dialog__headerbtn .el-dialog__close:hover) {
  color: #f5f5f5;
}

.custom-dialog :deep(.el-dialog__body) {
  padding: 25px 20px;
}

.custom-dialog :deep(.el-dialog__footer) {
  padding: 15px 20px;
  border-top: 1px solid #eee;
}

.dialog-header {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
}

.dialog-icon {
  color: #fff;
  font-size: 22px;
}

.custom-form .el-form-item {
  margin-bottom: 20px;
}

.custom-form .el-form-item__label {
  font-weight: 500;
  color: #333;
  font-size: 14px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.cancel-btn {
  border-radius: 8px;
  padding: 10px 20px;
  transition: all 0.3s ease;
}

.cancel-btn:hover {
  border-color: #667eea;
  color: #667eea;
}

.confirm-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 8px;
  padding: 10px 24px;
  transition: all 0.3s ease;
}

.confirm-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}
</style>

<script>

export default {
  name: 'AdminRoom',
  components: {},
  data() {
    return {
      loading: true,
      query: {
        pageNum: 1,
        pageSize: 10
      },
      form: {},
      dialogVisible: false,
      dialogTitle: '',
      total: 0,
      tableData: [],
      rules: {
        roomName: [
          { required: true, message: '请输入房间名称', trigger: 'blur' }
        ],
        bedNum: [
          { required: true, message: '请输入床位数量', trigger: 'blur' }
        ],
        remark: [
          { required: true, message: '请输入房间备注', trigger: 'blur' }
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
      this.$http.get("/room", {
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

    //添加按钮事件
    handleAdd() {
      this.form = {}
      this.dialogTitle = "房间新增"
      this.dialogVisible = true
      this.$nextTick(() => {
        //清除提示
        this.$refs.form.clearValidate();
      })
    },

    //编辑按钮事件
    handleEdit(row) {
      this.form = row;
      this.dialogTitle = "房间编辑"
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
            this.$http.put("/room", this.form).then(res => {
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
            this.$http.post("/room", this.form).then(res => {
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
          console.log('error submit!!');
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
        this.$http.delete("/room/" + row.id).then(res => {
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
