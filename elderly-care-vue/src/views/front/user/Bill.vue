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
                <span>缴费账单</span>
              </div>
            </template>
            <!-- 顶部搜索工具栏 -->
            <div>
              <el-form :inline="true" :model="query">
                <el-form-item>
                  <el-input v-model="query.name" placeholder="请输入老人姓名" @keydown.enter="handleSearch" clearable></el-input>
                </el-form-item>
                <el-form-item>
                  <el-select v-model="query.status" placeholder="请选择缴费状态" clearable>
                    <el-option key="0" label="待缴费" :value="0"></el-option>
                    <el-option key="1" label="已缴费" :value="1"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" icon="Search" @click="handleSearch">查询</el-button>
                </el-form-item>
              </el-form>
            </div>
            <el-table v-loading="loading" :data="tableData" border stripe>
              <el-table-column v-if="false" prop="id" label="ID" sortable />
              <el-table-column prop="roomName" label="房间名称" width="120" />
              <el-table-column prop="bedNo" label="床位编号" width="120" />
              <el-table-column prop="name" label="老人姓名" width="120" />
              <el-table-column prop="bedCost" label="床位费用" width="120" />
              <el-table-column prop="careCost" label="照护费用" width="120" />
              <el-table-column prop="foodCost" label="餐饮费用" width="120" />
              <el-table-column prop="totalCost" label="总费用" width="120" />
              <el-table-column prop="remark" label="账单备注" width="150" />
              <el-table-column prop="status" label="缴费状态" width="100">
                <template #default="scope">
                  <el-tag type="info" v-if="scope.row.status ==0">待缴费</el-tag>
                  <el-tag type="success" v-else>已缴费</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="添加时间" width="180" />
              <el-table-column label="操作" fixed="right" width="110">
                <template #default="scope">
                  <el-dropdown size="small" split-button>
                    操作
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item icon="CircleCheck" class="warning" @click="handlePay(scope.row)" v-if="scope.row.status == 0">缴费</el-dropdown-item>
                        <el-dropdown-item icon="Delete" class="danger" @click="handleDelete(scope.row)" v-if="scope.row.status == 1">删除</el-dropdown-item>
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
  </div>
</template>

<script>
import LeftMenu from '@/layout/front/LeftMenu.vue'

export default {
  name: "UserBill",
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
      this.$http.get("/bill", {
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

    //缴费
    handlePay(row) {
      // 二次确认
      this.$confirm('确定要缴费吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.$http.put("/bill/pay", { 'id': row.id }).then(res => {
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
        this.$http.delete("/bill/" + row.id).then(res => {
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
      this.$set(this.query, 'pageSize', pageSize);
      this.load()
    },
    //切换页码
    handleCurrentChange(pageNum) {
      this.$set(this.query, 'pageNum', pageNum);
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
