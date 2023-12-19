package com.easybytes.springsecuritybasic.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Slf4j
public class AuthoritiesLoggingAfterFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            log.info("User {} 님은 성공적으로 자격 증명 절차에서 살아남으셨습니다.",authentication.getName());
        }

        chain.doFilter(request, response);

    }
}
