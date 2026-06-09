<template>
  <div>
    <!-- 搜索框 -->
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-select v-model="query.roomId" placeholder="请选择房间名称" @change="handleQueryChange" clearable class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><House /></el-icon>
            </template>
            <el-option v-for="item in roomList" :label="item.roomName" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="query.bedId" placeholder="请选择床位编号" clearable class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><Grid /></el-icon>
            </template>
            <el-option v-for="item in bedList" :label="item.bedNo" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="query.name" placeholder="请输入老人姓名" @keydown.enter="handleSearch" clearable class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="query.status" placeholder="请选择入住状态" clearable class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><InfoFilled /></el-icon>
            </template>
            <el-option key="0" label="已入住" :value="0"></el-option>
            <el-option key="1" label="已离开" :value="1"></el-option>
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
          新增
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
        <el-table-column prop="bedNo" label="床位编号" min-width="100" align="center">
          <template #default="scope">
            <span class="bed-no">
              <el-icon><Grid /></el-icon>
              {{ scope.row.bedNo }}
            </span>
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
        <el-table-column prop="sex" label="老人性别" min-width="120" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.sex === 0 ? '' : 'warning'" effect="light" round>
              <el-icon><User /></el-icon>
              {{ scope.row.sex === 0 ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="birthday" label="出生日期" min-width="120" align="center">
          <template #default="scope">
            <span class="date-cell">
              <el-icon><Calendar /></el-icon>
              {{ scope.row.birthday }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" min-width="140" align="center">
          <template #default="scope">
            <span class="phone-cell">
              <el-icon><Phone /></el-icon>
              {{ scope.row.phone }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="beginTime" label="入住日期" min-width="120" align="center">
          <template #default="scope">
            <el-tag type="success" effect="plain">
              <el-icon><Timer /></el-icon>
              {{ scope.row.beginTime }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="endTime" label="退房日期" min-width="120" align="center">
          <template #default="scope">
            <span class="date-cell">
              <el-icon><Timer /></el-icon>
              {{ scope.row.endTime ? scope.row.endTime : '--' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="注意事项" min-width="180" align="center">
          <template #default="scope">
            <el-tooltip :content="scope.row.content" placement="top" :disabled="!scope.row.content">
              <span class="content-cell">
                <el-icon><WarningFilled /></el-icon>
                {{ scope.row.content ? (scope.row.content.length > 10 ? scope.row.content.substring(0, 10) + '...' : scope.row.content) : '--' }}
              </span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" min-width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status == 0 ? 'success' : 'info'" effect="dark" round>
              <el-icon><InfoFilled /></el-icon>
              {{ scope.row.status == 0 ? '已入住' : '已离开' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="退房备注" min-width="120" align="center">
          <template #default="scope">
            <span class="remark-cell">
              <el-icon><Document /></el-icon>
              {{ scope.row.remark ? (scope.row.remark.length > 8 ? scope.row.remark.substring(0, 8) + '...' : scope.row.remark) : '--' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="登记时间" min-width="180" align="center">
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
              <el-button type="primary" size="small" @click="handleEdit(scope.row)" class="edit-btn" v-if="scope.row.status == 0">
                <el-icon><EditPen /></el-icon>
                编辑
              </el-button>
              <el-button type="warning" size="small" @click="handleFinish(scope.row)" class="checkout-btn" v-if="scope.row.status == 0">
                <el-icon><CircleCheck /></el-icon>
                退房
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
    <el-dialog v-model="liveDialog.dialogVisible" width="40%" class="custom-dialog">
      <template #header>
        <div class="dialog-header">
          <el-icon><EditPen /></el-icon>
          {{liveDialog.dialogTitle}}
        </div>
      </template>
      <el-form :model="liveDialog.form" :rules="liveDialog.rules" ref="liveForm" label-position="right" label-width="80px" class="custom-form">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户姓名" prop="userId">
              <el-select v-model="liveDialog.form.userId" placeholder="请选择用户姓名" class="form-input">
                <el-option v-for="item in userList" :label="item.nickname" :value="item.id" :key="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="老人姓名" prop="name">
              <el-input v-model="liveDialog.form.name" placeholder="请输入老人姓名" autocomplete="off" class="form-input"></el-input>
            </el-form-item>
            <el-form-item label="老人性别" prop="sex" class="radio-group">
              <el-radio v-model="liveDialog.form.sex" :label="0">男</el-radio>
              <el-radio v-model="liveDialog.form.sex" :label="1">女</el-radio>
            </el-form-item>
            <el-form-item label="出生日期" prop="birthday">
              <el-date-picker v-model="liveDialog.form.birthday" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" placeholder="请选择老出生日期" style="width:100%" autocomplete="off" class="form-input">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="liveDialog.form.phone" placeholder="请输入联系电话" autocomplete="off" class="form-input"></el-input>
            </el-form-item>
            <el-form-item label="房间名称" prop="roomId">
              <el-select v-model="liveDialog.form.roomId" placeholder="请选择房间名称" @change="handleChange" class="form-input">
                <el-option v-for="item in roomList" :label="item.roomName" :value="item.id" :key="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="床位编号" prop="bedId">
              <el-select v-model="liveDialog.form.bedId" placeholder="请选择床位编号" clearable class="form-input">
                <el-option v-for="item in bedList" :label="item.bedNo + '（' + (item.status == 0 ? '空闲' : '占用') + '）'" :value="item.id" :key="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="入住日期" prop="beginTime">
              <el-date-picker v-model="liveDialog.form.beginTime" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" placeholder="请选择入住日期" style="width:100%" autocomplete="off" class="form-input">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="注意事项" prop="content">
              <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 8}" v-model="liveDialog.form.content" placeholder="请输入注意事项" autocomplete="off" class="form-textarea"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="liveDialog.dialogVisible = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleLiveSave" class="confirm-btn">确定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 退房 -->
    <el-dialog v-model="finishDialog.dialogVisible" width="25%" class="custom-dialog">
      <template #header>
        <div class="dialog-header">
          <el-icon><CircleCheck /></el-icon>
          {{finishDialog.dialogTitle}}
        </div>
      </template>
      <el-form :model="finishDialog.form" :rules="finishDialog.rules" ref="finishForm" label-position="right" label-width="80px" class="custom-form">
        <el-form-item label="退房日期" prop="endTime">
          <el-date-picker v-model="finishDialog.form.endTime" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" placeholder="请选择退房日期" style="width:100%" autocomplete="off" class="form-input">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="退房备注" prop="remark">
          <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 8}" v-model="finishDialog.form.remark" placeholder="请输入退房备注" autocomplete="off" class="form-textarea"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="finishDialog.dialogVisible = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleFinishSave" class="confirm-btn">确定</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script>


export default {
  name: 'AdminLive',
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
      userList: [],
      roomList: [],
      bedList: [],
      liveDialog: {
        form: {},
        dialogVisible: false,
        dialogTitle: '',
        //参数校验
        rules: {
          userId: [
            { required: true, message: '请选择用户姓名', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请输入老人姓名', trigger: 'blur' }
          ],
          sex: [
            { required: true, message: '请选择老人性别', trigger: 'blur' }
          ],
          birthday: [
            { required: true, message: '请选择出生日期', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '请输入联系电话', trigger: 'blur' }
          ],
          roomId: [
            { required: true, message: '请选择房间名称', trigger: 'blur' }
          ],
          bedId: [
            { required: true, message: '请选择床位编号', trigger: 'blur' }
          ],
          beginTime: [
            { required: true, message: '请选择入住日期', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '请输入注意事项', trigger: 'blur' }
          ],
        },
      },
      finishDialog: {
        form: {},
        dialogVisible: false,
        dialogTitle: '',
        //参数校验
        rules: {
          endTime: [
            { required: true, message: '请选择退房日期', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '请输入退房备注', trigger: 'blur' }
          ],
        },
      },

    }
  },
  mounted() {
    this.load();
    this.loadUser()
    this.loadRoom()
  },
  methods: {
    //获取数据
    load() {
      this.loading = true
      this.$http.get("/live", { params: this.query }).then(res => {
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
      this.liveDialog.form = {}
      this.liveDialog.dialogTitle = "入住登记"
      this.liveDialog.dialogVisible = true
      this.$nextTick(() => {
        //清除提示
        this.$refs.liveForm.clearValidate();
      })
    },

    //编辑按钮事件
    handleEdit(row) {
      this.liveDialog.form = row;
      this.loadBedByRoom(row.roomId)
      this.liveDialog.dialogTitle = "入住登记"
      this.liveDialog.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs.liveForm.clearValidate();
      })
    },

    //添加保存事件
    handleLiveSave() {
      this.$refs.liveForm.validate((valid) => {
        if (valid) {
          if (this.liveDialog.form.id) {
            this.$http.put("/live", this.liveDialog.form).then(res => {
              console.log(res)
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: res.msg
                })
                this.load()
                this.liveDialog.dialogVisible = false
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          } else {
            this.$http.post("/live", this.liveDialog.form).then(res => {
              console.log(res)
              if (res.code === 200) {
                this.$message({
                  type: "success",
                  message: res.msg
                })
                this.load()
                this.liveDialog.dialogVisible = false
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


    //退房
    handleFinish(row) {
      this.finishDialog.form = row;
      this.finishDialog.dialogTitle = "退房"
      this.finishDialog.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs.finishDialog.clearValidate();
      })
    },

    //退房保存
    handleFinishSave() {
      this.$refs.finishForm.validate((valid) => {
        if (valid) {
          this.$http.put("/live/finish", this.finishDialog.form).then(res => {
            console.log(res)
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: res.msg
              })
              this.load()
              this.finishDialog.dialogVisible = false
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
        this.$http.delete("/live/" + row.id).then(res => {
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


    //所有用户
    loadUser() {
      this.$http.get('/user/all').then(res => { this.userList = res.data })
    },

    //所有房间
    loadRoom() {
      this.$http.get('/room/all').then(res => { this.roomList = res.data })
    },

    //根据房间切换床位
    loadBedByRoom(roomId) {
      this.$http.get(`/bed/room/${roomId}`).then(res => {
        this.bedList = res.data
      })
    },

    //查询条件根据房间切换床位
    handleQueryChange() {
      this.loadBedByRoom(this.query.roomId);
      this.query.bedId = ''
    },


    //根据房间切换床位
    handleChange() {
      this.loadBedByRoom(this.liveDialog.form.roomId);
      this.liveDialog.form.bedId = ''
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
.live-page {
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
    margin-right: 10px;
  }

  :deep(.el-form-item__content) {
    display: flex;
    align-items: center;
  }

  .search-input {
    width: 180px;

    .el-input__wrapper {
      background: rgba(255, 255, 255, 0.95);
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
      }

      .el-input__inner {
        color: #333;
        height: 36px;
        line-height: 36px;

        &::placeholder {
          color: #999;
        }
      }

      .search-icon {
        color: #667eea;
        font-size: 16px;
        margin-right: 5px;
      }
    }

    .el-select__wrapper {
      background: rgba(255, 255, 255, 0.95);
    }
  }

  .search-btn {
    background: linear-gradient(135deg, #fff 0%, #f5f5f5 100%);
    border: none;
    border-radius: 8px;
    color: #667eea;
    font-weight: 600;
    height: 36px;
    padding: 0 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;

    &:hover {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: #fff;
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
    }

    .el-icon {
      margin-right: 5px;
      font-size: 16px;
    }
  }
}

.container {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.handle-box {
  margin-bottom: 15px;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  .add-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 8px;
    padding: 10px 20px;
    font-size: 14px;
    font-weight: 600;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);

    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4294 100%);
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
    }

    .el-icon {
      margin-right: 6px;
      font-size: 16px;
    }
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
      border-bottom: none;
    }
  }

  :deep(.el-table__body) {
    td {
      padding: 10px 0;
      text-align: center;
      border-bottom: 1px solid #eee;
    }

    tr:hover > td {
      background: #f5f7fa !important;
    }

    tr.current-row > td {
      background: #ecf5ff !important;
    }
  }

  :deep(.el-table__row) {
    transition: background-color 0.3s ease;

    &:hover {
      background-color: #f5f7fa;
    }
  }

  :deep(.cell) {
    padding: 0 12px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

.room-name, .bed-no, .elderly-name, .date-cell, .phone-cell, .content-cell, .remark-cell, .time-cell {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 5px;

  .el-icon {
    font-size: 14px;
    color: #667eea;
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
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4294 100%);
      transform: translateY(-1px);
      box-shadow: 0 2px 8px rgba(102, 126, 234, 0.4);
    }
  }

  .checkout-btn {
    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
    color: #fff;

    &:hover {
      background: linear-gradient(135deg, #e083ea 0%, #e54657 100%);
      transform: translateY(-1px);
      box-shadow: 0 2px 8px rgba(245, 87, 108, 0.4);
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
    padding: 5px 10px;
    background: #f5f7fa;
    border-radius: 8px;

    .el-pager {
      li {
        background: transparent;
        border-radius: 4px;

        &:hover {
          color: #667eea;
        }

        &.is-active {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          color: #fff;
        }
      }
    }

    button {
      background: transparent;
      border-radius: 4px;

      &:hover {
        color: #667eea;
      }
    }

    .el-input__inner {
      border-radius: 4px;
    }
  }
}

:deep(.custom-dialog) {
  border-radius: 12px;
  overflow: hidden;

  .el-dialog {
    margin-top: 10vh !important;
    border-radius: 12px;
  }

  .el-dialog__header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 15px 20px;
    margin: 0;
  }

  .el-dialog__title {
    color: #fff;
    font-size: 18px;
    font-weight: 600;
  }

  .el-dialog__headerbtn .el-dialog__close {
    color: #fff;

    &:hover {
      color: #f5f5f5;
    }
  }

  .el-dialog__body {
    padding: 25px 20px;
    max-height: 70vh;
    overflow-y: auto;
  }

  .el-dialog__footer {
    padding: 15px 20px;
    border-top: 1px solid #eee;
  }

  .dialog-header {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #fff;
    font-size: 18px;
    font-weight: 600;
  }
}

:deep(.custom-form) {
  .el-form-item {
    margin-bottom: 18px;
  }

  .el-form-item__label {
    font-weight: 500;
    color: #333;
    padding-right: 10px;
  }

  .form-input {
    width: 100%;

    .el-input__wrapper {
      border-radius: 8px;
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 2px 8px rgba(102, 126, 234, 0.2);
      }

      &.is-focus {
        box-shadow: 0 0 0 1px #667eea;
      }
    }
  }

  .form-textarea {
    width: 100%;

    .el-textarea__inner {
      border-radius: 8px;
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 2px 8px rgba(102, 126, 234, 0.2);
      }

      &:focus {
        box-shadow: 0 0 0 1px #667eea;
      }
    }
  }

  .radio-group {
    .el-form-item__content {
      display: flex;
      align-items: center;
      gap: 20px;
    }

    .el-radio {
      margin: 0;

      .el-radio__label {
        font-weight: 500;
      }
    }
  }
}

:deep(.dialog-footer) {
  display: flex;
  justify-content: flex-end;
  gap: 10px;

  .cancel-btn {
    border-radius: 8px;
    padding: 10px 20px;
    transition: all 0.3s ease;

    &:hover {
      color: #667eea;
      border-color: #667eea;
    }
  }

  .confirm-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 8px;
    padding: 10px 20px;
    transition: all 0.3s ease;

    &:hover {
      background: linear-gradient(135deg, #5a6fd6 0%, #6a4294 100%);
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
    }
  }
}

:deep(.el-table .el-table__row:hover) {
  background-color: #f5f7fa;
}

:deep(.el-tag) {
  border-radius: 6px;
}
</style>