package com.colvir.accountant.controller;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.service.AgrPaymentOrderService;
import com.colvir.accountant.service.PaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("agrPaymentOrder")
@RequiredArgsConstructor
public class AgrPaymentOrderController {

    private  final AgrPaymentOrderService agrPaymentOrderService;

    @PostMapping("calculate")
    public GenerateAgrPmtOrderResponse generateAgrPaymentOrder(@RequestBody GenerateAgrPmtOrderRequest request) {
        return agrPaymentOrderService.generateAgrPmtOrder(request);
    }

    @GetMapping
    public AgrPmtOrderPageResponse getAll() {return  agrPaymentOrderService.getAll(); }

    @GetMapping("/{pmtTypeName}")
    public AgrPmtOrderPageResponse getByPmtTypeName(@PathVariable("pmtTypeName") String pmtTypeName) { return agrPaymentOrderService.getByPmtTypeName(pmtTypeName); }

    @GetMapping("/{id}")
    public AgrPaymentOrderResponse getById(@PathVariable("id") Long id) { return agrPaymentOrderService.getById(id); }

    @PutMapping
    public AgrPaymentOrderResponse update(@RequestBody UpdateAgrPmtOrderRequest request) {return agrPaymentOrderService.update(request); }

    @DeleteMapping("/id")
    public AgrPaymentOrderResponse delete(@RequestBody UpdateAgrPmtOrderRequest request) {return agrPaymentOrderService.update(request); }
}
