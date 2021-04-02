package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.message.MailMessage.CaptchaResponse;
import cn.edu.zju.sishi.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/sendCaptcha")
    public CaptchaResponse sendCaptcha(@RequestParam("recipient") String recipient){
        CaptchaResponse captchaResponse = new CaptchaResponse();

        log.info("Start invoke sendCaptcha()");
        mailService.sendHTMLMail(recipient);

        captchaResponse.setMessage("发送成功，验证码将在120秒后过期");
        return captchaResponse;
    }

}
