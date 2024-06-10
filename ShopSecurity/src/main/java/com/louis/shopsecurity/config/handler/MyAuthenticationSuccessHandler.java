package com.louis.shopsecurity.config.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louis.shopsecurity.controller.result.BaseResult;
import com.louis.shopsecurity.jwt.dto.PayloadDto;
import com.louis.shopsecurity.jwt.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess (
            HttpServletRequest request , HttpServletResponse response , Authentication authentication
    ) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails)principal;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> authoritiesList = new ArrayList<>(authorities.size());
            authorities.forEach(authority -> {
                authoritiesList.add(authority.getAuthority());
            });

            Date now = new Date();
            Date exp = DateUtil.offsetSecond(now , 60 * 60);
            PayloadDto payloadDto = PayloadDto.builder()
                                              .sub(user.getUsername())
                                              .iat(now.getTime())
                                              .exp(exp.getTime())
                                              .jti(UUID.randomUUID()
                                                       .toString())
                                              .username(user.getUsername())
                                              .authorities(authoritiesList)
                                              .build();
            String token = null;
            try {
                token = JwtUtil.generateTokenByHMAC(
                        // nimbus-jose-jwt 所使用的 HMAC SHA256 演算法
                        // 所需金鑰長度至少要256位元(32位元組)，因此先用md5加密一下
                        JSONUtil.toJsonStr(payloadDto) , SecureUtil.md5(JwtUtil.DEFAULT_SECRET));
                response.setHeader("Authorization" , token);

                response.setContentType("application/json;charset=UTF-8");
                BaseResult result = new BaseResult(HttpServletResponse.SC_OK , "登录成功");
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                //使用Spring Boot預設的Jackson JSON函式庫中的ObjectMapper類別將物件轉換為JSON字串
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(result);
                out.write(json);
                out.close();
            }
            catch (JOSEException e) {
                e.printStackTrace();
            }
        }
    }
}
