package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.commons.utils.DateTool;
import cn.edu.zju.sishi.commons.utils.MD5Utils;
import cn.edu.zju.sishi.dao.UserDao;
import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.message.LoginMessage.LoginResponse;
import cn.edu.zju.sishi.message.LoginMessage.RegisterResponse;
import cn.edu.zju.sishi.passport.constant.AuthResponseCode;
import cn.edu.zju.sishi.passport.service.TokenService;
import cn.edu.zju.sishi.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    public static final Long tokenExpire = 14400L;
    public static final String DEFAULT_ROLE_TYPE = "general";

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenService tokenService;

    @Override
    public RegisterResponse register(User user) {
        try {
            RegisterResponse registerResponse = new RegisterResponse();
            boolean exist = existUsername(user.getUserName());
            boolean registerSuccess = false;

            // 邮箱已存在
            if (userDao.getUserByEmail(user.getEmail()) != null) {
                throw new ValidationException(AuthResponseCode.EMAIL_REPEAT_DESC);
            }
            // 用户已存在
            if (exist) {
                logger.info("This user exists! username={}", user.getUserName());
                throw new ValidationException(AuthResponseCode.USER_REPEAT_DESC);
            }

            //set salt
            String salt = generateSalt();
            user.setSalt(salt);
            user.setRoleType(DEFAULT_ROLE_TYPE);
            String encodedPassword = encrypt(user.getPassword(), salt).toLowerCase();
            user.setPassword(encodedPassword);
            boolean setSuccess = setUsername(user.getUserName(), user);
            int insertedCount = 0;
            if (setSuccess) {
                user.setCreateTime(Instant.now().toEpochMilli());
                user.setUpdateTime(Instant.now().toEpochMilli());
                user.setUserId(UUID.randomUUID().toString());
                user.setAvatar("https://img.88icon.com/download/jpg/20200815/cacc4178c4846c91dc1bfa1540152f93_512_512.jpg!88con");
                insertedCount = userDao.addUser(user);
                String userId = user.getUserId();
                if (insertedCount == 1) {
                    // 产生token并存入数据库
                    String token = MD5Utils.md5(DateTool.getTime() + user.getPassword()).toLowerCase();
                    Integer count = tokenService.insert(userId, token, tokenExpire, user.getCreateTime(), user.getUpdateTime());
                    if (count > 0) {
                        registerResponse.setUserId(userId);
                        registerResponse.setToken(token);
                        registerResponse.setUserName(user.getUserName());
                        registerSuccess = true;
                    }
                }
            }
            if (!registerSuccess) {
                throw new ValidationException(AuthResponseCode.USER_REGISTER_FAILED_DESC);
            }


            return registerResponse;
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    public LoginResponse login(String userName, String password) {
        User user = userDao.getUserByName(userName);
        if (null == user) {
            throw new ValidationException(String.format("用户名：%s不存在", userName));
        }
        String salt = user.getSalt();
        String encodedPassword = encrypt(password, salt).toLowerCase();
        String passwordInDatabase = "";
        passwordInDatabase = user.getPassword();
        String userId = user.getUserId();
        String token = "";
        if (encodedPassword.equals(passwordInDatabase)) {
            token = tokenService.copyTokenToCache(userId);
            LoginResponse loginResult = new LoginResponse();
            loginResult.setUserId(userId);
            loginResult.setUserName(userName);
            loginResult.setToken(token);
            loginResult.setAvatar(user.getAvatar());
            loginResult.setEmail(user.getEmail());
            loginResult.setHonor(user.getHonor());
            loginResult.setMobile(user.getMobile());
            loginResult.setPartyBranch(user.getPartyBranch());
            loginResult.setScore(user.getScore());
            loginResult.setRoleType(user.getRoleType());
            loginResult.setUnit(user.getUnit());
//            loginResult.setGrade(user.getGrade());
            loginResult.setStudentName(user.getStudentName());
            loginResult.setStudentNumber(user.getStudentNumber());
            return loginResult;
        } else {
            throw new ValidationException("登陆失败，密码错误!");
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

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        int salt = Math.abs(random.nextInt());
        return String.valueOf(salt);
    }

    public String encrypt(String password, String salt) {
        String encodedPassword = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        return encodedPassword;
    }
}
