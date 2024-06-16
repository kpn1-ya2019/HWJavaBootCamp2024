package com.colvir.accountant.mapper;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.model.PaymentOrder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PaymentOrderMapper {

    GeneratePmtOrderResponse pmtOrderToGeneratePmtOrderResponse(PaymentOrder paymentOrder);

    PaymentOrderResponse pmtOrderToPmtOrderResponse(PaymentOrder paymentOrder);

    List<PaymentOrderResponse> pmtOrdersToPmtOrderResponse(List<PaymentOrder> paymentOrders);

    PaymentOrder updatePmtOrderRequestToPmtOrder(UpdatePmtOrderRequest request);

    default PmtOrderPageResponse pmtOrdersToPmtOrderPageResponse(List<PaymentOrder> paymentOrders) {
        List<PaymentOrderResponse> pmtOrderResponses = pmtOrdersToPmtOrderResponse(paymentOrders);
        return  new PmtOrderPageResponse(pmtOrderResponses);
    }
}
