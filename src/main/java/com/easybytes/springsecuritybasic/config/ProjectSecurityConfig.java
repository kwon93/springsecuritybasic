package com.easybytes.springsecuritybasic.config;

import com.easybytes.springsecuritybasic.filter.AuthoritiesLoggingAfterFilter;
import com.easybytes.springsecuritybasic.filter.CsrfCookieFilter;
import com.easybytes.springsecuritybasic.filter.RequestValidationBeforeFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)
public class ProjectSecurityConfig {




    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

       return http
               .securityContext(context -> context.requireExplicitSave(false))
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
               .cors(cors -> cors.configurationSource(request -> {
                   CorsConfiguration config = new CorsConfiguration();
                   config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                   config.setAllowedMethods(Collections.singletonList("*"));
                   config.setAllowCredentials(true);
                   config.setAllowedHeaders(Collections.singletonList("*"));
                   config.setMaxAge(3600L);
                   return config;
               }))
               .csrf(csrf ->
                               csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact","register")
                                       .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
               )
               .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
               .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
               .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
               .authorizeHttpRequests(requests ->
                requests.requestMatchers("/myAccount","/myBalance","/myLoans","/myCards","/user").authenticated()
                        .requestMatchers("/contact","/notices","/register").permitAll()
        )
               .formLogin(withDefaults())
               .httpBasic(withDefaults())
               .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(  );
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//
//        UserDetails admin = User.withUsername("admin")
//                .password("1234")
//                .authorities("admin")
//                .build();
//
//
//        UserDetails user = User.withUsername("user")
//                .password("1234")
//                .authorities("admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//

//    }

}
