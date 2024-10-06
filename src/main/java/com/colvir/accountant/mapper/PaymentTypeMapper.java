package com.colvir.accountant.mapper;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.model.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PaymentTypeMapper {

    GeneratePmtTypeResponse pmtTypeToGeneratePmtTypeResponse(PaymentType paymentType);

    PaymentTypeResponse pmtTypeToPmtTypeResponse(PaymentType paymentType);

    List<PaymentTypeResponse> pmtTypesToPmtTypeResponse(List<PaymentType> paymentTypes);

    default PaymentType updatePmtTypeRequestToPmtType(@MappingTarget PaymentType pmtTypeForUpdate, UpdatePmtTypeRequest request)
    {
        String name = request.getName();
        if (name != null) {
            pmtTypeForUpdate.setName(name);
        }

        return pmtTypeForUpdate;

    }

    /* one of way used
    default PaymentType updatePmtTypeToPmtType(@MappingTarget PaymentType pmtTypeForUpdate, PaymentType pmtTypeRequest)
    {
        String name = pmtTypeRequest.getName();
        if (name != null) {
            pmtTypeForUpdate.setName(name);
        }

        return pmtTypeForUpdate;

    }
     */

    default PmtTypePageResponse paymentTypesToPmtTypePageResponse(List<PaymentType> paymentTypes) {
        List<PaymentTypeResponse> paymentTypeResponses = pmtTypesToPmtTypeResponse(paymentTypes);
        return  new PmtTypePageResponse(paymentTypeResponses);
    }

}
