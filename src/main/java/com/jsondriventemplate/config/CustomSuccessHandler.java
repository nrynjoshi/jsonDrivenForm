package com.jsondriventemplate.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jsondriventemplate.constant.AppUserRoleConstant;
import com.jsondriventemplate.constant.UrlConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		String redirectUrl = null;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Optional<? extends GrantedAuthority> role_super_admin = authorities.stream().filter(data -> StringUtils.equals(data.getAuthority(), "ROLE_SUPER_ADMIN")).findFirst();

		if(role_super_admin.isPresent()){
			redirectUrl = UrlConstant.ADMIN+UrlConstant.DASHBOARD;
		}else{
			for (GrantedAuthority grantedAuthority : authorities) {
				System.out.println("role " + grantedAuthority.getAuthority());
				if (grantedAuthority.getAuthority().equals(AppUserRoleConstant.ROLE_USER)) {
					redirectUrl = UrlConstant.AUTH+UrlConstant.DASHBOARD;
					break;
				} else if (grantedAuthority.getAuthority().equals(AppUserRoleConstant.ROLE_SUPER_ADMIN)) {
					redirectUrl = UrlConstant.ADMIN+UrlConstant.DASHBOARD;
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