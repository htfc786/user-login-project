import { createApp } from 'vue'
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

// Ant Design Vue
import Antd from "ant-design-vue";
import 'ant-design-vue/dist/reset.css';

import router from './router'
import App from './App.vue'
import './style.css'

// 状态管理
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

createApp(App).use(router).use(pinia).use(Antd).mount('#app')
