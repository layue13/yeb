package com.example.server.security;

import com.example.common.security.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SimpleGrantedAuthority implements GrantedAuthority {

    private String perm;

    @Override
    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }
}
