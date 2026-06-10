<template>
  <div class="home-page">
    <el-row :gutter="20" class="stat-row">
      <el-col :span="6">
        <div class="stat-card stat-card-1">
          <div class="stat-icon">
            <el-icon><Users /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalLiveNum || 0 }}</div>
            <div class="stat-label">入住人数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-card-2">
          <div class="stat-icon">
            <el-icon><Building /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalEmployeeNum || 0 }}</div>
            <div class="stat-label">护工总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-card-3">
          <div class="stat-icon">
            <el-icon><Bed /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalBedNum || 0 }}</div>
            <div class="stat-label">床位总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-card-4">
          <div class="stat-icon">
            <el-icon><FileText /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalRoomNum || 0 }}</div>
            <div class="stat-label">房间总数</div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ElRow, ElCol } from 'element-plus'
import { Users, Building, Bed, FileText } from '@element-plus/icons-vue'

export default {
  name: 'Home',
  components: { ElRow, ElCol, Users, Building, Bed, FileText },
  data() {
    return {
      stats: {
        totalLiveNum: 0,
        totalEmployeeNum: 0,
        totalRoomNum: 0,
        totalBedNum: 0
      }
    }
  },
  mounted() {
    this.loadStats()
  },
  methods: {
    loadStats() {
      this.$http.get("/count").then(res => {
        if (res && res.code === 200) {
          this.stats = res.data
        }
      }).catch(err => {
        console.error("获取统计数据失败:", err)
      })
    }
  }
}
</script>

<style scoped>
.home-page {
  padding: 20px;
  background-color: #f8f9fa;
  min-height: 100vh;
}

.stat-row {
  margin-bottom: 20px;
}

.stat-card {
  height: 120px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
}

.stat-icon .el-icon {
  font-size: 32px;
  color: #fff;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: #fff;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.85);
}

.stat-card-1 {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-card-2 {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-card-3 {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-card-4 {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}
</style>