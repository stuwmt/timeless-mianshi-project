package com.timeless.mianshi.satoken;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.timeless.mianshi.common.ErrorCode;
import com.timeless.mianshi.constant.UserConstant;
import com.timeless.mianshi.exception.BusinessException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册 Sa-Token 拦截器，打开注解式鉴权功能 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能 
        registry.addInterceptor(new SaInterceptor(
                        handle -> {
                            StpUtil.getRoleList().forEach(role -> {
                                if (role.equals(UserConstant.BAN_ROLE)) {
                                    // 清除sa-token的登录状态
                                    StpUtil.logout();
                                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "您已被封号");
                                }
                            });
                        }
                )).addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/user/logout");
    }
}
