<script>
import { h, resolveComponent } from 'vue'
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
  },
  render(props) {
    const { icon } = props
    const elIcon = resolveComponent('ElIcon');
    const component = resolveComponent(icon)
    return h(elIcon, { class: 'menu-item-icon' }, h(component, {}, []))
  }
}
</script>

<style scoped>
.menu-item-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  margin-right: 12px;
  transition: all 0.3s ease;
  vertical-align: middle;
}

.el-menu-item [class^="fa"],
.el-menu-item .menu-item-icon {
  margin-right: 12px;
  width: 24px;
  text-align: center;
  font-size: 18px;
  vertical-align: middle;
}

.sub-el-icon {
  color: currentColor;
  width: 1em;
  height: 1em;
}
</style>
