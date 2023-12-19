package com.easybytes.springsecuritybasic.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestValidationBeforeFilter implements Filter {

    public static final String AUTHENTICATION_SCHEME_BASID = "Basic";
    private final Charset credentialsCharset = StandardCharsets.UTF_8;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null){
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASID)){
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, credentialsCharset);
                    int delim = token.indexOf(":");
                    if (delim == -1){
                        throw new BadCredentialsException("인증되지않은 토큰입니다.");
                    }
                    String email = token.substring(0, delim);
                    if (email.toLowerCase().contains("test")){
                        res.setStatus(HttpStatus.BAD_REQUEST.value());
                        return;
                    }
                }catch (IllegalArgumentException e){
                    throw new BadCredentialsException("인증토큰 복호화에 실패했습니다.");
                }
            }
        }
        chain.doFilter(request,response);

    }
}
