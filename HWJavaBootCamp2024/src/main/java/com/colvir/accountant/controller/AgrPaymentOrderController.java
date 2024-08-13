package com.colvir.accountant.controller;

<<<<<<< HEAD
import java.time.LocalDate;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colvir.accountant.dto.AgrPaymentOrderResponse;
import com.colvir.accountant.dto.AgrPmtOrderPageResponse;
import com.colvir.accountant.dto.GenerateAgrPmtOrderRequest;
import com.colvir.accountant.dto.GenerateAgrPmtOrderResponse;
import com.colvir.accountant.dto.UpdateAgrPmtOrderRequest;
import com.colvir.accountant.service.AgrPaymentOrderService;

import lombok.RequiredArgsConstructor;
=======
import com.colvir.accountant.dto.*;
import com.colvir.accountant.service.AgrPaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
>>>>>>> master

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

<<<<<<< HEAD
    @GetMapping("paymentTypeName/{pmtTypeName}")
    public AgrPmtOrderPageResponse getByPmtTypeName(@PathVariable("pmtTypeName") String pmtTypeName) { return agrPaymentOrderService.getByPmtTypeName(pmtTypeName); }

    @GetMapping("id/{id}")
=======
    @GetMapping("/{pmtTypeName}")
    public AgrPmtOrderPageResponse getByPmtTypeName(@PathVariable("pmtTypeName") String pmtTypeName) { return agrPaymentOrderService.getByPmtTypeName(pmtTypeName); }

    @GetMapping("/{id}")
>>>>>>> master
    public AgrPaymentOrderResponse getById(@PathVariable("id") Integer id) { return agrPaymentOrderService.getById(id); }

    @PutMapping
    public AgrPaymentOrderResponse update(@RequestBody UpdateAgrPmtOrderRequest request) {return agrPaymentOrderService.update(request); }

<<<<<<< HEAD
    @DeleteMapping("/{id}")
    public AgrPaymentOrderResponse delete(@PathVariable Integer id) {return agrPaymentOrderService.delete(id); }
=======
    @DeleteMapping("/id")
    public AgrPaymentOrderResponse delete(@RequestBody UpdateAgrPmtOrderRequest request) {return agrPaymentOrderService.update(request); }
>>>>>>> master
}
