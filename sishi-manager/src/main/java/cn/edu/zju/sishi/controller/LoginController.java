package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.message.LoginMessage.LoginResponse;
import cn.edu.zju.sishi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;
  
  @RequestMapping(value = "login", method = RequestMethod.POST)
  @ResponseBody
  public LoginResponse login(@RequestParam(value = "userName") String username, 
                             @RequestParam(value = "password") String password) {
    return loginService.login(username, password);
  }

  @RequestMapping(value = "register",method = RequestMethod.POST)
  @ResponseBody
  public LoginResponse register(@RequestBody @Validated User user, BindingResult bindingResult)  {
    if(bindingResult.hasErrors()) {
      throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
    }
    return loginService.register(user);
  }
}
