package cn.edu.zju.sishi.commons.utils;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class BindResultUtils {

    public static void validData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }

            throw new ValueException(sb.toString());
        }
    }

}
