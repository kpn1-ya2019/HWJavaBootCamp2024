package com.colvir.accountant.controller;

import com.colvir.accountant.dto.GenerateAccountantResponse;
import com.colvir.accountant.service.AccountantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accountant")
@RequiredArgsConstructor

public class AccountantController {
    private final AccountantService accountantService;

    @GetMapping("/test")
    public String test(){
      return "Hello! Accountant";
    }

    @GetMapping("/fillTest")
    public GenerateAccountantResponse fillTest(){
        return accountantService.fillTest();
    }

}
