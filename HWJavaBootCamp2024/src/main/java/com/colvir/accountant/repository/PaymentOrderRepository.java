package com.colvir.accountant.repository;

import com.colvir.accountant.model.PaymentOrder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentOrderRepository {
    private final Set<PaymentOrder> paymentOrders = new HashSet<>();

    public PaymentOrder save(PaymentOrder paymentOrder) {
        paymentOrders.add(paymentOrder);
        return paymentOrder;
    }

    public List<PaymentOrder> findAll() {
        return new ArrayList<>(paymentOrders);
    }
    public Optional<PaymentOrder> findById(Long id) {
        return paymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(id))
                .findFirst();
    }

    public PaymentOrder update(PaymentOrder pmtForUpdate) {
        for (PaymentOrder paymentOrder : paymentOrders) {
            if (paymentOrder.getId().equals(pmtForUpdate.getId())) {
                paymentOrder.setIdType(pmtForUpdate.getIdType());
                paymentOrder.setIdEmployee(pmtForUpdate.getIdEmployee());
                paymentOrder.setDatePayment(pmtForUpdate.getDatePayment());
                paymentOrder.setAmount(pmtForUpdate.getAmount());
            }
        }
        return pmtForUpdate;
    }

    public PaymentOrder delete(Long id) {
        PaymentOrder pmtForDelete = paymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(id))
                .findFirst().get();
        paymentOrders.remove(pmtForDelete);
        return pmtForDelete;
    }

    public PaymentOrder getByTypeEmpDate(Long idType, Long idEmployee, Date datePayment) {
        return paymentOrders.stream()
                .filter(paymentOrder ->  ( ( idType != null) || (idEmployee != null) || (datePayment != null) ) &&
                                         (idType == null || paymentOrder.getIdType().equals(idType) ) &&
                                         (idEmployee == null || paymentOrder.getIdEmployee().equals(idEmployee) ) &&
                                         (datePayment == null || paymentOrder.getDatePayment().equals(datePayment) ) )
                .findFirst()
                .orElse(null);
    }
}
