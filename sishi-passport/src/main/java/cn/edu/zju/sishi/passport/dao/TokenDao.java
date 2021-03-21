package cn.edu.zju.sishi.passport.dao;

import org.apache.ibatis.annotations.Param;

public interface TokenDao {
  String getToken(String userId);

  Integer insert(@Param("userId") String userId, @Param("token") String token
          , @Param("expire") Long expire, @Param("createTime") Long createTime, @Param("updateTime") Long updateTime);

  Integer updateToken(@Param("userId") String userId, @Param("token") String token);

  String getUserIdByToken(@Param("token") String token);
  // select user_id from tb_token where token = #{token}
}