package com.louis.shopsecurity.jwt.filter;

import cn.hutool.crypto.SecureUtil;
import com.louis.shopsecurity.jwt.dto.PayloadDto;
import com.louis.shopsecurity.jwt.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal (
            HttpServletRequest request , HttpServletResponse response , FilterChain filterChain
    ) throws ServletException, IOException {

        logger.info(" JwtAuthenticationFilter Request Method: {}", request.getMethod());
        logger.info(" JwtAuthenticationFilter Request URL: {}", request.getRequestURL());
        logger.info(" JwtAuthenticationFilter Session ID: {}", request.getRequestedSessionId());

        String token = request.getHeader("Authorization");
        if (token == null) {

            logger.info(" JwtAuthenticationFilter token == null ");

            filterChain.doFilter(request , response);
            return;
        }

        // 如果請求標頭中有 token，則進行解析，並且設定認證資訊
        try {

            logger.info(" JwtAuthenticationFilter token getAuthentication ");

            SecurityContextHolder.getContext()
                                 .setAuthentication(getAuthentication(token));
            filterChain.doFilter(request , response);
        }
        catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
    }

    // 驗證 並解析 token，傳回以 使用者名稱、密碼 所表示的經過 身分驗證的 主體的 權杖
    private UsernamePasswordAuthenticationToken getAuthentication (String token) throws ParseException, JOSEException {

        PayloadDto payloadDto = JwtUtil.verifyTokenByHMAC(token , SecureUtil.md5(JwtUtil.DEFAULT_SECRET));

        logger.info(" JwtAuthenticationFilter token 主題 : {}", payloadDto.getSub());
        logger.info(" JwtAuthenticationFilter token 簽發時間 : {}", payloadDto.getIat());
        logger.info(" JwtAuthenticationFilter token 過期時間 : {}", payloadDto.getExp());
        logger.info(" JwtAuthenticationFilter token JWT ID : {}", payloadDto.getJti());
        logger.info(" JwtAuthenticationFilter token 使用者名稱 : {}", payloadDto.getUsername());
        logger.info(" JwtAuthenticationFilter token 使用者許可權 : {}", payloadDto.getAuthorities());

        String username = payloadDto.getUsername();
        List<String> roles = payloadDto.getAuthorities();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        if (username != null) {
            return new UsernamePasswordAuthenticationToken(username , null , authorities);

        }
        return null;
    }
}