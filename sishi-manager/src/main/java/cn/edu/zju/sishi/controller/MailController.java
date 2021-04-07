package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.RedisKeys;
import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.message.MailMessage.MailResponse;
import cn.edu.zju.sishi.message.UserMessage.UpdatePasswordRequest;
import cn.edu.zju.sishi.service.MailService;
import cn.edu.zju.sishi.service.RedisService;
import cn.edu.zju.sishi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author: zjh
 * @date : 2021/4/2 16:41
 * @Email : 2757412961@qq.com
 * @update:
 */

@Slf4j
@RestController
@RequestMapping("/email")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    /*
     *  redis 相关
     */
    @Autowired
    private RedisService redisService;

    public static final int REDIS_EXPIRE_TIME = 120;

    /**
     * @Description: 发送验证码
     * @Param: [emailAddress]
     * @return: cn.edu.zju.sishi.message.MailMessage.MailResponse
     * @Author: zjh
     * @Date: 2021/4/6
     */
    @GetMapping("/sendCaptcha")
    public MailResponse sendCaptcha(@RequestParam("emailAddress") String emailAddress) {
        MailResponse mailResponse = new MailResponse();

        log.info("Start invoke sendCaptcha()");
        Integer captcha = 100000 + new Random().nextInt(899999);
        redisService.set(RedisKeys.REDIS_Captcha_PREFIX + emailAddress, captcha.toString(), REDIS_EXPIRE_TIME);
        mailService.sendCaptcha(emailAddress, captcha);

        mailResponse.setMessage("发送成功，验证码将在120秒后过期");
        return mailResponse;
    }

    /**
     * @Description: 密码重置
     * @Param: [emailAddress]
     * @return: cn.edu.zju.sishi.message.MailMessage.MailResponse
     * @Author: zjh
     * @Date: 2021/4/6
     */
    @Transactional
    @GetMapping("/resetPassword")
    public MailResponse resetPasswordByEmail(@RequestParam("emailAddress") String emailAddress) {
        MailResponse mailResponse = new MailResponse();

        log.info("Start invoke resetPasswordByEmail()");
        String newPassword = RandomStringUtils.randomAlphanumeric(8);
        userService.updatePasswordByEmail(emailAddress, newPassword);
        mailService.sendResetPassword(emailAddress, newPassword);

        mailResponse.setMessage("密码重置成功");
        mailResponse.setNewPassword(newPassword);
        return mailResponse;
    }

    /**
     * @Description: 根据邮箱修改密码：1.发送验证码 2.填入验证码和新的密码 3.修改成功
     * @Param: [emailAddress]
     * @return: cn.edu.zju.sishi.message.MailMessage.MailResponse
     * @Author: zjh
     * @Date: 2021/4/6
     */
    @Transactional
    @GetMapping("/updatePassword")
    public MailResponse updatePasswordByEmail(@RequestParam("captcha") String captcha,
                                              @RequestParam("emailAddress") String emailAddress,
                                              @RequestParam("newPassword") String newPassword) {
        MailResponse mailResponse = new MailResponse();

        log.info("Start invoke updatePasswordByEmail()");
        userService.updatePasswordByEmail(emailAddress, newPassword);
        String redisCaptcha = redisService.get(RedisKeys.REDIS_Captcha_PREFIX + emailAddress);
        if (StringUtils.isEmpty(redisCaptcha)) {
            throw new ValidationException("验证码失效");
        }
        if (!captcha.equals(redisCaptcha)) {
            throw new ValidationException("验证码错误");
        }
        mailService.sendResetPassword(emailAddress, newPassword);

        mailResponse.setMessage("根据邮箱修改密码：1.发送验证码 2.填入验证码和新的密码 3.修改成功");
        return mailResponse;
    }


}
