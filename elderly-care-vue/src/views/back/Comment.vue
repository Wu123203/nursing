<template>
  <div class="comment-page">
    <!-- 搜索框 -->
    <div class="search-box">
      <el-form :inline="true" :model="query" @submit.prevent>
        <el-form-item>
          <el-input v-model="query.title" placeholder="请输入护工号" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><Postcard /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="query.nickname" placeholder="请输入用户姓名" clearable @keydown.enter="handleSearch" class="search-input">
            <template #prefix>
              <el-icon class="search-icon"><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch" class="search-btn">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据展示 -->
    <div class="container">
      <div class="handle-box">
      </div>
      <el-table v-loading="loading" :data="tableData" class="custom-table">
        <el-table-column v-if="false" prop="id" label="ID" sortable />
        <el-table-column prop="title" label="护工号" min-width="300">
          <template #default="scope">
            <router-link :to="{path:'/news/info',query: {id: scope.row.newsId} }" style="color:#409EFF;cousor:pointer" target="_blank" class="news-link">
              {{scope.row.title}}
            </router-link>
          </template>
        </el-table-column>
        <el-table-column prop="nickname" label="用户姓名" min-width="120" align="center">
          <template #default="scope">
            <span class="user-name">
              <el-icon><UserFilled /></el-icon>
              {{scope.row.nickname}}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="评论内容" min-width="200" align="center">
          <template #default="scope">
            <el-tooltip :content="scope.row.content" placement="top" :show-after="500">
              <span class="content-text">{{scope.row.content}}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="提交时间" min-width="180" align="center">
          <template #default="scope">
            <span class="time-text">
              <el-icon><Clock /></el-icon>
              {{ scope.row.createTime }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" min-width="100" align="center">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="danger" size="small" @click="handleDelete(scope.row)" class="delete-btn">
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件 -->
      <div class="page-box">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-sizes="[10, 20, 50]" :page-size="query.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" class="custom-pagination">
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>

export default {
  name: 'AdminComment',
  components: {},
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

  created() {
    this.load()
  },


  methods: {
    load() {
      this.loading = true
      this.$http.get("/comment", {
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

    //删除按钮事件
    handleDelete(row) {
      // 二次确认删除
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.$http.delete("/comment/" + row.id).then(res => {
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
      this.query.pageSize = pageSize;
      this.load()
    },

    //切换页码
    handleCurrentChange(pageNum) {
      this.query.pageNum = pageNum
      this.load()
    },


  }
}
</script>

<style lang="scss" scoped>
.comment-page {
  padding: 20px;
}

.search-box {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);

  :deep(.el-form-item) {
    margin-bottom: 0;
    margin-right: 10px;
  }

  :deep(.el-form-item__content) {
    display: flex;
    align-items: center;
  }

  .search-input {
    width: 180px;

    .el-input__wrapper {
      background: rgba(255, 255, 255, 0.95);
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
      }

      .el-input__inner {
        color: #333;
        height: 36px;
        line-height: 36px;

        &::placeholder {
          color: #999;
        }
      }

      .search-icon {
        color: #667eea;
        font-size: 16px;
        margin-right: 5px;
      }
    }
  }

  .search-btn {
    background: linear-gradient(135deg, #fff 0%, #f5f5f5 100%);
    border: none;
    border-radius: 8px;
    color: #667eea;
    font-weight: 600;
    height: 36px;
    padding: 0 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;

    &:hover {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: #fff;
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
    }

    .el-icon {
      margin-right: 5px;
      font-size: 16px;
    }
  }
}

.container {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.handle-box {
  margin-bottom: 15px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.custom-table {
  width: 100%;
  border-radius: 8px;
  overflow: hidden;

  :deep(.el-table__header) {
    th {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: #fff;
      font-weight: 600;
      text-align: center;
      padding: 12px 0;
      border-bottom: none;
    }
  }

  :deep(.el-table__body) {
    td {
      padding: 10px 0;
      text-align: center;
      border-bottom: 1px solid #eee;
    }

    tr:hover > td {
      background: #f5f7fa !important;
    }

    tr.current-row > td {
      background: #ecf5ff !important;
    }
  }

  :deep(.el-table__row) {
    transition: background-color 0.3s ease;

    &:hover {
      background-color: #f5f7fa;
    }
  }

  :deep(.cell) {
    padding: 0 12px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

.news-link {
  color: #409EFF;
  text-decoration: none;
  transition: all 0.3s ease;

  &:hover {
    color: #667eea;
    text-decoration: underline;
  }
}

.user-name {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 5px;

  .el-icon {
    font-size: 14px;
    color: #667eea;
  }
}

.content-text {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #666;
  font-size: 13px;
}

.time-text {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  color: #888;
  font-size: 13px;

  .el-icon {
    font-size: 14px;
    color: #667eea;
  }
}

.operation-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
  flex-wrap: nowrap;

  .el-button {
    padding: 6px 12px;
    border-radius: 6px;
    font-size: 12px;
    border: none;
    transition: all 0.3s ease;
  }

  .delete-btn {
    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
    color: #fff;

    &:hover {
      background: linear-gradient(135deg, #ff5252 0%, #dd4949 100%);
      transform: translateY(-1px);
      box-shadow: 0 2px 8px rgba(255, 107, 107, 0.4);
    }
  }
}

.page-box {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;

  :deep(.el-pagination) {
    padding: 5px 10px;
    background: #f5f7fa;
    border-radius: 8px;

    .el-pager {
      li {
        background: transparent;
        border-radius: 4px;

        &:hover {
          color: #667eea;
        }

        &.is-active {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          color: #fff;
        }
      }
    }

    button {
      background: transparent;
      border-radius: 4px;

      &:hover {
        color: #667eea;
      }
    }

    .el-input__inner {
      border-radius: 4px;
    }
  }
}

:deep(.el-table .el-table__row:hover) {
  background-color: #f5f7fa;
}
</style>
