package com.jsondriventemplate.logic;

import com.jsondriventemplate.constant.AppUserRoleConstant;
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
        if(StringUtils.equalsIgnoreCase(username,AppUserRoleConstant.SUPER_ADMIN)){
            authoritySet.add(new SimpleGrantedAuthority(AppUserRoleConstant.ROLE_SUPER_ADMIN));
        }
        authoritySet.add(new SimpleGrantedAuthority(AppUserRoleConstant.ROLE_USER));
        return new UsernamePasswordAuthenticationToken(username, password,authoritySet);
    }



}
