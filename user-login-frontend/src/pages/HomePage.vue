<template>
  <div class="abouts">
    <h1>用户中心</h1>
    <router-link to="/auth/logout">退出登录</router-link>
    <div>{{ info }}</div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import API from "@/api";

const info = ref('')

onMounted(() => {
  API.userController.test2().then(res => {
    info.value = res.data.data
  })
})

</script>

<style scoped></style>