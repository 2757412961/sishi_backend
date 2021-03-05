package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserDao {
  Integer addUser(User user);
  User getUserByName(@Param("userName") String userName);
  User getUserByEmail(@Param("email") String email);
  Integer userNameExist(@Param("userName") String userName);
}
