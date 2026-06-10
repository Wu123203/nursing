<template>
  <div class="index-page">
    <div class="container">
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :md="16" :lg="17" :xl="17">
          <div class="main-content">
            <div class="banner-section">
              <el-carousel height="380px" trigger="click" :interval="5000">
                <el-carousel-item v-for="(banner, index) in banners" :key="index">
                  <img :src="banner.src" :alt="banner.alt" class="banner-img" />
                </el-carousel-item>
              </el-carousel>
            </div>
            
            <div class="section-card news-section">
              <div class="section-header">
                <div class="header-left">
                  <i class="fa fa-file-text-o header-icon" aria-hidden="true"></i>
                  <span class="header-title">护工资讯</span>
                </div>
                <router-link to="/news" class="header-more">
                  查看更多 <i class="fa fa-arrow-right" aria-hidden="true"></i>
                </router-link>
              </div>
              
              <div class="news-list">
                <div 
                  v-for="item in newsLatestTopList" 
                  :key="item.id" 
                  class="news-item"
                >
                  <div class="news-image" v-if="item.cover">
                    <router-link :to="{path:'/news/info',query: {id: item.id}}">
                      <img :src="item.cover" :alt="item.title" />
                    </router-link>
                  </div>
                  <div class="news-content">
                    <h3 class="news-title">
                      <router-link :to="{path:'/news/info',query: {id: item.id}}">
                        {{item.title}}
                      </router-link>
                    </h3>
                    <p class="news-brief" v-html="item.brief"></p>
                    <div class="news-meta">
                      <span class="meta-item">
                        <i class="fa fa-eye" aria-hidden="true"></i>
                        {{item.clickNum}}
                      </span>
                      <span class="meta-item">
                        <i class="fa fa-comments" aria-hidden="true"></i>
                        {{item.commentNum}}
                      </span>
                      <span class="meta-item">
                        <i class="fa fa-clock-o" aria-hidden="true"></i>
                        {{dateFormat(item.createTime)}}
                      </span>
                    </div>
                  </div>
                </div>
                <div v-if="!newsLatestTopList || newsLatestTopList.length === 0" class="empty-state">
                  <i class="fa fa-file-text-o" aria-hidden="true"></i>
                  <p>暂无护工信息</p>
                </div>
              </div>
            </div>
          </div>
        </el-col>
        
        <el-col :xs="24" :sm="24" :md="8" :lg="7" :xl="7">
          <div class="sidebar">
            <div class="section-card notice-section">
              <div class="section-header">
                <div class="header-left">
                  <i class="fa fa-bell header-icon" aria-hidden="true"></i>
                  <span class="header-title">最新公告</span>
                </div>
              </div>
              <div class="notice-list">
                <router-link 
                  v-for="(item, index) in noticeLatestTopList" 
                  :key="index"
                  :to="{path:'/notice/info',query: {id: item.id}}" 
                  class="notice-item"
                >
                  <i class="fa fa-file-text-o notice-icon" aria-hidden="true"></i>
                  <span class="notice-title">{{item.title}}</span>
                  <span class="notice-date">{{dateFormat(item.createTime)}}</span>
                </router-link>
                <div v-if="!noticeLatestTopList || noticeLatestTopList.length === 0" class="empty-state small">
                  <p>暂无公告</p>
                </div>
              </div>
            </div>
            
            <div class="section-card hot-section">
              <div class="section-header">
                <div class="header-left">
                  <i class="fa fa-fire header-icon" aria-hidden="true"></i>
                  <span class="header-title">热门护工</span>
                </div>
              </div>
              <div class="hot-grid">
                <div 
                  v-for="(item, index) in newsClickTopList" 
                  :key="index" 
                  class="hot-card"
                >
                  <router-link :to="{path:'/news/info',query: {id: item.id}}" class="hot-card-link">
                    <div class="hot-card-image" v-if="item.cover">
                      <img :src="item.cover" :alt="item.title" />
                    </div>
                    <div class="hot-card-info">
                      <h4 class="hot-card-title">{{item.title}}</h4>
                      <div class="hot-card-meta">
                        <span><i class="fa fa-eye" aria-hidden="true"></i> {{item.clickNum}}</span>
                        <span><i class="fa fa-clock-o" aria-hidden="true"></i> {{dateFormat(item.createTime, 'YYYY-MM-DD')}}</span>
                      </div>
                    </div>
                  </router-link>
                </div>
                <div v-if="!newsClickTopList || newsClickTopList.length === 0" class="empty-state small">
                  <p>暂无热门护工</p>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { dateFormat } from '@/utils/utils'

export default {
  name: "Index",
  components: {},
  data() {
    return {
      newsLatestTopList: [],
      noticeLatestTopList: [],
      newsClickTopList: [],
      banners: [
        { src: require('@/assets/images/banner1.jpg'), alt: 'banner1' },
        { src: require('@/assets/images/banner2.jpg'), alt: 'banner2' },
        { src: require('@/assets/images/banner3.jpg'), alt: 'banner3' }
      ]
    }
  },
  mounted() {
    this.loadNewsLatestTopList()
    this.loadNoticeLatestLatestTopList();
    this.loadNewsClickTopList();
  },
  methods: {
    loadNewsLatestTopList() {
      this.$http.get("/news/latest/5").then(res => {
        if (res.code === 200) {
          this.newsLatestTopList = res.data;
        }
      })
    },
    loadNoticeLatestLatestTopList() {
      this.$http.get("/notice/latest/5").then(res => {
        if (res.code === 200) {
          this.noticeLatestTopList = res.data;
        }
      })
    },
    loadNewsClickTopList() {
      this.$http.get("/news/click/6").then(res => {
        if (res.code === 200) {
          this.newsClickTopList = res.data;
        }
      })
    },
    dateFormat(value, format = 'YYYY-MM-DD HH:mm') {
      return dateFormat(value, format);
    }
  }
}
</script>

<style lang="scss" scoped>
.index-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #f0f2f8 0%, #e8ecf4 100%);
  padding: 24px 0;
}

.container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
}

.section-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  margin-bottom: 24px;
  
  &:last-child {
    margin-bottom: 0;
  }
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 20px;
  border-bottom: 1px solid #f0f0f0;
  background: linear-gradient(135deg, #fafbfc 0%, #f5f7fa 100%);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.header-icon {
  font-size: 18px;
  color: #667eea;
}

.header-title {
  font-size: 15px;
  font-weight: 600;
  color: #333;
}

.header-more {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #888;
  text-decoration: none;
  transition: color 0.3s;
  
  &:hover {
    color: #667eea;
  }
}

.banner-section {
  border-radius: 16px;
  overflow: hidden;
  margin-bottom: 24px;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.15);
  
  :deep(.el-carousel) {
    .el-carousel__container {
      height: 380px;
    }
    
    .el-carousel__item {
      display: flex;
      align-items: center;
      justify-content: center;
    }
    
    .el-carousel__indicators {
      .el-carousel__indicator {
        padding: 8px 6px;
        
        .el-carousel__button {
          width: 24px;
          height: 4px;
          border-radius: 2px;
          background: rgba(255, 255, 255, 0.6);
          opacity: 1;
        }
        
        &.is-active .el-carousel__button {
          background: #fff;
          width: 32px;
        }
      }
    }
  }
}

.banner-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.news-section {
  .news-list {
    padding: 8px 0;
  }
}

.news-item {
  display: flex;
  gap: 20px;
  padding: 20px 24px;
  border-bottom: 1px solid #f5f5f5;
  transition: background 0.3s;
  
  &:last-child {
    border-bottom: none;
  }
  
  &:hover {
    background: linear-gradient(135deg, #f8f9ff 0%, #f0f3ff 100%);
    
    .news-title a {
      color: #667eea;
    }
    
    .news-image img {
      transform: scale(1.05);
    }
  }
}

.news-image {
  flex-shrink: 0;
  width: 200px;
  height: 130px;
  border-radius: 12px;
  overflow: hidden;
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.4s ease;
  }
}

.news-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.news-title {
  margin: 0 0 10px 0;
  font-size: 18px;
  line-height: 1.4;
  
  a {
    color: #333;
    text-decoration: none;
    transition: color 0.3s;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    
    &:hover {
      color: #667eea;
    }
  }
}

.news-brief {
  flex: 1;
  margin: 0 0 12px 0;
  font-size: 14px;
  color: #666;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  
  :deep(p) {
    margin: 0;
  }
}

.news-meta {
  display: flex;
  gap: 20px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #999;
  
  .el-icon {
    font-size: 14px;
  }
}

.notice-list {
  padding: 8px 12px;
}

.notice-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 6px;
  border-radius: 6px;
  text-decoration: none;
  transition: all 0.3s;
  margin-bottom: 2px;
  
  &:last-child {
    margin-bottom: 0;
  }
  
  &:hover {
    background: linear-gradient(135deg, #f0f3ff 0%, #e8ecf9 100%);
    
    .notice-title {
      color: #667eea;
    }
  }
}

.notice-icon {
  font-size: 14px;
  color: #667eea;
  flex-shrink: 0;
}

.notice-title {
  flex: 1;
  font-size: 14px;
  color: #444;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  transition: color 0.3s;
}

.notice-date {
  font-size: 12px;
  color: #999;
  flex-shrink: 0;
  margin-left: 6px;
}

.hot-grid {
  padding: 8px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.hot-card {
  background: linear-gradient(135deg, #fafbfc 0%, #f5f7fa 100%);
  border-radius: 10px;
  overflow: hidden;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.15);
    
    .hot-card-image img {
      transform: scale(1.05);
    }
    
    .hot-card-title {
      color: #667eea;
    }
  }
}

.hot-card-link {
  display: flex;
  flex-direction: column;
  text-decoration: none;
  padding: 0;
}

.hot-card-image {
  width: 100%;
  height: 100px;
  overflow: hidden;
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.4s ease;
  }
}

.hot-card-info {
  padding: 10px;
  display: flex;
  flex-direction: column;
  flex: 1;
}

.hot-card-title {
  margin: 0 0 6px 0;
  font-size: 13px;
  font-weight: 500;
  color: #333;
  line-height: 1.4;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  transition: color 0.3s ease;
}

.hot-card-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 11px;
  color: #999;
  margin-top: auto;
  
  span {
    display: flex;
    align-items: center;
    gap: 2px;
    
    .el-icon {
      font-size: 11px;
    }
  }
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 48px 20px;
  color: #ccc;
  
  &.small {
    padding: 24px 20px;
    
    p {
      font-size: 14px;
    }
  }
  
  p {
    margin: 12px 0 0 0;
    font-size: 14px;
  }
}

@media (max-width: 992px) {
  .index-page {
    padding: 16px 0;
  }
  
  .container {
    padding: 0 16px;
  }
  
  .banner-section :deep(.el-carousel .el-carousel__container) {
    height: 280px;
  }
  
  .news-item {
    flex-direction: column;
    gap: 12px;
    padding: 16px;
  }
  
  .news-image {
    width: 100%;
    height: 160px;
  }
  
  .news-title {
    font-size: 16px;
  }
}

@media (max-width: 768px) {
  .banner-section :deep(.el-carousel .el-carousel__container) {
    height: 220px;
  }
  
  .section-header {
    padding: 14px 16px;
  }
  
  .header-title {
    font-size: 16px;
  }
  
  .news-meta {
    flex-wrap: wrap;
    gap: 10px;
  }
}
</style>
