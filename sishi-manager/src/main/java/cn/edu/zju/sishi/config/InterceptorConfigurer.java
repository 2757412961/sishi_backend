package cn.edu.zju.sishi.config;

import cn.edu.zju.sishi.passport.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/tag/**", "/tags/**"
//                "/article/**", "/articles/**",
//                "/audio/**", "/audios/**",
//                "/video/**", "/videos/**",
//                "/picture/**", "/pictures/**",
//                "/mapinfo/**", "/mapinfos/**",
//                "/question/**", "/questions/**"
        );
    }
}
