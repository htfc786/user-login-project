declare namespace API {
  type BaseResponseLoginUserVO_ = {
    code?: number;
    data?: LoginUserVO;
    message?: string;
  };

  type BaseResponseLong_ = {
    code?: number;
    data?: number;
    message?: string;
  };

  type BaseResponseString_ = {
    code?: number;
    data?: string;
    message?: string;
  };

  type BaseResponseWxLoginInfoVO_ = {
    code?: number;
    data?: WxLoginInfoVO;
    message?: string;
  };

  type LoginUserVO = {
    createTime?: string;
    id?: number;
    token?: string;
    userAccount?: string;
    userName?: string;
  };

  type UserLoginRequest = {
    userAccount?: string;
    userPassword?: string;
  };

  type UserRegisterRequest = {
    checkPassword?: string;
    userAccount?: string;
    userPassword?: string;
  };

  type WxLoginInfoVO = {
    wxAppId?: string;
  };

  type WxLoginRequest = {
    code?: string;
    state?: string;
  };
}
