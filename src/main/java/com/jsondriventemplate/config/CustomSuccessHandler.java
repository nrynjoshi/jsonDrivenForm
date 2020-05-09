package com.jsondriventemplate.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = null;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		GrantedAuthority role_super_admin = authorities.stream().filter(data -> StringUtils.equals(((GrantedAuthority) data).getAuthority(), "ROLE_SUPER_ADMIN")).findFirst().get();
		if(role_super_admin!=null){
			redirectUrl = "/admin/dashboard";
		}else{
			for (GrantedAuthority grantedAuthority : authorities) {
				System.out.println("role " + grantedAuthority.getAuthority());
				if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
					redirectUrl = "/auth/dashboard";
					break;
				} else if (grantedAuthority.getAuthority().equals("ROLE_SUPER_ADMIN")) {
					redirectUrl = "/admin/dashboard";
					break;
				}
			}
		}

		System.out.println("redirectUrl " + redirectUrl);
		if (redirectUrl == null) {
			throw new IllegalStateException();
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}