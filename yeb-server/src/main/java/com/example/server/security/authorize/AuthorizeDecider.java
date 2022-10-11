package com.example.server.security.authorize;

import com.example.common.security.GrantedAuthority;

import java.util.List;

public interface AuthorizeDecider {


    boolean decide(List<? extends GrantedAuthority> allowed, List<? extends GrantedAuthority> had);

//    <T, U> boolean decide2(List<T> a, List<U> b);
}
