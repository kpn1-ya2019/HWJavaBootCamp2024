package com.colvir.accountant.controller;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.service.AgrPaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("agrPaymentOrder")
@RequiredArgsConstructor
public class AgrPaymentOrderController {

    @PostMapping("calculate/{dtFrom}, {dtTo}")
    public AgrPmtOrderPageResponse calculate(@PathVariable("dtFrom") LocalDate dtFrom, @PathVariable("dtTo") LocalDate dtTo) {
        return  agrPaymentOrderService.calculate( dtFrom, dtTo);
    }

    private  final AgrPaymentOrderService agrPaymentOrderService;

    @PostMapping("generate")
    public GenerateAgrPmtOrderResponse generateAgrPaymentOrder(@RequestBody GenerateAgrPmtOrderRequest request) {
        return agrPaymentOrderService.generateAgrPmtOrder(request);
    }



    @GetMapping
    public AgrPmtOrderPageResponse getAll() {return  agrPaymentOrderService.getAll(); }

    @GetMapping("/{pmtTypeName}")
    public AgrPmtOrderPageResponse getByPmtTypeName(@PathVariable("pmtTypeName") String pmtTypeName) { return agrPaymentOrderService.getByPmtTypeName(pmtTypeName); }

    @GetMapping("/{id}")
    public AgrPaymentOrderResponse getById(@PathVariable("id") Integer id) { return agrPaymentOrderService.getById(id); }

    @PutMapping
    public AgrPaymentOrderResponse update(@RequestBody UpdateAgrPmtOrderRequest request) {return agrPaymentOrderService.update(request); }

    @DeleteMapping("/id")
    public AgrPaymentOrderResponse delete(@RequestBody UpdateAgrPmtOrderRequest request) {return agrPaymentOrderService.update(request); }
}
