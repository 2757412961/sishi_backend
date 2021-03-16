package cn.edu.zju.sishi.commons.utils;

import cn.edu.zju.sishi.exception.ValidationException;
import org.springframework.validation.BindingResult;

public class BindResultUtils {

    public static void validData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            StringBuffer sb = new StringBuffer();
//            for (ObjectError error : bindingResult.getAllErrors()) {
//                sb.append(error.getDefaultMessage());
//            }
//            throw new ValidationException(sb.toString());

            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
    }

}
