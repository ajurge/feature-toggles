package com.bipinet.spring.featuretoggles.togglez.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bipinet.spring.featuretoggles.config.security.Roles.ADMIN;


@Configuration
@Order(0)
@EnableWebSecurity
public class TogglzConsoleSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REALM = "FT_BA_REALM";

    private static void setUnauthorizedHeader(HttpServletResponse response) throws IOException {
        //Authorisation and in turn Authentication failed, send error response.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + REALM + "");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/togglz-console/**")
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .httpBasic()
                .authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint())
                .realmName(REALM)
                .and()
                .authorizeRequests()
                .anyRequest().hasRole(ADMIN);
    }

    public class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
            setUnauthorizedHeader(response);
        }
    }

    public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException {
            setUnauthorizedHeader(response);
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            setRealmName(REALM);
            super.afterPropertiesSet();
        }
    }

}