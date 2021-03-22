package cn.edu.zju.sishi.commons;

import cn.edu.zju.sishi.commons.utils.ValidateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * @author lemon
 * @date 2021/3/21
 */
public @interface MobilePhoneConstraint {

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class MobilePhoneValidator implements ConstraintValidator<MobilePhoneConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (value == null || value.length() == 0) {
        return true;
      }
      //default the default message
      context.disableDefaultConstraintViolation();
      //set message
      context.buildConstraintViolationWithTemplate(
        ValidateUtils.ValidateEnum.MOBILEPHONE.getError()).addConstraintViolation();
      return ValidateUtils.validate(value, ValidateUtils.ValidateEnum.MOBILEPHONE.getRegular());
    }
  }
}



