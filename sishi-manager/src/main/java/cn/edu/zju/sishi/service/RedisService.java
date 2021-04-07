package cn.edu.zju.sishi.service;

/**
 * @author: zjh
 * @date : 2021/4/2 16:54
 * @Email : 2757412961@qq.com
 * @update:
 */

public interface RedisService {

    String get(String key);

    void set(String key, String value);

    void set(String key, String value, int expire);

}
