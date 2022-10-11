package com.example.server.security.authorize;

import com.example.common.pojo.Menu;
import com.example.common.pojo.Role;
import com.example.common.security.GrantedAuthority;
import com.example.server.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class JdbcResourceMetaDataProvider implements ResourceMetaDataProvider {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<? extends GrantedAuthority> provide(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {

        String url = request.getRequestURI();

        List<Menu> menus = menuMapper.findAllWithRoles();

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        List<Role> allowedRoles = null;

        for (Menu menu : menus) {

            if (antPathMatcher.match(menu.getUrl(), url)) {

                allowedRoles = menu.getRoles();
            }
        }

        return allowedRoles;
    }
}
