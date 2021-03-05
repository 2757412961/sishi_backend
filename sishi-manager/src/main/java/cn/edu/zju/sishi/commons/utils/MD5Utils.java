package cn.edu.zju.sishi.commons.utils;

import org.springframework.util.DigestUtils;

/**
 * @Author Zittur
 * @Description
 * @Date 2021/3/5
 */
public class MD5Utils {
  public static String md5(String src) {
    return DigestUtils.md5DigestAsHex(src.getBytes());
  }
}
