package com.colvir.accountant.repository;

import com.colvir.accountant.model.PaymentType;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentTypeRepository {
    private final Set<PaymentType> paymentTypes = new HashSet<>();

    public PaymentType save(PaymentType paymentType) {
        paymentTypes.add(paymentType);
        return paymentType;
    }

    public List<PaymentType> findAll() {
        return new ArrayList<>(paymentTypes);
    }

    public Optional<PaymentType> findById(Long id) {
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

    public PaymentType delete(Long id) {
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
}
