package com.example.server.security.authorize;

import com.example.common.security.GrantedAuthority;
import com.example.server.security.PreAuthorize;
import com.example.server.security.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PreAuthorizeResourceMetaDataProvider implements ResourceMetaDataProvider {

    @Override
    public List<? extends GrantedAuthority> provide(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {

        PreAuthorize methodAnnotation = handler.getMethodAnnotation(PreAuthorize.class);

        if (methodAnnotation != null) {

            String[] roles = methodAnnotation.roles();

            List<SimpleGrantedAuthority> allowedAuthorities = Arrays.asList(roles).stream() // 将数组roles 转换为 list<string>
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return allowedAuthorities;
        }
        return null;
    }
}
