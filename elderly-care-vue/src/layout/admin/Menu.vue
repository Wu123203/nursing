<template>
  <div v-if="!item.hidden">
    <template v-if="hasOneShowingChild(item.children, item) && (!onlyOneChild.children || onlyOneChild.noShowingChildren) && !item.alwaysShow">
      <!-- 如果是外链 -->
      <a v-if="isExternal(resolvePath(onlyOneChild.path))" :href="resolvePath(onlyOneChild.path)" target="_blank" rel="noopener">
        <el-menu-item :index="resolvePath(onlyOneChild.path)">
          <item :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)" />
          <template #title>{{ onlyOneChild.meta.title }}</template>
        </el-menu-item>
      </a>
      <!-- 如果是路由地址 -->
      <el-menu-item v-else :index="resolvePath(onlyOneChild.path)">
        <item :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)" />
        <template #title>{{ onlyOneChild.meta.title }}</template>
      </el-menu-item>
    </template>
    <el-sub-menu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
      <template #title>
        <item v-if="item.meta" :icon="item.meta && item.meta.icon" />
        <span> {{ item.meta.title }}</span>
      </template>
      <Menu v-for="child in item.children" :key="child.path" :item="child" :base-path="resolvePath(child.path)" />
    </el-sub-menu>
  </div>
</template>

<script>
import path from 'path'
import { isExternal } from '@/utils/validate'
import Item from './Item'

export default {
  name: 'Menu',
  components: { Item },
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data() {
    this.onlyOneChild = null
    return {}
  },
  methods: {
    hasOneShowingChild(children = [], parent) {
      const showingChildren = children.filter((item) => {
        if (item.hidden) {
          return false
        } else {
          // Temp set(will be used if only has one showing child)
          this.onlyOneChild = item
          return true
        }
      })

      // When there is only one child router, the child router is displayed by default
      if (showingChildren.length === 1) {
        return true
      }

      // Show parent if there are no child router to display
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ...parent, path: '', noShowingChildren: true }
        return true
      }

      return false
    },

    resolvePath(routePath) {
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(this.basePath)) {
        return this.basePath
      }
      return path.resolve(this.basePath, routePath)
    },

    isExternal(to) {
      return isExternal(to);
    }

  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-submenu [class^="fa"] {
  vertical-align: middle;
  margin-right: 5px;
  width: 24px;
  text-align: center;
  font-size: 18px;
}

::v-deep .el-menu-item {
  height: 50px;
  line-height: 50px;
  margin: 4px 10px;
  border-radius: 8px;
  padding: 0 20px !important;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;

  &:hover {
    background: linear-gradient(135deg, rgba(102, 126, 234, 0.15) 0%, rgba(118, 75, 162, 0.15) 100%) !important;
    transform: translateX(4px);
    
    i {
      color: #667eea !important;
      transform: scale(1.1);
    }
  }

  &.is-active {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
    color: #fff !important;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);

    i {
      color: #fff !important;
    }

    &::before {
      content: '';
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
      width: 4px;
      height: 24px;
      background: rgba(255, 255, 255, 0.8);
      border-radius: 0 2px 2px 0;
    }
  }

  i {
    margin-right: 12px;
    transition: all 0.3s ease;
    font-size: 18px;
    vertical-align: middle;
  }
}

::v-deep .el-sub-menu {
  margin: 4px 10px;
  border-radius: 8px;
  overflow: hidden;

  .el-sub-menu__title {
    height: 50px;
    line-height: 50px;
    padding: 0 20px !important;
    transition: all 0.3s ease;

    &:hover {
      background: linear-gradient(135deg, rgba(102, 126, 234, 0.15) 0%, rgba(118, 75, 162, 0.15) 100%) !important;
      
      i {
        color: #667eea !important;
        transform: scale(1.1);
      }
    }

    i {
      margin-right: 12px;
      transition: all 0.3s ease;
      font-size: 18px;
    }

    .el-sub-menu__icon-arrow {
      transition: transform 0.3s ease;
    }
  }

  &.is-active {
    > .el-sub-menu__title {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
      color: #fff !important;
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);

      i {
        color: #fff !important;
      }
    }
  }
}

::v-deep .el-menu {
  border: none !important;
  padding: 10px 0;
}
</style>
