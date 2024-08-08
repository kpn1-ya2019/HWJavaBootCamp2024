package com.colvir.accountant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colvir.accountant.dto.GeneratePmtTypeRequest;
import com.colvir.accountant.dto.GeneratePmtTypeResponse;
import com.colvir.accountant.dto.PaymentTypeResponse;
import com.colvir.accountant.dto.PmtTypePageResponse;
import com.colvir.accountant.dto.UpdatePmtTypeRequest;
import com.colvir.accountant.exception.PmtTypeNotFoundException;
import com.colvir.accountant.mapper.PaymentTypeMapper;
import com.colvir.accountant.model.PaymentType;
import com.colvir.accountant.repository.PaymentTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTypeService {

    private final PaymentTypeMapper paymentTypeMapper;

    private final PaymentTypeRepository paymentTypeRepository;

    public GeneratePmtTypeResponse generatePmtType(GeneratePmtTypeRequest request) {
        String name = request.getName();
        Integer newId = paymentTypeRepository.generateIdPmtType();
        PaymentType newPaymentType = new PaymentType(newId, name);
        paymentTypeRepository.save(newPaymentType);
        return  paymentTypeMapper.pmtTypeToGeneratePmtTypeResponse(newPaymentType);
    }

    public PmtTypePageResponse getAll() {
        List<PaymentType> allPaymentTypes = paymentTypeRepository.findAll();
        return paymentTypeMapper.paymentTypesToPmtTypePageResponse(allPaymentTypes);
    }

    public PaymentTypeResponse getById(Integer id) {

        PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(()-> new PmtTypeNotFoundException(String.format("%s с id = %s не найден", "Тип выплаты",id)));
        return paymentTypeMapper.pmtTypeToPmtTypeResponse(paymentType);
    }

    public PaymentTypeResponse update(UpdatePmtTypeRequest request) {
        Integer paymentTypeId = request.getId();
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId)
                .orElseThrow(() -> new PmtTypeNotFoundException(String.format("%s с id = %s не найден", "Тип выплаты", paymentTypeId)));

        PaymentType updatedPmtType = paymentTypeMapper.updatePmtTypeRequestToPmtType(paymentType, request);

        paymentTypeRepository.update(updatedPmtType);

        return paymentTypeMapper.pmtTypeToPmtTypeResponse(updatedPmtType);
    }

    public PaymentTypeResponse delete(Integer id) {

        PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(() -> new PmtTypeNotFoundException(String.format("%s с id = %s не найден", "Тип выплаты", id)));

        paymentTypeRepository.delete(id);

        return paymentTypeMapper.pmtTypeToPmtTypeResponse(paymentType);
    }
}
