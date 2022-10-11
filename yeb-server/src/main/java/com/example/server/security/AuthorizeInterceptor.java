package com.example.server.security;

import com.example.common.security.GrantedAuthority;
import com.example.server.exception.YebException;
import com.example.server.security.authorize.AuthorizeDecider;
import com.example.server.security.authorize.HasAnyRoleAuthorizeDecider;
import com.example.server.security.authorize.PreAuthorizeResourceMetaDataProvider;
import com.example.server.security.authorize.ResourceMetaDataProvider;
import com.example.server.security.context.SecurityContextHolder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Data
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Autowired
    private SecurityContextHolder securityContextHolder;

    private ResourceMetaDataProvider resourceMetaDataProvider;

    private AuthorizeDecider authorizeDecider;

    @Value("${yeb.enable-authorized-annotation}")
    private boolean enableAuthorize;

    @Autowired
    private PreAuthorizeResourceMetaDataProvider preAuthorizeResourceMetaDataProvider;

    @Autowired
    private HasAnyRoleAuthorizeDecider hasAnyRoleAuthorizeDecider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            /**
             * 1 获取到请求的url地址，在数据库中匹配允许的权限
             *
             * 2 获取用户具备的权限
             *
             * 3 讲两者进行比较，如果两者有交集，则通过授权；否则报错
             */
            if (enableAuthorize) {

                this.resourceMetaDataProvider = preAuthorizeResourceMetaDataProvider;

                PreAuthorize methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(PreAuthorize.class);

                if (methodAnnotation != null) {
                    String type = methodAnnotation.type();

                    if (type.equals("hasAnyRole")) {
                        this.authorizeDecider = hasAnyRoleAuthorizeDecider;
                    } else if (type.equals("hasAllRoles")) {
                        // TODO 替换decider

                    }
                }
            }

            List<? extends GrantedAuthority> allowed = resourceMetaDataProvider.provide(request, response, (HandlerMethod) handler);

            if (allowed == null || allowed.size() == 0) {
                return true;
            }

            List<? extends GrantedAuthority> had = securityContextHolder.getUserDetails().getPerms();

            boolean decide = authorizeDecider.decide(allowed, had);

            if (decide) {
                return true;
            } else {

                throw new YebException(411, "权限不允许！");
            }
        }

        return true;
    }
}
