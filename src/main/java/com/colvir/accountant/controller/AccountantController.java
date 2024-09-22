package com.colvir.accountant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colvir.accountant.dto.GenerateAccountantResponse;
import com.colvir.accountant.service.AccountantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("accountant")
@RequiredArgsConstructor

public class AccountantController {
    private final AccountantService accountantService;

    @GetMapping("/test")
    public String test(){
      return "Hello! Accountant";
    }

    @GetMapping("fillTest/fillTest")
    public GenerateAccountantResponse fillTest(){
        return accountantService.fillTest();
    }

}
