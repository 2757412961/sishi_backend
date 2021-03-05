package cn.edu.zju.sishi.passport.service;

import cn.edu.zju.sishi.passport.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TokenService {
  @Autowired
  private TokenDao tokenDao;

  @Autowired
  private TokenRedisService tokenRedisService;

  public String getToken(String userId) {
    String cachedToken = tokenRedisService.getToken(userId);
    String token = "";
    if (StringUtils.isEmpty(cachedToken)) {
      token = tokenDao.getToken(userId);
    } else {
      token = cachedToken;
    }
    return token;
  }

  public String copyTokenToCache(String userId) {
    String tokenInDB = tokenDao.getToken(userId);
    tokenRedisService.setToken(userId, tokenInDB);
    return tokenInDB;
  }

  public Integer insert(String userId, String token, Long expire, Long createTime, Long updateTime) {
    Integer count = tokenDao.insert(userId, token, expire, createTime, updateTime);
    if (count > 0) {
      tokenRedisService.setToken(userId, token);
    }
    return count;
  }

  public Integer updateToken(String userId, String token) {
    Integer count = tokenDao.updateToken(userId, token);
    if (count > 0) {
      tokenRedisService.setToken(userId, token);
    }
    return count;
  }
}
