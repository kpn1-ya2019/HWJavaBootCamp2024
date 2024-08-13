package com.colvir.accountant.controller;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("paymentType")
@RequiredArgsConstructor
public class PaymentTypeController {

    private  final PaymentTypeService paymentTypeService;

    @PostMapping("generate")
    public GeneratePmtTypeResponse generatePaymentType(@RequestBody GeneratePmtTypeRequest request) {
        return paymentTypeService.generatePmtType(request);
    }

    @GetMapping
    public PmtTypePageResponse getAll() {return  paymentTypeService.getAll(); }

    @GetMapping("/{id}")
    public PaymentTypeResponse getById(@PathVariable("id") Integer id) { return paymentTypeService.getById(id); }

    @PutMapping
    public PaymentTypeResponse update(@RequestBody UpdatePmtTypeRequest request) {return paymentTypeService.update(request); }

<<<<<<< HEAD
    @DeleteMapping("/{id}")
    public PaymentTypeResponse delete(@PathVariable Integer id) {return paymentTypeService.delete(id); }
=======
    @DeleteMapping("/id")
    public PaymentTypeResponse delete(@RequestBody UpdatePmtTypeRequest request) {return paymentTypeService.update(request); }
>>>>>>> master
}
