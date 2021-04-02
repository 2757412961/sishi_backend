package cn.edu.zju.sishi.message.MailMessage;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zjh
 * @date : 2021/4/2 15:29
 * @Email : 2757412961@qq.com
 * @update:
 */

@Getter
@Setter
public class MailResponse implements Serializable {

    private String message;

    private String newPassword;

}
