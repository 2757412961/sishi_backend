package cn.edu.zju.sishi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisCacheConfig {
  private Logger log = LoggerFactory.getLogger(RedisCacheConfig.class);

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  @Value("${spring.redis.database}")
  private int database;

  @Value("${spring.redis.password}")
  private String password;

  @Value("${spring.redis.timeout}")
  private int timeout;

  @Value("${spring.redis.jedis.pool.max-idle}")
  private int maxIdle;

  @Value("${spring.redis.jedis.pool.min-idle}")
  private int minIdle;

  @Value("${spring.redis.jedis.pool.max-wait}")
  private long maxWaitMillis;

  @Value("${spring.redis.jedis.pool.max-active}")
  private int maxActive;

  @Bean(name = "poolConfig")
  public JedisPoolConfig initJedisPoolConfig() {
    log.info("JedisPoolConfig注入开始:");
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(maxActive);
    poolConfig.setMaxIdle(maxIdle);
    poolConfig.setMaxWaitMillis(maxWaitMillis);
    poolConfig.setMinIdle(minIdle);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setBlockWhenExhausted(true);
    return poolConfig;
  }

  @Bean
  public JedisPool initJedisPool(@Qualifier("poolConfig") JedisPoolConfig poolConfig) {
    log.info("JedisPool注入开始:");
    //  Redis无密码时候的处理
    if ("".equals(password)){
      password = null;
    }
    return new JedisPool(poolConfig, host, port, timeout, password, database);
  }
}
