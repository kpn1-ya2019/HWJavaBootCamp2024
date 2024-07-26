package com.colvir.accountant.mapper;

import com.colvir.accountant.dto.GenerateAccountantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AccountantMapper {
    GenerateAccountantResponse accountantToGenerateAccResponse(String response);
}
