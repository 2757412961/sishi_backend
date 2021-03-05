package cn.edu.zju.sishi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends BaseException {
  public ValidationException(String msg) {
    super(HttpStatus.BAD_REQUEST.value(), msg);
  }
}
