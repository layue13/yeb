package com.example.server.security.authorize;

import com.example.common.security.GrantedAuthority;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ResourceMetaDataProvider {

    List<? extends GrantedAuthority> provide(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);
}
