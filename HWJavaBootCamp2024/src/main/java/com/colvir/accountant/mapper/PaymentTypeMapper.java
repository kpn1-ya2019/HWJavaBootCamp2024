package com.colvir.accountant.mapper;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.model.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PaymentTypeMapper {

    GeneratePmtTypeResponse pmtTypeToGeneratePmtTypeResponse(PaymentType paymentType);

    PaymentTypeResponse pmtTypeToPmtTypeResponse(PaymentType paymentType);

    List<PaymentTypeResponse> pmtTypesToPmtTypeResponse(List<PaymentType> paymentTypes);

    PaymentType updatePmtTypeRequestToPmtType(UpdatePmtTypeRequest request);

    default PmtTypePageResponse paymentTypesToPmtTypePageResponse(List<PaymentType> paymentTypes) {
        List<PaymentTypeResponse> paymentTypeResponses = pmtTypesToPmtTypeResponse(paymentTypes);
        return  new PmtTypePageResponse(paymentTypeResponses);
    }
}
