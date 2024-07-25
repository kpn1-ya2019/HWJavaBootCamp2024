package com.colvir.accountant.service;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.exception.PmtOrderNotFoundException;
import com.colvir.accountant.mapper.PaymentOrderMapper;
import com.colvir.accountant.model.PaymentOrder;
import com.colvir.accountant.repository.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentOrderService {

    private final PaymentOrderMapper paymentOrderMapper;

    private final PaymentOrderRepository paymentOrderRepository;

    private final Random randomPmtOrder = new Random();

    public GeneratePmtOrderResponse generatePmtOrder(GeneratePmtOrderRequest request) {
        Integer   idType = request.getIdType();
        Integer   idEmployee = request.getIdEmployee();
        Integer   idDepartment = request.getIdDepartment();
        LocalDate datePayment = request.getDatePayment();
        Double amount =  request.getAmount();
        PaymentOrder newPaymentOrder = new PaymentOrder(randomPmtOrder.nextInt(), idType, idDepartment, idEmployee, datePayment, amount);
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
