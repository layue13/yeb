package com.example.server.security.authorize;

import com.example.common.security.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HasAnyRoleAuthorizeDecider implements AuthorizeDecider {

    @Override
    public boolean decide(List<? extends GrantedAuthority> allowed, List<? extends GrantedAuthority> had) {

        for (GrantedAuthority allowedAuthority : allowed) {

            for (GrantedAuthority hadAuthority : had) {

                if (allowedAuthority.getPerm().equals(hadAuthority.getPerm())) {
                    return true;
                }
            }
        }
        return false;
    }
}
