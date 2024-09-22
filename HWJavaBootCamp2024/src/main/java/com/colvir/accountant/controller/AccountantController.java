package com.colvir.accountant.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colvir.accountant.dto.GenerateAccountantResponse;
import com.colvir.accountant.service.AccountantService;

import lombok.RequiredArgsConstructor;
=======
import com.colvir.accountant.dto.GenerateAccountantResponse;
import com.colvir.accountant.service.AccountantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> master

@RestController
@RequestMapping("accountant")
@RequiredArgsConstructor

public class AccountantController {
    private final AccountantService accountantService;

    @GetMapping("/test")
    public String test(){
      return "Hello! Accountant";
    }

<<<<<<< HEAD
    @GetMapping("fillTest/fillTest")
=======
    @GetMapping("/fillTest")
>>>>>>> master
    public GenerateAccountantResponse fillTest(){
        return accountantService.fillTest();
    }

}
