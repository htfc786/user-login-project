<template>
  <div id="userRegisterPage" style="max-width:480px; margin:0 auto;">
    <h2 style="text-align: center; margin-bottom: 16px">用户注册</h2>
    <a-form
      :model="form"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item
        label="账号"
        name="userAccount"
        :rules="[{ required: true, message: '必须输入账号！' }, 
                 { min: 4, message: '账号长度不能小于4位！' }]"
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
      <a-form-item
        label="确认密码"
        name="checkPassword"
        :rules="[{ required: true, message: '必须输入确认密码！' }]"
      >
        <a-input-password v-model:value="form.checkPassword"></a-input-password>
      </a-form-item>
      <a-form-item>
        <div style="display: flex; width: 100%; align-items: center; justify-content: space-around;">
          <a-button type="primary" html-type="submit" style="width: 120px">注册</a-button>
          <p>已有账号？<router-link to="/auth/login">前往登录</router-link></p>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import API from "@/api";
import { message } from 'ant-design-vue';
import { useRouter } from "vue-router";

const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
} as API.UserRegisterRequest);

/**
 * 提交
 */
const handleSubmit = async () => {
  const { userPassword, checkPassword } = form;
  if (userPassword !== checkPassword) {
    message.error("两次输入的密码不一致！");
    return;
  }
  const hide = message.loading("注册中...");
  const res = await API.userController.userRegister(form);
  hide();
  if (res.data.code === 200) {
    message.success("注册成功");
    router.push({
      path: "/auth/login",
      replace: true,
    });
  } else {
    message.error("注册失败，" + res.data.message);
  }
};
</script>
