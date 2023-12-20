package com.easybytes.springsecuritybasic.filter;

import com.easybytes.springsecuritybasic.constant.SecurityConstant;
import com.easybytes.springsecuritybasic.model.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtTokenValidateFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(SecurityConstant.JWT_HEADER);
        if (jwt != null){
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        SecurityConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e){
                throw new BadCredentialsException("잘못된 토큰입니다.");
            }
        }

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/user");
    }

}
