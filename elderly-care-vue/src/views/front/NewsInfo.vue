<template>
  <div>
    <el-row :gutter="15">
      <el-col :span="18">
        <div class="box-card">
          <el-card>
            <template #header>
              <el-page-header @back="$router.back()" :content="news.title">
              </el-page-header>
            </template>
            <article class="article">
              <h1 class="page-title">{{news.title}}</h1>
              <div class="post-info">
                <span>
                  <el-icon>
                    <View />
                  </el-icon> {{news.clickNum}}</span>
                <span>
                  <el-icon>
                    <Pointer />
                  </el-icon> {{news.likeNum}}</span>
                <span>
                  <el-icon>
                    <ChatDotSquare />
                  </el-icon> {{news.commentNum}}</span>
                <span>
                  <el-icon>
                    <Clock />
                  </el-icon> {{news.createTime}}</span>
              </div>
              <div class="smalltext">
                <div>
                  {{news.brief}}
                </div>
              </div>
              <div class="article-content" v-html="news.content">
              </div>
              <div class="diggit">
                <el-button type="primary" icon="Pointer" plain @click="handleLike" :disabled="user.role != 2">点赞</el-button>
              </div>
            </article>
          </el-card>
        </div>
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>评论</span>
              </div>
            </template>
            <comment @doSend="handleSave" :commentList="commentList"></comment>
          </el-card>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>点击排行</span>
              </div>
            </template>
            <div class="whitebg down-suiji">
              <ul>
                <li v-for="(item,index) in clickNewsList" :key="index">
                  <router-link :to="{path:'/news/info',query: {id: item.id} }"><i v-if="item.cover"><img :src="item.cover"></i>
                    <p>{{item.title}}</p>
                    <span class="down-info">
                      <i><el-icon>
                          <View />
                        </el-icon> {{item.clickNum}}</i>
                      <i><el-icon>
                          <Clock />
                        </el-icon> {{dateFormat(item.createTime,'YYYY-MM-DD')}}</i>
                    </span>
                  </router-link>
                </li>
              </ul>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>

</template>

<script>
import { dateFormat } from '@/utils/utils'
import comment from '@/components/Comment'

export default {
  name: "NewsInfo",
  components: {
    comment
  },
  data() {
    return {
      news: {},
      form: {},
      query: {
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      commentList: [],
      // 阅读排行
      clickNewsList: [],
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  mounted() {
    this.getById()
    this.loadComment()
    this.loadClickNews()
  },
  methods: {
    getById() {
      this.$http.get(`/news/${this.$route.query.id}`).then(res => {
        if (res.code === 200) {
          this.news = res.data;
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },

    loadComment() {
      this.query.newsId = this.$route.query.id
      this.$http.get("/comment", {
        params: this.query
      }).then(res => {
        this.commentList = res.data.records
        this.total = res.data.total
      })
    },

    //点赞
    handleLike() {
      if (!this.user.id) {
        this.$message({
          type: "error",
          message: "操作失败，请先登录系统！"
        })
        return;
      }
      //点赞保存
      this.form.userId = this.user.id
      this.form.newsId = this.$route.query.id
      this.$http.post("/like", this.form).then(res => {
        console.log(res)
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
      })
    },

    //保存评论
    handleSave(content, pid) {
      if (!content) {
        this.$message({
          type: "error",
          message: "请输入评论内容"
        })
        return;
      }
      if (!this.user.id) {
        this.$message({
          type: "error",
          message: "操作失败，您暂未登录！"
        })
        return;
      }
      //添加保存事件
      this.form.userId = this.user.id
      this.form.newsId = this.$route.query.id
      this.form.pid = pid
      this.form.content = content;
      this.$http.post("/comment", this.form).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message({
            type: "success",
            message: res.msg
          })
          this.form.content = ''
          this.loadComment()
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }

      })
    },


    //查询护工点击排行
    loadClickNews() {
      this.$http.get("/news/click/6").then(res => {
        if (res.code === 200) {
          this.clickNewsList = res.data;
        }
      })
    },

    dateFormat(date, format) {
      return dateFormat(date, format);
    }

  }


}
</script>
<style scoped>
.article .page-title {
  margin-bottom: 24px;
  text-align: center;
  color: #202935;
  font-size: 24px;
  font-weight: bold;
}
.article .post-info {
  margin: 12px 0;
  text-align: center;
  color: #8d8f96;
}

.article .post-info span {
  font-size: 12px;
  margin-right: 15px;
}

.article .post-info span i {
  vertical-align: text-top;
}

.article .smalltext {
  border: 1px dashed #eee;
  border-radius: 8px;
  margin-bottom: 20px;
  padding: 20px;
  color: #888;
  line-height: 22px;
}

.article .post-info .yiafa {
  margin: 0 16px 8px 0;
  line-height: 1;
  font-size: 13px;
}

.article-content {
  font-size: 16px;
  line-height: 1.8;
  word-wrap: break-word;
  color: #202935;
}

.article-content h1,
.article-content h2,
.article-content h3,
.article-content h4,
.article-content h5,
.article-content h6,
.article-content p {
  margin: 0 0 24px;
}

.article-content img {
  display: block;
  max-width: 100%;
  height: auto;
}

.article .diggit {
  margin-top: 20px;
  text-align: center;
}

/** 右侧点击排行 */
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
  height: 70px;
  border-radius: 5px;
  float: left;
  margin: 0 17px 0 0;
  display: inline;
  overflow: hidden;
}

.down-suiji li a > i > img {
  width: 100%;
  height: 100%;
}

.down-suiji ul li p {
  line-height: 20px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.down-suiji span.down-info {
  font-size: 12px;
  color: #9b9b9b;
  display: block;
  overflow: hidden;
  margin-top: 10px;
}

.down-info > i {
  margin-right: 10px;
  font-style: normal;
}
.down-info > i > i {
  vertical-align: text-top;
}
</style>
