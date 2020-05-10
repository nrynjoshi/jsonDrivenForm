package com.jsondriventemplate;

import com.jsondriventemplate.exception.AuthenticationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public class AuthProvider {

    public static UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken(String username,String password) throws AuthenticationException {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw new AuthenticationException("Username/Password is empty");
        }
        Set<GrantedAuthority> authoritySet=new HashSet<>();
        if(StringUtils.equalsIgnoreCase(username,"superadmin")){
            authoritySet.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
        }
        authoritySet.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(username, password,authoritySet);
    }



}
