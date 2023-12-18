package com.easybytes.springsecuritybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
       return http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/myAccount","/myBalance","/myLoans","myCards").authenticated()
                        .requestMatchers("/contact","/notice","/register").permitAll()
        )
               .formLogin(withDefaults())
               .httpBasic(withDefaults())
               .csrf(AbstractHttpConfigurer::disable)
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
