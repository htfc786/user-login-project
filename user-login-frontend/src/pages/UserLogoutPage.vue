<template></template>

<script setup lang="ts">
import API from "@/api";
import { onMounted } from "vue";
import { useRouter } from "vue-router";
import { message } from 'ant-design-vue';

const router = useRouter();

const logout = async () => {
  const hide = message.loading("正在退出登录...", 0);
  await API.userController.userLogout()
  hide();
  router.push({
    path: "/",
    replace: true,
  });
}

onMounted(logout);

</script>

<style scoped></style>