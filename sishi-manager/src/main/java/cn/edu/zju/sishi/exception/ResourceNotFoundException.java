package cn.edu.zju.sishi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {
  private String resourceName;
  private String fieldName;
  private Object fieldValue;

  public ResourceNotFoundException(int errorCode, String resourceName, String fieldName, Object fieldValue) {
    super(errorCode, String.format("%s not found with %s : '%s'",
            resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }
  //lemon not sure
  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    this(HttpStatus.NOT_FOUND.value(), resourceName, fieldName, fieldValue);
  }

  public ResourceNotFoundException(String resourceName, Map<String, String> conditions) {
    StringBuffer logStringBuffer = new StringBuffer();
    for (Map.Entry<String, String> entry : conditions.entrySet()) {
      logStringBuffer.append(entry.getKey() + " : '" + entry.getValue() + "', ");
    }
    logStringBuffer.delete(logStringBuffer.length() - 2, logStringBuffer.length());
    String message = String.format("%s not found with %s", resourceName, logStringBuffer.toString());
    this.code = HttpStatus.NOT_FOUND.value();
    this.msg = message;
  }

  public ResourceNotFoundException(int errorCode, String message) {
    super(errorCode, message);
  }

  public String getResourceName() {
    return resourceName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }
}
