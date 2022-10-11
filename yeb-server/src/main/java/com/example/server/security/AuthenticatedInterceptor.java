package com.example.server.security;

import com.example.common.security.UserDetails;
import com.example.server.security.context.SecurityContextHolder;
import com.example.server.vo.RespBean;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Data
public class AuthenticatedInterceptor implements HandlerInterceptor {

    @Autowired
    private SecurityContextHolder securityContextHolder;

    @Value("${yeb.enable-authenticated-annotation}")
    private boolean enableAuthenticated;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {

            Authenticated methodAnnotation = handlerMethod.getMethodAnnotation(Authenticated.class);

            // 当注解支持关闭的时候 或者 开启了注解支持，并且访问方法添加了注解
            if (!enableAuthenticated || (enableAuthenticated && methodAnnotation != null)) {

                // 验证用户是否登录
                UserDetails auth = securityContextHolder.getUserDetails();

                if (auth == null) {

                    // 抛出异常
                    // 4xx 认证或者授权的异常
                    throw RespBean.AUTH_FAILED_ERROR.toException();
                }
                // 用户已经登录过了，直接放行
                return true;
            }
        }

        // 当需要访问的方法没有被注解修饰，说明这个方法是公共方法，没有登陆的请求也能访问
        return true;
    }
}
