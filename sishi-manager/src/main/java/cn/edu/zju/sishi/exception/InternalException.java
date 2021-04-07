package cn.edu.zju.sishi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: zjh
 * @date : 2021/4/2 15:54
 * @Email : 2757412961@qq.com
 * @update:
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalException extends BaseException {
    public InternalException(String msg) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }
}
