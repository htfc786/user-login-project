import { RouteRecordRaw } from "vue-router";
import IndexPage from "@/pages/IndexPage.vue";
/**
 * 路由列表
 */
export default [
  {
    path: "/",
    component: IndexPage,
    props: true,
  },
  {
    path: "/about",
    component: () => import("@/pages/AboutPage.vue"),
    props: true,
  },
  {
    path: "/home",
    component: () => import("@/pages/HomePage.vue"),
    props: true,
  },
  {
    path: "/auth/login",
    component: () => import("@/pages/UserLoginPage.vue"),
    props: true,
  },
  {
    path: "/auth/register",
    component: () => import("@/pages/UserRegisterPage.vue"),
    props: true,
  },
  {
    path: "/auth/logout",
    component: () => import("@/pages/UserLogoutPage.vue"),
    props: true,
  },
  {
    path: "/auth/wx/login",
    component: () => import("@/pages/WxLoginPage.vue"),
    props: true,
  },
  // 404页面
  {
    path: "/404",
    component: () => import("@/pages/404Page.vue"),
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
  },
] as RouteRecordRaw[];
