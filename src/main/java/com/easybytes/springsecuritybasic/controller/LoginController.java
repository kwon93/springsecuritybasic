package com.easybytes.springsecuritybasic.controller;

import com.easybytes.springsecuritybasic.model.Customer;
import com.easybytes.springsecuritybasic.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer){
        Customer savedCustomer = null;
        ResponseEntity response = null;

        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0){
                response = ResponseEntity.status(HttpStatus.CREATED).body("요청한 정보로 회원가입이 완료되었습니다.");
            }
        }catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다. : "+e.getMessage());
        }

        return response;

    }


}
