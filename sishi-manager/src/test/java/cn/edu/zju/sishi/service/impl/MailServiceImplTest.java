package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;


/**
 * @author: zjh
 * @date : 2021/4/2 15:35
 * @Email : 2757412961@qq.com
 * @update:
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleEmail() {
//        MailBean mailBean = new MailBean();
//        mailBean.setRecipient("2757412961@qq.com");
//        mailBean.setSubject("SpringBootMail之这是一封文本的邮件");
//        mailBean.setContent("SpringBootMail发送一个简单格式的邮件，时间为：" + new Date().toString());

//        mailService.sendSimpleMail("2757412961@qq.com");
    }

    @Test
    public void sendSimpleMail() {
        int captcha = 100000 + new Random().nextInt(899999);

        mailService.sendHTMLMail("2757412961@qq.com");
        System.out.println(Math.abs(new SecureRandom().nextInt()));
        System.out.println(Math.abs(new SecureRandom().nextInt()));
        System.out.println(Math.abs(new SecureRandom().nextInt()));
        System.out.println(Math.abs(new SecureRandom().nextInt()));
        System.out.println(100000 + new Random().nextInt(899999));
        System.out.println(100000 + new Random().nextInt(899999));
        System.out.println(100000 + new Random().nextInt(899999));
        System.out.println(100000 + new Random().nextInt(899999));
        System.out.println(100000 + new Random().nextInt(899999));
        System.out.println(100000 + new Random().nextInt(899999));
    }

    @Test
    public void getRedisCaptcha() {
        System.out.println(mailService.getRedisCaptcha(""));
        System.out.println(StringUtils.isEmpty(mailService.getRedisCaptcha("")));
    }
}
