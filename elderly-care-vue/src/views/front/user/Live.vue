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
                <span>老人管理</span>
              </div>
            </template>
            <!-- 顶部搜索工具栏 -->
            <el-form :inline="true" :model="query">
              <el-form-item>
                <el-input v-model="query.name" placeholder="请输入老人姓名" @keydown.enter="handleSearch" clearable></el-input>
              </el-form-item>
              <el-form-item>
                <el-select v-model="query.status" placeholder="请选择入住状态" clearable>
                  <el-option key="0" label="已入住" :value="0"></el-option>
                  <el-option key="1" label="已离开" :value="1"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="Search" @click="handleSearch">查询</el-button>
              </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="tableData" border stripe>
              <el-table-column v-if="false" prop="id" label="ID" sortable />
              <el-table-column prop="roomName" label="房间名称" width="120" />
              <el-table-column prop="bedNo" label="床位编号" width="100" />
              <el-table-column prop="name" label="老人姓名" width="120" />
              <el-table-column prop="sex" label="老人性别" width="120">
                <template #default="scope">
                  <el-tag type="success">{{scope.row.sex === 0 ? "男" : "女"}}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="birthday" label="出生日期" width="100" />
              <el-table-column prop="phone" label="联系电话" width="120" />
              <el-table-column prop="beginTime" label="入住日期" width="100" />
              <el-table-column prop="endTime" label="退房日期" width="100">
                <template #default="scope">
                  {{scope.row.endTime ? scope.row.endTime : '--'}}
                </template>
              </el-table-column>
              <el-table-column prop="content" label="注意事项" width="200" />
              <el-table-column prop="status" label="状态">
                <template #default="scope">
                  <el-tag type="info" v-if="scope.row.status ==0">已入住</el-tag>
                  <el-tag type="success" v-else>已离开</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="remark" label="退房备注" width="100">
                <template #default="scope">
                  {{scope.row.remark ? scope.row.remark : '--'}}
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="登记时间" width="180" />
              <el-table-column label="操作" fixed="right" width="110">
                <template #default="scope">
                  <el-dropdown size="small" split-button>
                    操作
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item icon="Edit" class="primary" @click="handleLeave(scope.row)" v-if="scope.row.status == 0">外出</el-dropdown-item>
                        <el-dropdown-item icon="Delete" class="danger" @click="handleDelete(scope.row)"> 删除</el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </template>
              </el-table-column>
            </el-table>
            <!-- 分页组件 -->
            <div class="page-box">
              <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
              </el-pagination>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <!-- 添加弹窗 -->
    <el-dialog v-model="leaveDialog.dialogVisible" width="25%">
      <template #header>
        <span class="el-dialog__title">
          <el-icon>
            <edit />
          </el-icon>
          {{leaveDialog.dialogTitle}}
        </span>
      </template>
      <el-form ref="form" :model="leaveDialog.form" :rules="leaveDialog.rules" label-position="right" label-width="80px">
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
  name: "UserLive",
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
  created() {
    this.load()
  },
  methods: {

    //加载我的订单数据
    load() {
      this.loading = true
      this.query.userId = this.user.id
      this.$http.get("/live", {
        params: this.query
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    // 搜索按钮
    handleSearch() {
      this.query.pageNum = 1;
      this.load();
    },


    // 外出报备
    handleLeave(row) {
      this.leaveDialog.dialogTitle = "外出报备"
      this.leaveDialog.form = { 'liveId': row.id };
      this.leaveDialog.dialogVisible = true;
      this.$nextTick(() => {
        //清除提示
        this.$refs.form.clearValidate();
      })
    },

    // 外出报备保存
    handleLeaveSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
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
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    //删除按钮事件
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
      this.query.pageSize = pageSize
      this.load()
    },

    //切换页码
    handleCurrentChange(pageNum) {
      this.query.currentPage = pageNum
      this.load()
    },


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
</style>
