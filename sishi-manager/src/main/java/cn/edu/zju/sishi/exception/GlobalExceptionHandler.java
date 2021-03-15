package cn.edu.zju.sishi.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.ValidationException;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {
  @Bean
  MethodValidationPostProcessor methodValidationPostProcessor() {
    return new MethodValidationPostProcessor();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionMessage handle(SecurityException exception) {
    ExceptionMessage message = new ExceptionMessage();
    message.setCode(HttpStatus.UNAUTHORIZED.value());
    message.setMessage(exception.getMessage());
    return message;
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionMessage handle(ValidationException exception) {
    ExceptionMessage message = new ExceptionMessage();
    message.setCode(HttpStatus.BAD_REQUEST.value());
    message.setMessage(exception.getMessage());
    return message;
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionMessage handle(PathValidationException exception) {
    ExceptionMessage message = new ExceptionMessage();
    message.setCode(HttpStatus.BAD_REQUEST.value());
    message.setMessage(exception.getMessage());
    return message;
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionMessage handle(ResourceNotFoundException exception) {
    ExceptionMessage message = new ExceptionMessage();
    message.setCode(HttpStatus.NOT_FOUND.value());
    message.setMessage(exception.getMessage());
    return message;
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionMessage handle(RuntimeException exception) {
    ExceptionMessage message = new ExceptionMessage();
    message.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    message.setMessage(exception.getMessage());
    return message;
  }
}
