//package com.easybytes.springsecuritybasic.config;
//
//import com.easybytes.springsecuritybasic.model.Customer;
//import com.easybytes.springsecuritybasic.repository.CustomerRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class EasyBankUserDetails implements UserDetailsService {
//
//    private final CustomerRepository customerRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        String userName,password = null;
//        List<GrantedAuthority> authorities = null;
//        List<Customer> customers = customerRepository.findByEmail(username);
//        if (customers.isEmpty()){
//            throw new UsernameNotFoundException("찾을 수 없는 사용자입니다. : "+username);
//        }
//
//            userName = customers.get(0).getEmail();
//            password = customers.get(0).getPwd();
//            authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
//
//
//        return new User(username,password,authorities);
//    }
//}
