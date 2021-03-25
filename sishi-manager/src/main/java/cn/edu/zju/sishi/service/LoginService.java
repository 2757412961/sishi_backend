package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.message.LoginMessage.LoginResponse;
import cn.edu.zju.sishi.message.LoginMessage.RegisterResponse;

public interface LoginService {
  
  RegisterResponse register(User user);

  LoginResponse login(String username, String password);
}
