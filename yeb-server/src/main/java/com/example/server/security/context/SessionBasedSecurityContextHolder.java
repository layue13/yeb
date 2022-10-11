package com.example.server.security.context;

import com.example.common.security.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class SessionBasedSecurityContextHolder implements SecurityContextHolder {

    @Override
    public UserDetails getUserDetails() {

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        UserDetails auth = (UserDetails) request.getSession().getAttribute("auth");

        return auth;
    }

    @Override
    public void setUserDetails(UserDetails userDetails) {

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        request.getSession().setAttribute("auth", userDetails);
    }
}
