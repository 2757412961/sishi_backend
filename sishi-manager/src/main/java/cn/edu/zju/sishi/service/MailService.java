package cn.edu.zju.sishi.service;

/**
 * @author: zjh
 * @date : 2021/4/2 15:28
 * @Email : 2757412961@qq.com
 * @update:
 */

public interface MailService {

    void sendSimpleMail(String recipient);

    void sendCaptcha(String recipient, Integer captcha);

    void sendResetPassword(String recipient, String newPassword);



}
