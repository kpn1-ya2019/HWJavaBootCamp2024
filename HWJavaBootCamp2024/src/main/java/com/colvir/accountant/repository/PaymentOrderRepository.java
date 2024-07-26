package com.colvir.accountant.repository;

import com.colvir.accountant.model.PaymentOrder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public Optional<PaymentOrder> findById(Integer id) {
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

    public PaymentOrder delete(Integer id) {
        PaymentOrder pmtForDelete = paymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(id))
                .findFirst().get();
        paymentOrders.remove(pmtForDelete);
        return pmtForDelete;
    }

    public PaymentOrder getByAllFld( Integer   pmtOrderIdType,
                                     Integer   pmtOrderIdDepartment,
                                     Integer   pmtOrderIdEmployee,
                                     LocalDate   pmtOrderDatePayment,
                                     Double pmtOrderAmount) {
        return paymentOrders.stream()
                .filter(paymentOrder ->  (
                        paymentOrder.getIdType().equals(pmtOrderIdType)            &&
                        paymentOrder.getIdDepartment().equals(pmtOrderIdDepartment) &&
                        paymentOrder.getIdEmployee().equals(pmtOrderIdEmployee) )  &&
                        paymentOrder.getDatePayment().equals(pmtOrderDatePayment)  &&
                        String.format("%.2f",paymentOrder.getAmount()) == String.format("%.2f",pmtOrderAmount)
                )
                .findFirst()
                .orElse(null);
    }

    public  Integer generateIdPaymentOrder() {
        Random randomPaymentOrder = new Random();
        return randomPaymentOrder.nextInt();
    }
    public PaymentOrder generateNewPaymentOrder( Integer   pmtOrderIdType,
                                                 Integer   pmtOrderIdDepartment,
                                                 Integer   pmtOrderIdEmployee,
                                                 LocalDate   pmtOrderDatePayment,
                                                 Double pmtOrderAmount
                                                 ) {

        PaymentOrder fndPaymentOrder=   getByAllFld(pmtOrderIdType, pmtOrderIdDepartment, pmtOrderIdEmployee, pmtOrderDatePayment, pmtOrderAmount);
        if (fndPaymentOrder == null) {
            Integer newId = generateIdPaymentOrder();
            PaymentOrder newPaymentOrder = new PaymentOrder(newId, pmtOrderIdType, pmtOrderIdDepartment, pmtOrderIdEmployee, pmtOrderDatePayment, pmtOrderAmount);
            return save(newPaymentOrder);
        } else {
            return fndPaymentOrder;
        }
    }

    public LocalDate payStrToDate(String stringPayDate){
        LocalDate parsingDate ;
        try {
            parsingDate = LocalDate.parse(stringPayDate);
            System.out.println(parsingDate);
        }catch (DateTimeParseException e) {
            parsingDate = null;
            System.out.println("Нераспаршена с помощью " + e.getParsedString());
            System.out.println("Ошибка " + e.getMessage());
        }
        return parsingDate;
    }

}
