package com.colvir.accountant.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.colvir.accountant.dto.GeneratePmtOrderRequest;
import com.colvir.accountant.dto.GeneratePmtOrderResponse;
import com.colvir.accountant.dto.PaymentOrderResponse;
import com.colvir.accountant.dto.PmtOrderPageResponse;
import com.colvir.accountant.dto.UpdatePmtOrderRequest;
import com.colvir.accountant.exception.PmtOrderNotFoundException;
import com.colvir.accountant.mapper.PaymentOrderMapper;
import com.colvir.accountant.model.PaymentOrder;
import com.colvir.accountant.repository.PaymentOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentOrderService {

    private final PaymentOrderMapper paymentOrderMapper;

    private final PaymentOrderRepository paymentOrderRepository;


    public GeneratePmtOrderResponse generatePmtOrder(GeneratePmtOrderRequest request) {
        Integer   newId = paymentOrderRepository.generateIdPaymentOrder();
        Integer   idType = request.getIdType();
        Integer   idEmployee = request.getIdEmployee();
        Integer   idDepartment = request.getIdDepartment();
        LocalDate datePayment = request.getDatePayment();
        Double amount =  request.getAmount();
        PaymentOrder newPaymentOrder = new PaymentOrder(newId, idType, idDepartment, idEmployee, datePayment, amount);
        paymentOrderRepository.save(newPaymentOrder);
        return  paymentOrderMapper.pmtOrderToGeneratePmtOrderResponse(newPaymentOrder);
    }

    public PmtOrderPageResponse getAll() {
        List<PaymentOrder> allPaymentOrders = paymentOrderRepository.findAll();
        return paymentOrderMapper.pmtOrdersToPmtOrderPageResponse(allPaymentOrders);
    }

    public PaymentOrderResponse getById(Integer id) {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id)
                .orElseThrow(()-> new PmtOrderNotFoundException(String.format("%s с id = %s не найдена", "Выплата",id)));
        return paymentOrderMapper.pmtOrderToPmtOrderResponse(paymentOrder);
    }

    public PaymentOrderResponse update(UpdatePmtOrderRequest request) {
        Integer paymentOrderId = request.getId();
        PaymentOrder paymentOrder = paymentOrderRepository.findById(paymentOrderId)
                .orElseThrow(() -> new PmtOrderNotFoundException(String.format("%s с id = %s не найдена", "Выплата",paymentOrderId)));

        PaymentOrder updatedPmtOrder = paymentOrderMapper.updatePmtOrderRequestToPmtOrder(paymentOrder, request);

        paymentOrderRepository.update(updatedPmtOrder);

        return paymentOrderMapper.pmtOrderToPmtOrderResponse(updatedPmtOrder);
    }

    public PaymentOrderResponse delete(Integer id) {

        PaymentOrder paymentOrder = paymentOrderRepository.findById(id)
                .orElseThrow(() -> new PmtOrderNotFoundException(String.format("%s с id = %s не найдена", "Выплата",id)));

        paymentOrderRepository.delete(id);

        return paymentOrderMapper.pmtOrderToPmtOrderResponse(paymentOrder);
    }
}
