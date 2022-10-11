package com.example.server.security.context;

import com.example.common.security.UserDetails;

/**
 * 保存、获取用户信息
 *
 * @author admin
 */
public interface SecurityContextHolder {

    UserDetails getUserDetails();

    void setUserDetails(UserDetails userDetails);
}
