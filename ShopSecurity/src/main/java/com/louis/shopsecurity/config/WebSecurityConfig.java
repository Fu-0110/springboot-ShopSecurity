package com.louis.shopsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louis.shopsecurity.controller.result.BaseResult;
import com.louis.shopsecurity.jwt.filter.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure (HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/resource/**")
            .hasRole("USER")

            .anyRequest()
            .permitAll()
            .and()

            .formLogin()
            .loginProcessingUrl("/login")
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .and()

            .logout()
            .and()

            .addFilterBefore(new JwtAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class)
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(new MyLoginUrlAuthenticationEntryPoint());
    }

    /**
     * 解決 當未經授權的請求存取保護資源時，Spring Sercurity 預設會重新導向到登入頁面
     * 以 private static class 的方式實作 AuthenticationEntryPoint.commence()
     */
    private static class MyLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence (
                HttpServletRequest request , HttpServletResponse response ,
                AuthenticationException authenticationException
        ) throws IOException, ServletException {

            logger.info(" WebSecurityConfig Request Method: {}", request.getMethod());
            logger.info(" WebSecurityConfig Request URL: {}", request.getRequestURL());
            logger.info(" WebSecurityConfig Remote Address: {}", request.getRemoteAddr());
            logger.info(" WebSecurityConfig Remote Host: {}", request.getRemoteHost());
            logger.info(" WebSecurityConfig Protocol: {}", request.getProtocol());
            logger.info(" WebSecurityConfig Session ID: {}", request.getRequestedSessionId());

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            BaseResult result = new BaseResult(HttpServletResponse.SC_FORBIDDEN , "未登入，無權存取");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);

            PrintWriter out = response.getWriter();
            out.write(json);
            out.close();
        }
    }
}

