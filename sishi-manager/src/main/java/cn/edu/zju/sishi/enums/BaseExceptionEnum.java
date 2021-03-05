package cn.edu.zju.sishi.enums;

public enum BaseExceptionEnum {
  
  BASE_EXCEPTION_ENUM(500, "System error");

  int code;
  String msg;

  BaseExceptionEnum(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static String getDescStrByCode(int code) {
    BaseExceptionEnum[] payTypes = values();
    for (BaseExceptionEnum payType : payTypes) {
      if (payType.getCode() == code) {
        return payType.getMsg();
      }
    }
    return "";
  }

  public static BaseExceptionEnum getByCode(int code) {
    BaseExceptionEnum[] payTypes = values();
    for (BaseExceptionEnum payType : payTypes) {
      if (payType.getCode() == code) {
        return payType;
      }
    }
    return null;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
