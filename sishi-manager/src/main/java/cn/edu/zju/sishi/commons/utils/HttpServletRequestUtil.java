package cn.edu.zju.sishi.commons.utils;

import cn.edu.zju.sishi.commons.UserContext;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Map;

/**
 * @author lemon
 * @date 2021/3/20
 */
public class HttpServletRequestUtil {


  public static UserContext getAndCheckIdFromRequest(HttpServletRequest request) {
    UserContext userContext = new UserContext();
    String id = request.getHeader("userId");
    if (id != null && id.length() != 36) {
      throw new ValidationException("userId length should be 36");
    }
    // when not login, use emptyString as userId. so that will support search public data
    if (id == null) {
      id = "";
    }
    userContext.setUserId(id);
    return userContext;
  }
}
