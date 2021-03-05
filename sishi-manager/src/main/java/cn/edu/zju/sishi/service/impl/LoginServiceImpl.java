package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.commons.utils.DateTool;
import cn.edu.zju.sishi.commons.utils.MD5Utils;
import cn.edu.zju.sishi.dao.UserDao;
import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.message.LoginMessage.LoginResponse;
import cn.edu.zju.sishi.passport.constant.AuthResponseCode;
import cn.edu.zju.sishi.passport.service.TokenService;
import cn.edu.zju.sishi.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

  private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
  public static final Long tokenExpire = 1000000000L;

  @Autowired
  private UserDao userDao;

  @Autowired
  private TokenService tokenService;

  @Override
  public LoginResponse register(User user) {
    LoginResponse loginResponse = new LoginResponse();
    boolean exist = existUsername(user.getUserName());
    boolean registerSuccess = false;
    if (exist) {
      logger.info("This user exists! username={}", user.getUserName());
      throw new ValidationException(AuthResponseCode.USER_REPEAT_DESC);
    } else {
      String encodedPassword = MD5Utils.md5(user.getPassword()).toLowerCase();
      user.setPassword(encodedPassword);
      boolean setSuccess = setUsername(user.getUserName(), user);
      int insertedCount = 0;
      if (setSuccess) {
        user.setCreateTime(Instant.now().toEpochMilli());
        user.setUpdateTime(Instant.now().toEpochMilli());
        user.setUserId(UUID.randomUUID().toString());
        insertedCount = userDao.addUser(user);
        String userId = user.getUserId();
        if (insertedCount == 1) {
          // 产生token并存入数据库
          String token = MD5Utils.md5(DateTool.getTime() + user.getPassword()).toLowerCase();
          Integer count = tokenService.insert(userId, token, tokenExpire, user.getCreateTime(), user.getUpdateTime());
          if (count > 0) {
            loginResponse.setUserId(userId);
            loginResponse.setToken(token);
            loginResponse.setUserName(user.getUserName());
            registerSuccess = true;
          }
        }
      }
      if (!registerSuccess) {
        throw new ValidationException(AuthResponseCode.USER_REGISTER_FAILED_DESC);
      }
    }

    return loginResponse;
  }

  @Override
  public LoginResponse login(String userName, String password) {
    String encodedPassword = MD5Utils.md5(password).toLowerCase();
    String passwordInDatabase = "";
    User user = userDao.getUserByName(userName);
    if (null == user) {
      throw new ValidationException(String.format("用户名：%s不存在", userName));
    }
    passwordInDatabase = user.getPassword();
    String userId = user.getUserId();
    String token = "";
    if (encodedPassword.equals(passwordInDatabase)) {
      token = tokenService.copyTokenToCache(userId);
      LoginResponse loginResult = new LoginResponse();
      loginResult.setUserId(userId);
      loginResult.setToken(token);
      loginResult.setUserName(userName);
      return loginResult;
    } else {
      throw new ValidationException("Login Error: Password error!");
    }
  }

  private boolean existUsername(String username) {
    Integer count = userDao.userNameExist(username);
    logger.info("existUsername count={}", count);
    boolean exist = count > 0;
    return exist;
  }

  private boolean setUsername(String username, User user) {
    user.setUserName(username);
    return true;
  }
}
