<template>
  <div>
    <el-row :gutter="15">
      <el-col :span="18">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>系统公告</span>
              </div>
            </template>
            <div v-if="total > 0">
              <ul class="plaintexts">
                <li v-for="item in noticeList" :key="item.id">
                  <router-link :to="{path:'/notice/info',query: {id: item.id} }">{{item.title}}</router-link>
                  <span>
                    <el-icon>
                      <clock />
                    </el-icon>
                    {{item.createTime}}
                  </span>
                </li>
              </ul>
              <!-- 分页组件 -->
              <div class="page-box">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
              </div>
            </div>
            <el-empty v-else description="暂无数据..." />
          </el-card>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>安度晚年</span>
              </div>
            </template>
            <div class="tip">
              <img src="@/assets/images/tip1.jpg" />
            </div>
          </el-card>
        </div>
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>畅享晚年</span>
              </div>
            </template>
            <div class="tip">
              <img src="@/assets/images/tip2.jpg" />
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {

  name: "Notice",
  data() {
    return {
      query: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      noticeList: [],
    }
  },
  mounted() {
    this.load()
  },
  methods: {
    load() {
      this.$http.get("/notice", {
        params: this.query
      }).then(res => {
        this.noticeList = res.data.records
        this.total = res.data.total
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
  },
}
</script>

<style lang="scss" scoped>
.plaintexts li {
  padding: 25px 2px;
  border-bottom: 1px dashed #e1e1e1;
}

.plaintexts li a > i {
  font-size: 18px;
  font-weight: 700;
  margin-right: 10px;
}

.plaintexts span {
  float: right;
  font-size: 12px;
}

.plaintexts span i {
  margin-right: 2px;
  vertical-align: top;
}

/** 温馨提示 */
.text {
  font-size: 12px;
}

.item {
  margin-bottom: 12px;
  line-height: 20px;
}

.item span {
  display: inline-block;
  width: 14px;
  height: 14px;
  line-height: 12px;
  border: 1px solid rgb(238, 178, 13);
  background: rgb(238, 178, 13);
  border-radius: 50%;
  text-align: center;
  margin-right: 5px;
  color: #fff;
}

.tip {
  margin: -15px;
}

.tip img {
  width: 100%;
  height: 220px;
}
</style>