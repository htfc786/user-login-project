import routes from "./configs/routes";
import * as VueRouter from "vue-router";

// 路由
const router = VueRouter.createRouter({
  history: VueRouter.createWebHashHistory(), // 哈希模式
  routes,
});

export default router;

export const getRoute = () => {
  return router.currentRoute.value;
}
