package com.jsondriventemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import com.jsondriventemplate.constant.AppConstant;
import com.jsondriventemplate.constant.AppUserRoleConstant;
import com.jsondriventemplate.constant.UrlConstant;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider authProvider;

    public SecSecurityConfig(CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().addHeaderWriter(
                new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .authorizeRequests()
                .antMatchers(UrlConstant.TEMPLATES).hasRole(AppUserRoleConstant.NOT_PERMITTED)
                .antMatchers(UrlConstant.ADMINS).hasRole(AppUserRoleConstant.SUPER_ADMIN)
                .antMatchers(UrlConstant.AUTHS).authenticated()
                .anyRequest().permitAll()
                .and().formLogin()
                .loginPage(UrlConstant.LOGIN)
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
                .and().logout().deleteCookies().logoutSuccessUrl(UrlConstant.URL_ROOT).invalidateHttpSession(true)
                .deleteCookies(AppConstant.JSESSIONID);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new CustomSuccessHandler();
    }
}