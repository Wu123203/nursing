<template>
  <div>
    <el-row :gutter="15">
      <el-col :span="18">
        <div class="box-card">
          <el-card>
            <template #header>
              <el-page-header @back="$router.back()" :content="notice.title">
              </el-page-header>
            </template>
            <article class="article">
              <h1 class="page-title">{{notice.title}}</h1>
              <div class="post-info">
                <span class="yiafa"><el-icon>
                    <Clock />
                  </el-icon> {{notice.createTime}}</span>
              </div>
              <div class="article-content" v-html="notice.content">
              </div>
            </article>
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
  name: "NoticeInfo",
  data() {
    return {
      notice: {},
    }
  },
  mounted() {
    this.getInfo()
  },
  methods: {
    getInfo() {
      this.$http.get(`/notice/${this.$route.query.id}`).then(res => {
        if (res.code === 200) {
          this.notice = res.data;
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
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

.article .post-info .yiafa i {
  vertical-align: text-top;
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

/** 热门歌手 */
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
  width: 50px;
  height: 50px;
  border-radius: 50%;
  float: left;
  margin: 0 10px 0 0;
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
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  text-overflow: ellipsis;
  overflow: hidden;
  overflow-wrap: break-word;
}

.down-suiji span.down-info {
  font-size: 12px;
  color: #9b9b9b;
  display: block;
  overflow: hidden;
  margin-top: 10px;
}

.down-info i {
  margin-right: 10px;
}

.tip {
  margin: -15px;
}

.tip img {
  width: 100%;
  height: 220px;
}
</style>