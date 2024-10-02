<template></template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { TOKEN_KEY } from "@/request";

onMounted(() => {
  if (localStorage.getItem(TOKEN_KEY)) {
    localStorage.removeItem(TOKEN_KEY);
    window.location.reload();
  } else {
    // 跳转到首页
    const route = useRouter();
    route.push({ path: '/' });
  }
});
</script>

<style scoped></style>