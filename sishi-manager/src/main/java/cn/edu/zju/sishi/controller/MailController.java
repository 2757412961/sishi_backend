package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.User;
import cn.edu.zju.sishi.message.MailMessage.MailResponse;
import cn.edu.zju.sishi.service.MailService;
import cn.edu.zju.sishi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/sendCaptcha")
    public MailResponse sendCaptcha(@RequestParam("emailAddress") String emailAddress) {
        MailResponse mailResponse = new MailResponse();

        log.info("Start invoke sendCaptcha()");
        mailService.sendHTMLMail(emailAddress);

        mailResponse.setMessage("发送成功，验证码将在120秒后过期");
        return mailResponse;
    }

    @Transactional
    @GetMapping("/resetPassword")
    public MailResponse resetPasswordByEmail(@RequestParam("emailAddress") String emailAddress) {
        MailResponse mailResponse = new MailResponse();

        log.info("Start invoke resetPassword()");
        String newPassword = userService.resetPasswordByEmail(emailAddress);
        mailService.sendResetPassword(emailAddress, newPassword);

        mailResponse.setMessage("密码重置成功");
        mailResponse.setNewPassword(newPassword);
        return mailResponse;
    }




}
