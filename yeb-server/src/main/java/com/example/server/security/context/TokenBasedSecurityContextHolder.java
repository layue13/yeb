package com.example.server.security.context;

import com.example.common.security.UserDetails;

public class TokenBasedSecurityContextHolder implements SecurityContextHolder {

    /**
     * 为每一个线程维护一个独立数据空间
     * tomcat 中每一次请求进来了，都会分配一个独立的线程
     * 因此这个根据，就可以理解为，为每一个请求维护独立的会话空间
     * <p>
     * Map<Thread, UserDetails>
     * get() -> Map.get(Thread.currentThread)
     * set() -> Map.set(Thread.currentThread, userDetails)
     */
    private final ThreadLocal<UserDetails> USER_DETAILS_HOLDER = new ThreadLocal<>();

    @Override
    public UserDetails getUserDetails() {

        return USER_DETAILS_HOLDER.get();
    }

    @Override
    public void setUserDetails(UserDetails userDetails) {

        USER_DETAILS_HOLDER.set(userDetails);
    }
}