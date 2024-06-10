package com.louis.shopsecurity.jwt.filter;

import cn.hutool.crypto.SecureUtil;
import com.louis.shopsecurity.jwt.dto.PayloadDto;
import com.louis.shopsecurity.jwt.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
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
    @Override
    protected void doFilterInternal (
            HttpServletRequest request , HttpServletResponse response , FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            filterChain.doFilter(request , response);
            return;
        }
        //如果請求標頭中有token，則進行解析，並且設定認證資訊
        try {
            SecurityContextHolder.getContext()
                                 .setAuthentication(getAuthentication(token));
            filterChain.doFilter(request , response);
        }
        catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
    }

    //驗證token，並解析token，傳回以使用者名稱和密碼所表示的經過身分驗證的主體的權杖
    private UsernamePasswordAuthenticationToken getAuthentication (String token) throws ParseException, JOSEException {
        PayloadDto payloadDto = JwtUtil.verifyTokenByHMAC(token , SecureUtil.md5(JwtUtil.DEFAULT_SECRET));
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
