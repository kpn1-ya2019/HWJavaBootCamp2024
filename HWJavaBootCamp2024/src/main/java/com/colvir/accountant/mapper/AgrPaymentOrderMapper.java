package com.colvir.accountant.mapper;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.model.AgrPaymentOrder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AgrPaymentOrderMapper {

    GenerateAgrPmtOrderResponse agrPmtOrderToGenerateAgrPmtOrderResponse(AgrPaymentOrder agrPaymentOrder);

    AgrPaymentOrderResponse agrPmtOrderToAgrPmtOrderResponse(AgrPaymentOrder agrPaymentOrder);

    List<AgrPaymentOrderResponse> agrPmtOrdersToAgrPmtOrderResponse(List<AgrPaymentOrder> agrPaymentOrders);

    AgrPaymentOrder updateAgrPmtOrderRequestToAgrPmtOrder(UpdateAgrPmtOrderRequest request);

    default AgrPmtOrderPageResponse agrPmtOrdersToAgrPmtOrderPageResponse(List<AgrPaymentOrder> agrPaymentOrders) {
        List<AgrPaymentOrderResponse> agrPmtOrderResponses = agrPmtOrdersToAgrPmtOrderResponse(agrPaymentOrders);
        return  new AgrPmtOrderPageResponse(agrPmtOrderResponses);
    }

}
