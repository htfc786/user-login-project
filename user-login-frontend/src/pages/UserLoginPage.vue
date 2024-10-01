<template>
  <div id="userLoginPage" style="max-width:480px; margin:0 auto;">
    <h2 style="text-align: center; margin-bottom: 32px;">用户登录</h2>
    <a-form
      :model="form"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item
        label="账号"
        name="userAccount"
        :rules="[{ required: true, message: '必须输入账号！' }]"
      >
        <a-input v-model:value="form.userAccount"></a-input>
      </a-form-item>
      <a-form-item
        label="密码"
        name="userPassword"
        :rules="[{ required: true, message: '必须输入密码！' }]"
      >
        <a-input-password v-model:value="form.userPassword"></a-input-password>
      </a-form-item>
      <a-form-item>
        <div style="display: flex; width: 100%; align-items: center; justify-content: space-around;">
          <a-button type="primary" html-type="submit" style="width: 120px">登录</a-button>
          <p>没有账号？<router-link to="/auth/register">点击注册</router-link></p>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive, computed } from "vue";
import API from "@/api";
import { message } from 'ant-design-vue';
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const form = reactive<API.UserLoginRequest>({
  userAccount: "",
  userPassword: "",
});

const disabled = computed(() =>  !(form.userAccount && form.userPassword));

/**
 * 提交
 */
const handleSubmit = async () => {
  if (disabled.value) {
    message.error("账号和密码不能为空");
    return;
  }
  const hide = message.loading("登录中...");
  const res = await API.userController.userLogin(form);
  hide();
  if (res.data.code === 200) {
    message.success("登录成功");
    router.push({
      path: decodeURIComponent(route.query.redirect as string) || "/",
      replace: true,
    });
  } else {
    message.error("登录失败，" + res.data.message);
  }
};
</script>
