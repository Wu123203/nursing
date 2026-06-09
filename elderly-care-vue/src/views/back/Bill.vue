<template>
  <div class="bill-page">
    <!-- 搜索框 -->
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-input v-model="query.name" placeholder="请输入老人姓名" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="query.status" placeholder="请选择缴费状态" clearable class="search-select">
            <template #prefix>
              <el-icon><Select /></el-icon>
            </template>
            <el-option key="0" label="待缴费" :value="0"></el-option>
            <el-option key="1" label="已缴费" :value="1"></el-option>
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
      <div class="handle-box">
        <el-button type="primary" @click="handleAdd" class="add-btn">
          <el-icon><CirclePlus /></el-icon>
          新增账单
        </el-button>
      </div>
      <el-table v-loading="loading" :data="tableData" class="custom-table">
        <el-table-column v-if="false" prop="id" label="ID" sortable />
        <el-table-column prop="roomName" label="房间名称" min-width="120" align="center">
          <template #default="scope">
            <span class="room-name">
              <el-icon><House /></el-icon>
              {{ scope.row.roomName }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="bedNo" label="床位编号" min-width="120" align="center">
          <template #default="scope">
            <el-tag type="info" effect="plain" class="bed-tag">
              <el-icon><Grid /></el-icon>
              {{ scope.row.bedNo }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="老人姓名" min-width="120" align="center">
          <template #default="scope">
            <span class="elderly-name">
              <el-icon><UserFilled /></el-icon>
              {{ scope.row.name }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="bedCost" label="床位费用" min-width="100" align="center">
          <template #default="scope">
            <span class="cost-text bed-cost">
              <el-icon><Wallet /></el-icon>
              ¥{{ scope.row.bedCost }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="careCost" label="照护费用" min-width="100" align="center">
          <template #default="scope">
            <span class="cost-text care-cost">
              <el-icon><FirstAidKit /></el-icon>
              ¥{{ scope.row.careCost }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="foodCost" label="餐饮费用" min-width="100" align="center">
          <template #default="scope">
            <span class="cost-text food-cost">
              <el-icon><Food /></el-icon>
              ¥{{ scope.row.foodCost }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="totalCost" label="总费用" min-width="100" align="center">
          <template #default="scope">
            <el-tag type="warning" effect="plain" class="total-tag">
              <el-icon><Money /></el-icon>
              ¥{{ scope.row.totalCost }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="账单备注" min-width="150" align="center">
          <template #default="scope">
            <el-tooltip :content="scope.row.remark || '无'" placement="top" :show-after="500">
              <span class="remark-text">{{ scope.row.remark || '--' }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="缴费状态" min-width="100" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.status == 0" type="info" effect="plain" class="status-pending">
              <el-icon><Clock /></el-icon>
              待缴费
            </el-tag>
            <el-tag v-else type="success" effect="plain" class="status-paid">
              <el-icon><CircleCheck /></el-icon>
              已缴费
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
              <el-button type="primary" size="small" @click="handleEdit(scope.row)" v-if="scope.row.status == 0" class="edit-btn">
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

    <!-- 缴费添加 -->
    <el-dialog v-model="dialogVisible" width="40%" class="custom-dialog">
      <template #header>
        <div class="dialog-header">
          <el-icon><Edit /></el-icon>
          <span>{{ dialogTitle }}</span>
        </div>
      </template>
      <el-form :model="form" :rules="rules" ref="form" label-position="right" label-width="80px" class="custom-form">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="房间名称" prop="roomId">
              <el-select v-model="form.roomId" placeholder="请选择房间名称" @change="handleChange" class="full-width">
                <el-option v-for="item in roomList" :label="item.roomName" :value="item.id" :key="item.id">
                  <span style="float: left">{{ item.roomName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 12px">
                    <el-icon><House /></el-icon> {{ item.bedNum }}床
                  </span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="老人姓名" prop="liveId">
              <el-select v-model="form.liveId" placeholder="请选择老人姓名" class="full-width">
                <el-option v-for="item in liveList" :label="item.name + ' - ' + item.bedNo" :value="item.id" :key="item.id">
                  <span style="float: left">{{ item.name }}</span>
                  <span style="float: right; color: #8492a6; font-size: 12px">
                    <el-icon><Grid /></el-icon> {{ item.bedNo }}
                  </span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="账单月份" prop="month">
              <el-date-picker v-model="form.month" format="YYYY-MM" value-format="YYYY-MM" type="month" placeholder="请选择账单月份" style="width:100%" autocomplete="off" clearable class="full-width">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="床位费用" prop="bedCost">
              <el-input v-model="form.bedCost" placeholder="请输入床位费用" autocomplete="off">
                <template #prefix>
                  <el-icon><Wallet /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="照护费用" prop="careCost">
              <el-input v-model="form.careCost" placeholder="请输入照护费用" autocomplete="off">
                <template #prefix>
                  <el-icon><FirstAidKit /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="餐饮费用" prop="foodCost">
              <el-input v-model="form.foodCost" placeholder="请输入餐饮费用" autocomplete="off">
                <template #prefix>
                  <el-icon><Food /></el-icon>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="账单备注" prop="remark">
              <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 8}" v-model="form.remark" placeholder="请输入账单备注" autocomplete="off"></el-input>
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
  name: 'AdminBill',
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
      roomList: [],
      liveList: [],
      form: {},
      dialogVisible: false,
      dialogTitle: '',
      rules: {
        roomId: [
          { required: true, message: '请选择房间编号', trigger: 'blur' }
        ],
        liveId: [
          { required: true, message: '请选择老人姓名', trigger: 'blur' }
        ],
        month: [
          { required: true, message: '请选择账单月份', trigger: 'blur' }
        ],
        bedCost: [
          { required: true, message: '请输入床位费用', trigger: 'blur' }
        ],
        careCost: [
          { required: true, message: '请输入照护费用', trigger: 'blur' }
        ],
        foodCost: [
          { required: true, message: '请输入餐饮费用', trigger: 'blur' }
        ],
        remark: [
          { required: true, message: '请输入账单备注', trigger: 'blur' }
        ],
      },
    }
  },
  mounted() {
    this.load();
    this.loadRoom()
  },
  methods: {
    load() {
      this.loading = true
      this.$http.get("/bill", { params: this.query }).then(res => {
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
      this.dialogTitle = "缴费添加"
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      })
    },

    handleEdit(row) {
      this.form = row;
      this.loadLiveByRoom(row.roomId)
      this.dialogTitle = "缴费编辑"
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      })
    },

    handleSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.$http.put("/bill", this.form).then(res => {
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
            this.$http.post("/bill", this.form).then(res => {
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
        this.$http.delete("/bill/" + row.id).then(res => {
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

    loadRoom() {
      this.$http.get('/room/all').then(res => { this.roomList = res.data })
    },

    loadLiveByRoom(roomId) {
      this.$http.get(`/live/room/${roomId}`).then(res => {
        this.liveList = res.data
      })
    },

    handleChange() {
      this.loadLiveByRoom(this.form.roomId);
      this.form.liveId = ''
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
.bill-page {
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
  
  :deep(.el-input__wrapper),
  :deep(.el-select .el-input__wrapper) {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 6px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }
  
  :deep(.el-input__inner),
  :deep(.el-select .el-input__inner) {
    color: #333;
    height: 38px;
    font-size: 14px;
  }
  
  :deep(.el-select .el-input__inner) {
    height: 38px;
    line-height: 38px;
  }
  
  .search-icon {
    color: #667eea;
    font-size: 16px;
    margin-right: 4px;
  }
  
  .search-select {
    :deep(.el-input__prefix) {
      display: flex;
      align-items: center;
    }
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

.room-name {
  font-weight: 500;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
}

.bed-tag {
  border-radius: 4px;
  font-weight: 500;
  
  .el-icon {
    margin-right: 4px;
  }
}

.elderly-name {
  font-weight: 500;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
}

.cost-text {
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  
  .el-icon {
    font-size: 14px;
  }
}

.bed-cost {
  color: #409eff;
}

.care-cost {
  color: #67c23a;
}

.food-cost {
  color: #e6a23c;
}

.total-tag {
  border-radius: 4px;
  font-weight: 600;
  font-size: 14px;
  
  .el-icon {
    margin-right: 4px;
  }
}

.remark-text {
  color: #606266;
  max-width: 140px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: block;
}

.status-pending {
  border-radius: 4px;
  font-weight: 500;
  
  .el-icon {
    margin-right: 4px;
  }
}

.status-paid {
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
  :deep(.el-select .el-input__wrapper),
  :deep(.el-textarea__inner) {
    border-radius: 6px;
  }
  
  :deep(.el-input__inner:focus),
  :deep(.el-textarea__inner:focus) {
    border-color: #667eea;
    box-shadow: 0 0 0 1px rgba(102, 126, 234, 0.2);
  }
  
  .full-width {
    width: 100%;
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

::v-deep .table-td-thumb img {
  width: 35px;
  height: 35px;
  border-radius: 50%;
}
</style>
