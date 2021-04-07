package cn.edu.zju.sishi.commons.utils;

/**
 * @author lemon
 * @date 2021/3/17
 */
public class RuntimeEnvironment {
  private static boolean testing = false;

  public static boolean isTesting() {
    return testing;
  }

  public static void setTesting(boolean test) {
    testing = test;
  }
}
