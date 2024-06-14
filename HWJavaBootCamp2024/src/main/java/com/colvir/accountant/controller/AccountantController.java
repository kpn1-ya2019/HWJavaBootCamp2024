package com.colvir.accountant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountantController {
    @GetMapping("/test")
    public String test(){
      return "Hello! Accountant";
    }
}
