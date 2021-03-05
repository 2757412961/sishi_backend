package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.message.LoginMessage.LoginResponse;

public interface LoginService {
  
  LoginResponse register(User user);

  LoginResponse login(String username, String password);
}
