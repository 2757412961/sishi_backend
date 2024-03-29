package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.RedisKeys;
import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.message.LoginMessage.LoginResponse;
import cn.edu.zju.sishi.message.LoginMessage.RegisterResponse;
import cn.edu.zju.sishi.service.LoginService;
import cn.edu.zju.sishi.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse login(@RequestParam(value = "userName") String username,
                               @RequestParam(value = "password") String password) {
        return loginService.login(username, password);
    }

    @Transactional
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public RegisterResponse register(@RequestBody @Validated User user,
                                     BindingResult bindingResult,
                                     @RequestParam(value = "captcha") String captcha) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        RegisterResponse register = loginService.register(user);

        // redis 中存储的验证码
        String redisCaptcha = redisService.get(RedisKeys.REDIS_Captcha_PREFIX + user.getEmail());
        System.out.println(redisCaptcha);
        if (StringUtils.isEmpty(redisCaptcha)) {
            throw new ValidationException("验证码失效，请重新发送！");
        }
        if (!captcha.equals(redisCaptcha)) {
            throw new ValidationException("验证码错误");
        }

        return register;
    }
}
