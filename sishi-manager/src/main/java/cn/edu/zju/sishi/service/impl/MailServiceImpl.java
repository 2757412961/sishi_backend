package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.exception.InternalException;
import cn.edu.zju.sishi.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zjh
 * @date : 2021/4/2 15:28
 * @Email : 2757412961@qq.com
 * @update:
 */

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    /** 邮件 相关 */
    @Autowired
    private JavaMailSender javaMailSender;

    /** 邮件发送者 */
    @Value("${spring.mail.username}")
    private String MAIL_SENDER;

    private final String MAIL_SUBJECT = "党史学习教育平台邮箱验证（浙江大学 地球科学学院）";

    @Override
    public void sendSimpleMail(String recipient) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(MAIL_SENDER);
            mailMessage.setTo(recipient);
            mailMessage.setSubject(MAIL_SUBJECT);
            mailMessage.setText("SpringBootMail发送一个简单格式的邮件，时间为：" + new Date().toString());

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new InternalException("邮件发送失败" + e.getMessage());
        }
    }

    /**
     * @Description: 按照html的格式发送邮件
     * @Param: [recipient]
     * @return: void
     * @Author: zjh
     * @Date: 2021/4/2
     */
    @Override
    public void sendCaptcha(String recipient, Integer captcha) {
        try {
            // 与文本格式邮件代码对比，富文本HTML邮件发送使用MimeMessageHelper类，把setText()方法的消息文本设置为html,
            // 并将第二个参数设置为true,表示这是html的富文本。MimeMessageHelper支持发送复杂邮件模板，支持文本、附件、HTML、图片等。
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //true 表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(MAIL_SUBJECT);
            mimeMessageHelper.setText("<h2>党史学习教育平台（浙江大学 地球科学学院）</h2>" +
                            "<p>尊敬的用户：</p>" +
                            "<p style='text-indent:2em'>您好！</p>" +
                            "<p style='text-indent:2em'>欢迎使用党史学习教育平台！</p>" +
                            "<p style='text-indent:2em'>当前时间为：" + new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date()) + "</p>" +
                            "<p style='text-indent:2em'>您的验证码为：" + captcha + "</p>" +
                            "<p>此致，敬礼</p>",
                    true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new InternalException("邮件发送失败" + e.getMessage());
        }
    }

    @Override
    public void sendResetPassword(String recipient, String newPassword) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(MAIL_SUBJECT);
            mimeMessageHelper.setText("<h2>党史学习教育平台（浙江大学 地球科学学院）</h2>" +
                            "<p>尊敬的用户：</p>" +
                            "<p style='text-indent:2em'>您好！</p>" +
                            "<p style='text-indent:2em'>欢迎使用党史学习教育平台！</p>" +
                            "<p style='text-indent:2em'>当前时间为：" + new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date()) + "</p>" +
                            "<p style='text-indent:2em'>您的新密码为：" + newPassword + "</p>" +
                            "<p>此致，敬礼</p>",
                    true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new InternalException("密码重置失败" + e.getMessage());
        }
    }



}
