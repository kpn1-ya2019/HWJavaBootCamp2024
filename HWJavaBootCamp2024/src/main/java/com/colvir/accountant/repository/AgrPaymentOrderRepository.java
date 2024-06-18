package com.colvir.accountant.repository;

import com.colvir.accountant.model.AgrPaymentOrder;
import org.springframework.stereotype.Repository;

import java.util.*;

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
    public Optional<AgrPaymentOrder> findById(Long id) {
        return agrPaymentOrders.stream()
                .filter(agrPaymentOrder -> agrPaymentOrder.getId().equals(id))
                .findFirst();
    }
    public List<AgrPaymentOrder>  findPmtTypeName(String pmtTypeName) {
        return new ArrayList<>(agrPaymentOrders);
        //todo доделать фильтр по коллекции - вернуть агрегать с фильтром по типу выплаты
        /*
        return new ArrayList<>(agrPaymentOrders.stream()
                                  .filter(agrPaymentOrder -> agrPaymentOrder.getPaymentTypeName().equals(pmtTypeName))
                                  .findFirst());
*/
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

    public AgrPaymentOrder delete(Long id) {
        AgrPaymentOrder pmtForDelete = agrPaymentOrders.stream()
                .filter(agrPaymentOrder -> agrPaymentOrder.getId().equals(id))
                .findFirst().get();
        agrPaymentOrders.remove(pmtForDelete);
        return pmtForDelete;
    }

}
