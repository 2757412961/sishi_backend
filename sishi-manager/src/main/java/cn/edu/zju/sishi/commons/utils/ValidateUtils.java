package cn.edu.zju.sishi.commons.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lemon
 * @date 2021/3/21
 */
public class ValidateUtils {
  public enum ValidateEnum {
    MOBILEPHONE("^(((\\+\\d{2}-)?([1][3,4,5,7,8][0-9]\\d{8})))$", "Invalid mobile phone number"),
    PHONE("^(((\\+\\d{2}-)?0\\d{2,3}-\\d{7,8})|((\\+\\d{2}-)?(\\d{2,3}-)?([1][3,4,5,7,8][0-9]\\d{8})))$",
      "Invalid phone number");
    private String regular;
    private String error;

    ValidateEnum(String regular, String error) {
      this.regular = regular;
      this.error = error;
    }

    public String getRegular() {
      return regular;
    }

    public String getError() {
      return error;
    }

    //get the error by specific regular
    public static String getErrorByRegular(String regular) {
      for (ValidateEnum validateEnum : ValidateEnum.values()) {
        if (validateEnum.regular.equals(regular)) {
          return validateEnum.error;
        }
      }
      //return default error if regular is null
      return "Invalid pattern";
    }
  }

  public static boolean validate(String value, String regular) {
    Pattern pattern = Pattern.compile(regular);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }

  public static boolean isRange(String value, List range) {
    if (range.contains(value)) {
      return true;
    }
    return false;
  }
}