package cn.edu.zju.sishi.exception;

import cn.edu.zju.sishi.enums.BaseExceptionEnum;

public class BaseException extends RuntimeException {
  public String msg;
  public int code;

  public BaseException() {
  }

  protected BaseException(int code, String arg0, Throwable arg1) {
    super(arg0, arg1);
    this.code = code;
    this.msg = arg0;
  }

  public BaseException(int code) {
    this.code = code;
    this.msg = BaseExceptionEnum.getDescStrByCode(code);
  }

  public BaseException(int code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }

  @Override
  public String getMessage() {
    return this.msg;
  }

  public int getCode() {
    return this.code;
  }
}
