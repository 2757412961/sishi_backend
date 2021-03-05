package cn.edu.zju.sishi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends BaseException {
  public UnauthorizedException(String msg) {
    super(HttpStatus.UNAUTHORIZED.value(), msg);
  }
}
