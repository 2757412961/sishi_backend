package cn.edu.zju.sishi.commons.utils;

/**
 * @author lemon
 * @date 2021/3/17
 */
public class PrivilegeUtils {

  public static String ADMIN_USER_ID = "ADMIN";
  public static String PERMISSION_DENIED_MESSAGE = "Permission denied, don't have privilege to do this action.";


  public static boolean isAdmin(String requestUser) {
    return requestUser.equalsIgnoreCase(ADMIN_USER_ID);
  }

  public static void checkAdminPrivilege(String requestUser) {
    if (!isAdmin(requestUser)) {
      throw new SecurityException("Permission denied, don't have privilege to do this action.");
    }
  }
}
