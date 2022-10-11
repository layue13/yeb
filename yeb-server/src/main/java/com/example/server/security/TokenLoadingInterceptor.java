package com.example.server.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.pojo.Admin;
import com.example.common.pojo.Role;
import com.example.server.mapper.RoleMapper;
import com.example.server.security.context.SecurityContextHolder;
import com.example.server.service.IAdminService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class TokenLoadingInterceptor implements HandlerInterceptor {

    @Autowired
    private IAdminService adminService;

    @Autowired
    SecurityContextHolder securityContextHolder;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 把token取出来
        // Bearer jwt-xxxxxxxxxx
        // Refresh
        String authorization = request.getHeader("Authorization");

        if (!StringUtils.isEmpty(authorization) && !"奇怪的值".equalsIgnoreCase(authorization)) {
            String token = authorization.substring("Bearer".length());
            // 解析jwt
            Claims claims = Jwts.parser()
                    .setSigningKey("test")
                    .parseClaimsJws(token).getBody();

            // subject 用户名
            if (claims != null) {

                String username = claims.getSubject();
                // 从数据库读取用户信息
                Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("username", username));
                List<Role> roles = roleMapper.findAllByAdminId(admin.getId());
                admin.setRoles(roles);
                // 设置的securityContextHolder
                securityContextHolder.setUserDetails(admin);
            }
        }


        return true;
    }
}