package com.colvir.accountant.mapper;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.model.PaymentOrder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PaymentOrderMapper {

    GeneratePmtOrderResponse pmtOrderToGeneratePmtOrderResponse(PaymentOrder paymentOrder);

    PaymentOrderResponse pmtOrderToPmtOrderResponse(PaymentOrder paymentOrder);

    List<PaymentOrderResponse> pmtOrdersToPmtOrderResponse(List<PaymentOrder> paymentOrders);

    default PaymentOrder updatePmtOrderRequestToPmtOrder(@MappingTarget PaymentOrder paymentOrderForUpdate, UpdatePmtOrderRequest request){

        Integer   idType = request.getIdType();
        if (idType != null) {
            paymentOrderForUpdate.setIdType(idType);
        }

        Integer   idEmployee = request.getIdEmployee();
        if (idEmployee != null) {
            paymentOrderForUpdate.setIdEmployee(idEmployee);
        }

        Integer   idDepartment = request.getIdDepartment();
        if (idDepartment != null) {
            paymentOrderForUpdate.setIdDepartment(idDepartment);
        }
        LocalDate datePayment = request.getDatePayment();
        if (datePayment != null) {
            paymentOrderForUpdate.setDatePayment(datePayment);
        }
        Double amount = request.getAmount();
        if (amount != null) {
            paymentOrderForUpdate.setAmount(amount);
        }

        return paymentOrderForUpdate;

    }

    default PmtOrderPageResponse pmtOrdersToPmtOrderPageResponse(List<PaymentOrder> paymentOrders) {
        List<PaymentOrderResponse> pmtOrderResponses = pmtOrdersToPmtOrderResponse(paymentOrders);
        return  new PmtOrderPageResponse(pmtOrderResponses);
    }

}
