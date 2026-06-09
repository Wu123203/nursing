<template>
  <div>
    <el-row :gutter="15">
      <el-col :span="18">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>用户意见</span>
              </div>
            </template>
            <div class="comment" v-if="user.role == null || user.role == 2">
              <el-form ref="form" :model="form" label-width="0" :rules="rules">
                <el-form-item prop="content">
                  <el-input type="textarea" v-model.lazy="form.content" placeholder="请输入内容" rows="4" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="">
                  <el-button type="primary" @click="handleSave">保存</el-button>
                </el-form-item>
              </el-form>
            </div>
            <div v-if="total > 0">
              <ol class="commentlist">
                <li class="comment-content" v-for="(item, index) of feedbackList" :key="index">
                  <div class="comment-avatar">
                    <img class="avatar" :src="item.avatar ? item.avatar : require('@/assets/images/avatar.png')" alt="" draggable="false">
                  </div>
                  <div class="comment-main">
                    <div>
                      <h2 class="nickname">
                        {{item.nickname}}
                        <span class="time"> {{item.createTime}}</span>
                      </h2>
                      <div class="comment-box">
                        <p>{{item.content}}</p>
                        <p v-if="item.reply">
                          <el-tag type="info">答复</el-tag>{{item.reply}}
                        </p>
                      </div>
                    </div>
                  </div>
                </li>
              </ol>
              <!-- 分页组件 -->
              <div class="page-box">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
              </div>
            </div>
            <el-empty v-else description="暂无数据..."></el-empty>
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
  name: "FrontFeedback",
  data() {
    return {
      query: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      feedbackList: [],
      avatar: require('@/assets/images/avatar.png'),
      form: {},
      rules: {
        content: [
          { required: true, message: '请输入留言内容', trigger: 'blur' }
        ]
      },
      tradeFruitList: [],
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  mounted() {
    this.load();
    this.loadTradeFruitList();
  },
  methods: {
    //获取数据
    load() {
      this.$http.get("/feedback", {
        params: this.query
      }).then(res => {
        this.feedbackList = res.data.records
        this.total = res.data.total
      })
    },

    //查询水果销量排名
    loadTradeFruitList() {
      this.$http.get("/fruit/trade/6").then(res => {
        if (res.code === 200) {
          this.tradeFruitList = res.data;
        }
      })
    },

    //保存留言
    handleSave() {
      if (!this.user.id) {
        this.$message({
          type: "error",
          message: "操作失败，您暂未登录！"
        })
        return;
      }
      this.$refs.form.validate((valid) => {
        if (valid) {
          //保存数据
          this.form.userId = this.user.id
          this.$http.post("/feedback", this.form).then(res => {
            console.log(res)
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: res.msg
              })
              this.load()
              this.form.content = ''
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
/** 评论 */
.comment-title {
  font-size: 12px;
  color: #999;
  float: left;
  margin-left: -46px;
  width: 36px;
  text-align: center;
}
.comment-title .avatar {
  height: 36px;
  width: 36px;
  border-radius: 50%;
  margin-bottom: 5px;
}

.commentlist {
  padding: 0;
  margin: 0;
  list-style: none;
  margin-bottom: 10px;
  margin-top: 10px;
}
.comment-content {
  padding: 10px 0;
  border-bottom: #eee 1px dashed;
}

.comment-avatar {
  width: 40px;
  height: 40px;
  background-color: #fff;
  float: left;
  display: inline;
  position: relative;
  padding: 2px;
}
.comment-avatar .avatar {
  display: block;
  border-radius: 50%;
  width: 36px;
  height: 36px;
}
.comment-main {
  margin-left: 46px;
  color: #888;
  line-height: 22px;
  position: relative;
  font-size: 14px;
  word-wrap: break-word;
  max-height: 300px;
  overflow: hidden;
}

.comment-main div p {
  line-height: 32px;
}

::v-deep .comment-main div p img {
  vertical-align: middle;
}
.comment-main .nickname {
  font-size: 14px;
  font-weight: 700;
}
.comment-main span.time {
  display: inline-block;
  margin: 0 5px;
  font-size: 12px;
  color: #bbb;
}

.comment-box p span {
  margin-right: 5px;
}

.down-suiji ul {
  padding-top: 10px;
}

.down-suiji ul li {
  margin-bottom: 20px;
  border-bottom: 1px dotted #eee;
  padding-bottom: 10px;
  overflow: hidden;
}

.down-suiji li a > i {
  width: 100px;
  height: 60px;
  border-radius: 5px;
  float: left;
  margin: 0 17px 0 0;
  display: inline;
  overflow: hidden;
}

.down-suiji li a > i > img {
  width: 100%;
}

.down-suiji ul li p {
  line-height: 24px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.down-suiji span.down-info {
  font-size: 12px;
  color: #9b9b9b;
  display: block;
  overflow: hidden;
  margin-top: 10px;
}

.tip {
  margin: -15px;
}

.tip img {
  width: 100%;
  height: 220px;
}
</style>