package cn.edu.zju.sishi.passport.service;

import cn.edu.zju.sishi.passport.model.Passport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginIntercepterService {
  @Autowired
  private TokenRedisService tokenRedisService;

  public Passport getPassport(String userId) {
    String token = tokenRedisService.getToken(userId);
    Passport passport = new Passport();
    passport.setUserId(userId);
    passport.setToken(token);
    return passport;
  }
}
