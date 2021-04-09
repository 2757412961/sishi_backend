package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.UserDao;
import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.message.UserMessage.*;
import cn.edu.zju.sishi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lemon
 * @date 2021/3/17
 */
@Service
public class UserServiceImpl implements UserService {


  @Autowired
  UserDao userDao;


//  @Override
//  public void addUser(User user) {
//
//    user.setUserId(UUID.randomUUID().toString());
//
//    long updateTime = Instant.now().toEpochMilli();
//    long createTime = Instant.now().toEpochMilli();
//    user.setCreateTime(createTime);
//    user.setUpdateTime(updateTime);
//
//    String salt = generateSalt();
//    user.setSalt(salt);
//
//    String password = user.getPassword();
//    String newPassword = encrypt(password, salt);
//    user.setPassword(newPassword);
//
////   todo: how to set roleType
//    userDao.addUser(user);
//
//  }

  @Override
  public void updateUserInfo(String id, UpdateUserRequest updateUserRequest) {


    //check whether there is a user corresponding to the id
    UserInfoResponse oldUserInfo = userDao.getUserInfo(id);
    if (oldUserInfo == null) {
      throw new ResourceNotFoundException("user", "id", id);
    }
    User userEntity = new User();
    BeanUtils.copyProperties(oldUserInfo, userEntity, getEmptyStringPropertyNames(updateUserRequest));
    BeanUtils.copyProperties(updateUserRequest, userEntity, getNullOrEmptyPropertyNames(updateUserRequest));
    userDao.updateUserInfo(id, userEntity);
    userEntity.setUpdateTime(Instant.now().toEpochMilli());


  }


  @Override
  public void updatePassword(String id, UpdatePasswordRequest updatePasswordRequest) {

    UserPasswordSaltResponse userPasswordSaltResponse = userDao.getPasswordSalt(id);
//    check if the id is correct
    if (userPasswordSaltResponse == null) {
      throw new ResourceNotFoundException("user", "id", id);
    }
    String salt = userPasswordSaltResponse.getSalt();
    String oldPassword = userPasswordSaltResponse.getPassword();
    String inputOldPassword = updatePasswordRequest.getOldPassword();
    String newPassword = updatePasswordRequest.getNewPassword();
    //check if the origin password is correct
    if (!oldPassword.equals(encrypt(inputOldPassword, salt))) {
      throw new ValidationException("the old password is incorrect");
    }

    String entryedNewPassword = encrypt(newPassword, salt);
    userDao.updatePassword(id, entryedNewPassword, Instant.now().toEpochMilli());


  }

  @Override
  public String updatePasswordByEmail(String email, String newPassword) {
    User user = userDao.getUserByEmail(email);
    if (user == null){
      throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), "邮箱和对应用户不存在，请核对邮箱");
    }

    String salt = user.getSalt();
    String encryptNewPassword = encrypt(newPassword, salt);

    userDao.updatePassword(user.getUserId(), encryptNewPassword, Instant.now().toEpochMilli());
    return newPassword;
  }


  @Override
  public List<ScoreResponse> getTopTenByScore() {
    return userDao.getTopTenByScore();
  }

  @Override
  public List<UserListResponse> getUserList(int start, int length, String id) {

    String roleType = userDao.getUserRoleType(id);
    if (!roleType.equalsIgnoreCase("admin")) {
      throw new ValidationException("Permission denied, don't have privilege to do this action.");
    }

    return userDao.getUserList(start, length);
  }

  @Override
  public UserInfoResponse getUserInfo(String id) {
    return userDao.getUserInfo(id);
  }

  @Override
  public ScoreResponse getScore(String id) {
    return userDao.getScore(id);
  }

  @Override
  public User getUserByEmail(String email) {
    User user = userDao.getUserByEmail(email);

    if (user==null){
      throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), "邮箱和对应用户不存在，请核对邮箱");
    }

    return user;
  }


  public String encrypt(String password, String salt) {
    String newPassword = DigestUtils.md5DigestAsHex((password + salt).getBytes());
    return newPassword;
  }

  public static String[] getNullOrEmptyPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> emptyNames = new HashSet<>();
    for (java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null || srcValue.equals("")) emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  public static String[] getEmptyStringPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> emptyNames = new HashSet<>();
    for (java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue != null && srcValue.equals(""))
        emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

}