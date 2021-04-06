package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.commons.UserContext;
import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.message.UserMessage.*;

import java.util.List;

/**
 * @author lemon
 * @date 2021/3/17
 */
public interface UserService {

//  void addUser(User user);

  void updateUserInfo(String id, UpdateUserRequest updateUserRequest);

  void updatePassword(String id, UpdatePasswordRequest updatePasswordRequest);

  String resetPasswordByEmail(String email);


  List<ScoreResponse> getTopTenByScore();

  List<UserListResponse> getUserList(int offset, int length, String id);

  UserInfoResponse getUserInfo(String id);

  ScoreResponse getScore(String id);


  User getUserByEmail(String email);





}
