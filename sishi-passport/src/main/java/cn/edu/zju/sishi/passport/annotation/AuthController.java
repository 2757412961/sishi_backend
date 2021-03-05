package cn.edu.zju.sishi.passport.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

/**
 * 类说明 
 * Controller需要Passport认证
 */
public @interface AuthController {
  boolean value() default true;
}

