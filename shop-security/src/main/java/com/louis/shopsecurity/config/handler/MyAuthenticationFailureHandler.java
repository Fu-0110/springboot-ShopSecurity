package com.louis.shopsecurity.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louis.shopsecurity.controller.result.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure (
            HttpServletRequest request , HttpServletResponse response , AuthenticationException exception
    ) throws IOException, ServletException {

        logger.info(" FailureHandler Request Method: {}", request.getMethod());
        logger.info(" FailureHandler Request URL: {}", request.getRequestURL());
        logger.info(" FailureHandler Remote Address: {}", request.getRemoteAddr());
        logger.info(" FailureHandler Remote Host: {}", request.getRemoteHost());
        logger.info(" FailureHandler Protocol: {}", request.getProtocol());
        logger.info(" FailureHandler Session ID: {}", request.getRequestedSessionId());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        BaseResult result = new BaseResult(HttpServletResponse.SC_UNAUTHORIZED , "使用者名稱或密碼錯誤");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(result);

        logger.info(" FailureHandler 使用者名稱或密碼錯誤");

        PrintWriter out = response.getWriter();
        out.write(json);
        out.close();
    }
}