package com.example.common.security;

import java.util.List;

/**
 * @author admin
 */
public interface UserDetails {

    String getUsername();

    String getPassword();

    List<? extends GrantedAuthority> getPerms();
}
