package cn.zhh.sso.configuration;

import cn.zhh.sso.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/index.html",
                "/js/**",
                "/login",
                "/logout"
        );
    }
}
