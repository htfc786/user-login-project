<template>
  <div class="main">
    <a-row class="header" type="flex" align="middle">
      <a-col flex="200px" style="margin: 0 auto">
        <RouterLink to="/">
          <a-row align="middle">
            <img src="/vite.svg" alt="vite.svg" />
            <span class="title">user-login-project</span>
          </a-row>
        </RouterLink>
      </a-col>
      <a-col flex="auto">
        <a-menu
          mode="horizontal"
          :selected-keys="selectedKeys"
          @click="doClickMenu"
        >
          <a-menu-item key="/about">About</a-menu-item>
          <a-menu-item key="/home">用户中心</a-menu-item>
          <a-menu-item key="/auth/login">登录</a-menu-item>
          <a-menu-item>
            <a href="https://github.com/htfc786/user-login-project" target="_blank">
              <github-outlined /> 代码开源
            </a>
          </a-menu-item>
        </a-menu>
      </a-col>
    </a-row>
    <div class="content">
      <router-view />
    </div>
    <div class="footer">
      <span><a href="https://github.com/htfc786/user-login-project" target="_blank">user-login-project</a> by htfc786</span>
    </div>
  </div>
  <a-back-top :style="{ right: '24px' }" />
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { GithubOutlined } from '@ant-design/icons-vue';

const route = useRoute();
const router = useRouter();

// 显示蓝条url
const selectedKeys = computed(() => {
  return ["/" + route.path.split("/")[1]];
});

/**
 * 点击菜单跳转
 * @param key
 */
const doClickMenu = ({ key }: any) => {
  if (key) {
    router.push({
      path: key,
    });
  }
};

// 使中间容器铺满
const changeContentMinHeight = () => {
  const headerHeight = (<HTMLElement>document.querySelector(".header"))?.offsetHeight;
  const footerHeight = (<HTMLElement>document.querySelector(".footer"))?.offsetHeight;
  const content = <HTMLElement>document.querySelector(".content");
  const contentMinHeight = window.innerHeight - headerHeight - footerHeight;
  content.style.minHeight = contentMinHeight + "px";
};

onMounted(() => {
  window.addEventListener("resize", changeContentMinHeight);
  changeContentMinHeight();
});

onUnmounted(() => {
  window.removeEventListener("resize", changeContentMinHeight);
})

</script>

<style scoped>
.header {
  background-color: #ffffff;
  border-bottom: 1px solid #f0f0f0;
  padding: 0 24px;
}
.header .title {
  margin-left: 8px;
  font-size: 20px;
  color: #000;
}
.header .ant-menu {
  line-height: 64px;
  border-bottom: none !important;
}
.content {
  padding: 24px;
}
.footer {
  padding: 12px;
  text-align: center;
  background: #efefef;
  font-size: 14px;
}
.footer span {
  display: block;
  margin: 0;
}

</style>
