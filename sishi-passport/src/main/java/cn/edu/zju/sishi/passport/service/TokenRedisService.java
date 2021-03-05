package cn.edu.zju.sishi.passport.service;

import cn.edu.zju.sishi.passport.redis.CommonCacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class TokenRedisService {
  private static final Logger logger = LoggerFactory.getLogger(TokenRedisService.class);

  @Autowired
  private JedisPool jedisPool;

  public String getToken(String userId) {
    Jedis jedis = jedisPool.getResource();
    String key = CommonCacheKey.PASSPORT_PREFIX + userId;
    String tokenInRedis = jedis.get(key);
    jedis.close();
    logger.info("TokenRedisService.getToken:key={},value={}", new Object[]{key, tokenInRedis});
    return tokenInRedis;
  }

  public String setToken(String userId, String token) {
    String key = CommonCacheKey.PASSPORT_PREFIX + userId;
    Jedis jedis = jedisPool.getResource();
    String result = jedis.set(key, token);
    jedis.close();
    logger.info("TokenRedisService.setToken:key={},value={}", new Object[]{key, token});
    return result;
  }
}
