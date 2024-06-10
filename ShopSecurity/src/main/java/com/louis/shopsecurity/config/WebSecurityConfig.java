package com.louis.shopsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louis.shopsecurity.controller.result.BaseResult;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

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
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(new MyLoginUrlAuthenticationEntryPoint());
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    /**
     * 以 private static class 的方式實作 AuthenticationEntryPoint.commence()
     */
    private static class MyLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence (
                HttpServletRequest request , HttpServletResponse response , AuthenticationException authException
        ) throws IOException, ServletException {

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


