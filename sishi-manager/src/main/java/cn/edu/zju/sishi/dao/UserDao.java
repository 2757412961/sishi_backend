package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.message.UserMessage.ScoreResponse;
import cn.edu.zju.sishi.message.UserMessage.UserInfoResponse;
import cn.edu.zju.sishi.message.UserMessage.UserListResponse;
import cn.edu.zju.sishi.message.UserMessage.UserPasswordSaltResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserDao {
  Integer addUser(User user);

  User getUserByName(@Param("userName") String userName);

  User getUserByEmail(@Param("email") String email);

  Integer userNameExist(@Param("userName") String userName);

  void updateUserInfo(@Param("id") String id,
                      @Param("user") User userEntity);

  void updatePassword(@Param("id") String id,
                      @Param("password") String password,
                      @Param("updateTime") Long updateTime);

  List<UserListResponse> getUserList(@Param("offset") int offset,
                                     @Param("length") int length);

  UserInfoResponse getUserInfo(@Param("id") String id);


  List<ScoreResponse> getTopTenByScore();

  ScoreResponse getScore(@Param("id") String id);

  UserPasswordSaltResponse getPasswordSalt(@Param("id") String id);

  // 根据
  String getUserRoleType(@Param("id") String id);

}
