package cn.edu.zju.sishi.passport.service;

import cn.edu.zju.sishi.passport.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenDBService {
  @Autowired
  private TokenDao tokenDao;

  public String getToken(String userId) {
    String token = tokenDao.getToken(userId);
    return token;
  }

  public Integer insert(String userId, String token, Long expire, Long createTime, Long updateTime) {
    Integer count = tokenDao.insert(userId, token, expire, createTime, updateTime);
    return count;
  }

  public Integer updateToken(String userId, String token) {
    Integer count = tokenDao.updateToken(userId, token);
    return count;
  }
}
