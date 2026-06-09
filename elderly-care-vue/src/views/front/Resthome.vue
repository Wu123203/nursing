<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="18">
        <div class="box-card">
          <el-card>
            <template #header>
              <div class="clearfix">
                <span>入院指南</span>
              </div>
            </template>
            <div class="newstext-box">
              <div class="shopview">
                <img :src="park.cover" />
              </div>
              <div class="product">
                <h1 class="news-title-h1">{{park.title}}</h1>
                <p class="list-nums">
                  <span title="浏览量"><el-icon>
                      <View />
                    </el-icon> {{park.clickNum}}</span>
                  <span title="更新时间"><el-icon>
                      <Clock />
                    </el-icon> {{park.createTime}}</span>
                </p>
                <p><span>开园时间：</span>{{park.beginTime}} - {{park.endTime}}</p>
                <p><span>联系电话：</span>{{park.phone}}</p>
                <p><span>景点位置：</span>{{park.address}}</p>
                <p class="product-jianjie"><span>景点简介：</span>{{park.brief}} </p>
              </div>
            </div>
            <div class="clear"></div>
            <div class="product-infos">
              <el-tabs v-model="activeName">
                <el-tab-pane label="介绍" name="first">
                  <div class="product-content" v-html="park.content"></div>
                </el-tab-pane>
              </el-tabs>
            </div>
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
import { dateFormat } from '@/utils/utils'
import { ref } from 'vue'

export default {

  name: "FrontResthome",
  data() {
    return {
      park: {},
      activeName: ref('first'),
      projectScoreTopList: [],
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    }
  },
  mounted() {
    //查询信息
    this.info()
    this.loadProjectScoreTopList();
  },
  methods: {
    info() {
      this.$http.get('/resthome/1').then(res => {
        if (res.code === 200) {
          this.park = res.data;
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },

    //查询项目评分排行
    loadProjectScoreTopList() {
      this.$http.get("/project/score/6").then(res => {
        if (res.code === 200) {
          this.projectScoreTopList = res.data;
        }
      })
    },

    //日期格式化
    dateFormat(date, format) {
      return dateFormat(date, format);
    }

  }


}
</script>

<style lang="scss" scoped>
.newstext-box {
  overflow: hidden;
}
.news-title-h1 {
  font-size: 22px;
}

.list-nums {
  font-size: 12px;
  height: 20px;
  line-height: 20px;
  margin: 15px 0 !important;
}

.list-nums span {
  margin-right: 15px;
}

.list-nums span i {
  vertical-align: text-top;
}

.shopview {
  width: 44%;
  float: left;
}

.shopview img {
  width: 100%;
  height: 280px;
  border: 1px solid #eee;
  border-radius: 8px;
}

.product {
  width: 52%;
  float: right;
}

.product h1 {
  font-size: 18px;
  margin: 15px 0;
}

.product p {
  margin: 10px 0;
  line-height: 25px;
}

.product-jianjie {
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  display: -webkit-box;
  -webkit-line-clamp: 2;
}

.product-jianjie span {
  font-weight: normal;
}

.product span {
  display: inline-block;
}

.product-scj em {
  text-decoration: line-through;
  color: #f00;
  font-style: normal;
}

.clear,
.blank {
  clear: both;
  width: 100%;
  overflow: hidden;
}

.product-infos {
  margin-top: 20px;
}

.product-content {
  line-height: 30px;
}

.picview-other {
  overflow: hidden;
  color: #888;
  margin-bottom: 15px;
}

.picview-other span {
  float: left;
  margin-right: 10px;
}

.smalltext {
  margin-bottom: 20px;
  background: #f1f1f1;
  padding: 20px;
  color: #888;
  line-height: 22px;
}
.smalltext i {
  width: 24px;
  height: 15px;
  background-position: 0 0;
  float: left;
}

.newstext {
  line-height: 24px;
  margin-bottom: 20px;
  overflow: hidden;
}

.newstext p {
  margin-bottom: 20px;
}

.newstext h2,
.newstext h3,
.newstext h4,
.newstext h5,
.newstext h6 {
  font-size: 16px;
  margin: 20px 0;
  position: relative;
  padding-left: 10px;
}

.tip {
  margin: -15px;
}

.tip img {
  width: 100%;
  height: 220px;
}
</style>