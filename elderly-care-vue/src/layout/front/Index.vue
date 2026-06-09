<template>
  <div class="app-wrapper">
    <Header></Header>
    <div class="main">
      <router-view :key='$route.fullPath' />
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from '@/layout/front/Header'
import Footer from '@/layout/front/Footer'

export default {
  name: "Index",
  components: {
    Header, Footer
  },
  data() {
    return {
      user: this.$store.state.user,
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.main {
  width: 90%;
  margin: 0 auto;
  padding-top: 75px;
}

.item {
  position: absolute;
  right: 35px;
  top: 260px;
}
</style>