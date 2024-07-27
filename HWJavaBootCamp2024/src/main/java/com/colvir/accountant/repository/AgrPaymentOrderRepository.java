package com.colvir.accountant.repository;

import com.colvir.accountant.model.AgrPaymentOrder;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AgrPaymentOrderRepository {
    private final Set<AgrPaymentOrder> agrPaymentOrders = new HashSet<>();

    public AgrPaymentOrder save(AgrPaymentOrder agrPaymentOrder) {
        agrPaymentOrders.add(agrPaymentOrder);
        return agrPaymentOrder;
    }

    public List<AgrPaymentOrder> findAll() {
        return new ArrayList<>(agrPaymentOrders);
    }
    public Optional<AgrPaymentOrder> findById(Integer id) {
        return agrPaymentOrders.stream()
                .filter(agrPaymentOrder -> agrPaymentOrder.getId().equals(id))
                .findFirst();
    }
    public List<AgrPaymentOrder>  findPmtTypeName(String pmtTypeName) {

        return new ArrayList<>(agrPaymentOrders.stream()
                .filter(agrPaymentOrder -> agrPaymentOrder.getPaymentTypeName().equals(pmtTypeName))
                .collect(Collectors.toSet()));
    }
    public AgrPaymentOrder update(AgrPaymentOrder pmtForUpdate) {
        for (AgrPaymentOrder agrPaymentOrder : agrPaymentOrders) {
            if (agrPaymentOrder.getId().equals(pmtForUpdate.getId())) {
                agrPaymentOrder.setPaymentTypeName(pmtForUpdate.getPaymentTypeName());
                agrPaymentOrder.setDepartmentCode(pmtForUpdate.getDepartmentCode());
                agrPaymentOrder.setDepartmentName(pmtForUpdate.getDepartmentName());
                agrPaymentOrder.setEmployeeSurname(pmtForUpdate.getEmployeeSurname());
                agrPaymentOrder.setEmployeeName(pmtForUpdate.getEmployeeName());
                agrPaymentOrder.setEmployeePatronymic(pmtForUpdate.getEmployeePatronymic());
            }
        }
        return pmtForUpdate;
    }

    public AgrPaymentOrder delete(Integer id) {
        AgrPaymentOrder pmtForDelete = agrPaymentOrders.stream()
                .filter(agrPaymentOrder -> agrPaymentOrder.getId().equals(id))
                .findFirst().get();
        agrPaymentOrders.remove(pmtForDelete);
        return pmtForDelete;
    }
    public Integer generateIdAgrPaymentOrder() {
        Random randomId = new Random();
        return randomId.nextInt();
    }


}
