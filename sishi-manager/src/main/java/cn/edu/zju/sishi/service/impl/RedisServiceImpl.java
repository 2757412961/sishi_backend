package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author: zjh
 * @date : 2021/4/2 16:54
 * @Email : 2757412961@qq.com
 * @update:
 */

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * @Description: 从Redeis中获取值
     * @Param: [key]
     * @return: java.lang.String
     * @Author: zjh
     * @Date: 2021/4/2
     */
    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();

        return value;
    }

    @Override
    public void set(String key, String value) {
        setBase(key, value, 100000000);
    }

    @Override
    public void set(String key, String value, int expire) {
        setBase(key, value, expire);
    }

    /**
     * @Description: 从Redis中放入值
     * @Param: [key, value, expire]
     * @return: void
     * @Author: zjh
     * @Date: 2021/4/2
     */
    public void setBase(String key, String value, int expire) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.expire(key, expire);
        jedis.close();
    }


}
