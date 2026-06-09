<template>
  <div>
    <el-row :gutter="15">
      <el-col :span="18">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>护工信息介绍</span>
              </div>
            </template>
            <ul class="bloglist" v-if="total > 0">
              <!--单图-->
              <li v-for="item in newsList" :key="item.id">
                <span class="blogpic imgscale" v-if="item.cover">
                  <router-link :to="{path:'/news/info',query: {id: item.id} }"><img :src="item.cover" alt=""></router-link>
                </span>
                <h3 class="blogtitle">
                  <router-link :to="{path:'/news/info',query: {id: item.id} }">{{item.title}}</router-link>
                </h3>
                <p class="bloginfo">
                  <span>
                    <el-icon>
                      <View />
                    </el-icon> {{item.clickNum}}
                  </span>
                  <span>
                    <el-icon>
                      <Clock />
                    </el-icon> {{item.createTime}}
                  </span>
                </p>
                <p class="blogtext" v-html="item.brief"></p>
              </li>
            </ul>
            <!-- 分页组件 -->
            <div class="page-box" v-if="total > 0">
              <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
              </el-pagination>
            </div>
            <el-empty v-else description="暂无护工信息..."></el-empty>
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

export default {
  name: "FrontNews",
  data() {
    return {
      query: {
        title: '',
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      newsList: [],
      // 阅读排行
      clickNewsList: [],
    }
  },
  mounted() {
    this.load()
    this.loadClickNews()
  },
  methods: {
    //获取数据
    load() {
      this.query.type = 1;
      this.$http.get("/news", {
        params: this.query
      }).then(res => {
        this.newsList = res.data.records
        this.total = res.data.total
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

    // 搜索按钮
    handleSearch() {
      this.query.pageNum = 1
      this.load();
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

    dateFormat(date, format) {
      return dateFormat(date, format);
    }
  },
}
</script>
<style scoped>
/* 新闻样式开始 */
.bloglist li {
  overflow: hidden;
  margin-bottom: 20px;
  border-bottom: #eee 1px dashed;
  padding-bottom: 20px;
  position: relative;
  min-height: 120px;
}

.bloglist li:hover .blogtitle a {
  color: #337ab7;
}

.blogtitle {
  margin: 10px 0 10px 0;
  font-size: 18px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.blogpic {
  float: left;
  width: 21%;
  height: 150px;
  margin-right: 20px;
  display: block;
  overflow: hidden;
  position: relative;
}

.blogpic img {
  width: 100%;
  height: 150px;
  border-radius: 10px;
}

.bplist {
  display: block;
  overflow: hidden;
}

.bplist a {
  display: block;
  float: left;
  width: 25%;
  overflow: hidden;
}

.blogtext {
  font-size: 14px;
  color: #666;
  line-height: 22px;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  display: -webkit-box;
  -webkit-line-clamp: 3;
}

.bloginfo {
  margin-bottom: 5px;
  overflow: hidden;
  color: #999;
  line-height: 25px;
}

.bloginfo span {
  margin-right: 10px;
  font-size: 13px;
}

.bloginfo span i {
  vertical-align: text-top;
}

.viewmore {
  right: 10px;
  bottom: 20px;
  position: absolute;
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