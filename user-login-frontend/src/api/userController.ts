// @ts-ignore
/* eslint-disable */
import request from "@/request.ts";

/** userLogin POST /auth/login */
export async function userLogin(
  body: API.UserLoginRequest,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseLoginUserVO_>("/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
    ...(options || {}),
  });
}

/** userRegister POST /auth/register */
export async function userRegister(
  body: API.UserRegisterRequest,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseLong_>("/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
    ...(options || {}),
  });
}

/** wxLoginInfo GET /auth/wx/info */
export async function wxLoginInfo(options?: { [key: string]: any }) {
  return request<API.BaseResponseWxLoginInfoVO_>("/auth/wx/info", {
    method: "GET",
    ...(options || {}),
  });
}

/** wxLogin POST /auth/wx/login */
export async function wxLogin(
  body: API.WxLoginRequest,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseLoginUserVO_>("/auth/wx/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
    ...(options || {}),
  });
}

/** test1 GET /test1 */
export async function test1(options?: { [key: string]: any }) {
  return request<API.BaseResponseString_>("/test1", {
    method: "GET",
    ...(options || {}),
  });
}

/** test2 GET /test2 */
export async function test2(options?: { [key: string]: any }) {
  return request<API.BaseResponseString_>("/test2", {
    method: "GET",
    ...(options || {}),
  });
}
