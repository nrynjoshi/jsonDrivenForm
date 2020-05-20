package com.jsondriventemplate.logic;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.exception.AuthenticationException;
import com.jsondriventemplate.repo.DBConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthProvider {

    public static UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken(String username, String password) throws AuthenticationException {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new AuthenticationException("Username/Password is empty");
        }
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        Map user = AppInject.mongoClientProvider.findByAtt("username", username, DBConstant.USER);
        if (user == null || user.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        }

        boolean validPassword = AppInject.passwordEncoder.matches(password, (String) user.get("password"));
        if (!validPassword) {
            throw new UsernameNotFoundException("username not found");
        }
        String role = (String) user.get("role");
        authoritySet.add(new SimpleGrantedAuthority("ROLE_" + StringUtils.upperCase(role)));

        return new UsernamePasswordAuthenticationToken(username, password, authoritySet);
    }


}
