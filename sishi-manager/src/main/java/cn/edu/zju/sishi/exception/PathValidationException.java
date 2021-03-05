package cn.edu.zju.sishi.exception;

import org.springframework.http.HttpStatus;

public class PathValidationException extends BaseException {
  private String path;

  public PathValidationException(String path) {
    this(HttpStatus.BAD_REQUEST.value(), path);
  }

  public PathValidationException(int errorCode, String path) {
    super(errorCode, String.format("path format error: %s", path));
    this.path = path;
  }

  public PathValidationException(String messageFormat, String... args) {
    super(HttpStatus.BAD_REQUEST.value(), String.format(messageFormat, args));
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
