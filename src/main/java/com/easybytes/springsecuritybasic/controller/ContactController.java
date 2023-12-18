package com.easybytes.springsecuritybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @GetMapping("/contact")
    public String getContact(){
        return "Inquiry  details Saved to the DB";
    }
}
