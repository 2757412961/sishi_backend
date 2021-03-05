package cn.edu.zju.sishi.passport.constant;

public class AuthResponseCode {
  // 成功
  public static final String SUCCESS = "200";

  public static final String SUCCESS_DESC = "处理成功";

  // token值为空
  public static final String TOKEN_IS_NULL = "400";

  public static final String TOKEN_IS_NULL_DESC = "token值为空";

  // token值过期
  public static final String TOKEN_EXPIRED = "400";

  public static final String TOKEN_EXPIRED_DESC = "token值过期";

  // token无效
  public static final String TOKEN_INVALID = "400";

  public static final String TOKEN_INVALID_DESC = "token无效";

  // userId为null
  public static final String USER_ID_IS_NULL = "400";

  public static final String USER_ID_IS_NULL_DESC = "userId为null";

  // 状态无效
  public static final String STATUS_INVALID = "400";

  public static final String STATUS_INVALID_DESC = "状态无效";
  
  // 手机号码不存在
  public static final String USER_MOBILE_NOT_FOUND = "400";

  public static final String USER_MOBILE_NOT_FOUND_DESC = "手机号码不存在";

  // 手机号码已经注册
  public static final String USER_MOBILE_REPEAT = "400";

  public static final String USER_MOBILE_REPEAT_DESC = "手机号码已注册";

  public static final String USER_REPEAT = "400";
  public static final String USER_REPEAT_DESC = "此用户名已注册";

  public static final String USER_REGISTER_FAILED = "400";
  public static final String USER_REGISTER_FAILED_DESC = "用户注册失败";
  
  // 密码错误5次被冻结
  public static final String USER_FROZEN = "400";

  public static final String USER_FROZEN_DESC = "您的账号因密码错误次数太多,已被冻结,请30分钟后尝试登陆！";

  // 验证码不正确
  public static final String USER_CAPTCHA_ERROR = "400";

  public static final String USER_CAPTCHA_ERROR_DESC = "验证码不正确";

  // 验证码已过期
  public static final String USER_CAPTCHA_EXPARIED = "400";

  public static final String USER_CAPTCHA_EXPARIED_DESC = "验证码已过期";
  
  // 用户中心登陆错误
  public static final String USERCENTER_ERROR = "400";

  public static final String USERCENTER_ERROR_DESC = "用户中心登陆异常";
}
