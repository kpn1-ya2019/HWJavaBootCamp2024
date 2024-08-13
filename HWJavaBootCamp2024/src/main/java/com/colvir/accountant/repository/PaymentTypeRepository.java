package com.colvir.accountant.repository;

import com.colvir.accountant.model.PaymentType;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentTypeRepository {
    private final Set<PaymentType> paymentTypes = new HashSet<>();

    public  Integer generateIdPmtType() {
        //Генерация в репо https://habr.com/ru/articles/709848/
        Random randomPmtType = new Random();
        return randomPmtType.nextInt();
    }

    public PaymentType save(PaymentType paymentType) {
        paymentTypes.add(paymentType);
        return paymentType;
    }

    public List<PaymentType> findAll() {
        return new ArrayList<>(paymentTypes);
    }
    public Optional<PaymentType> findById(Integer id) {
        return paymentTypes.stream()
                .filter(paymentType -> paymentType.getId().equals(id))
                .findFirst();
    }

    public PaymentType update(PaymentType pmtForUpdate) {
        for (PaymentType paymentType : paymentTypes) {
            if (paymentType.getId().equals(pmtForUpdate.getId())) {
                paymentType.setName(pmtForUpdate.getName());
            }
        }
        return pmtForUpdate;
    }

    public PaymentType delete(Integer id) {
        PaymentType pmtForDelete = paymentTypes.stream()
                .filter(paymentType -> paymentType.getId().equals(id))
                .findFirst().get();
        paymentTypes.remove(pmtForDelete);
        return pmtForDelete;
    }
    public PaymentType getByName(String pmtTypeName) {
        return paymentTypes.stream()
                .filter(paymentType -> paymentType.getName().equals(pmtTypeName))
                .findFirst()
                .orElse(null);
    }
    public PaymentType generateNewPaymentType(String pmtTypeName) {
        PaymentType fndPaymentType =   getByName(pmtTypeName);
        if (fndPaymentType == null) {
            Integer newId = generateIdPmtType();
            PaymentType newPaymentType = new PaymentType(newId, pmtTypeName);
            return save(newPaymentType);
        } else {
            return fndPaymentType;
        }
    }

    public String getName(PaymentType paymentType) {
        return paymentType.getName();
    }
    public PaymentType getById(Integer idPmtType) {
        return paymentTypes.stream()
                .filter(paymentType -> paymentType.getId().equals(idPmtType))
                .findFirst()
                .orElse(null);
    }
}
