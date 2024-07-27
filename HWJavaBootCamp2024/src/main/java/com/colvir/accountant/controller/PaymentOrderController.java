package com.colvir.accountant.controller;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.service.PaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("paymentOrder")
@RequiredArgsConstructor
public class PaymentOrderController {

    private  final PaymentOrderService paymentOrderService;

    @PostMapping("generate")
    public GeneratePmtOrderResponse generatePaymentOrder(@RequestBody GeneratePmtOrderRequest request) {
        return paymentOrderService.generatePmtOrder(request);
    }

    @GetMapping
    public PmtOrderPageResponse getAll() {return  paymentOrderService.getAll(); }

    @GetMapping("/{id}")
    public PaymentOrderResponse getById(@PathVariable("id") Integer id) { return paymentOrderService.getById(id); }

    @PutMapping
    public PaymentOrderResponse update(@RequestBody UpdatePmtOrderRequest request) {return paymentOrderService.update(request); }

    @DeleteMapping("/{id}")
    public PaymentOrderResponse delete(@PathVariable Integer id) {return paymentOrderService.delete(id); }
}
