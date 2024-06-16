package com.colvir.accountant.service;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.exception.PmtTypeNotFoundException;
import com.colvir.accountant.mapper.PaymentTypeMapper;
import com.colvir.accountant.model.PaymentType;
import com.colvir.accountant.repository.PaymentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentTypeService {

    private final PaymentTypeMapper paymentTypeMapper;

    private final PaymentTypeRepository paymentTypeRepository;

    private Random randomPmtType = new Random();

    public GeneratePmtTypeResponse generatePmtType(GeneratePmtTypeRequest request) {
        String name = request.getName();
        PaymentType newPaymentType = new PaymentType(randomPmtType.nextLong(), name);
        paymentTypeRepository.save(newPaymentType);
        return  paymentTypeMapper.pmtTypeToGeneratePmtTypeResponse(newPaymentType);
    }

    public PmtTypePageResponse getAll() {
        List<PaymentType> allPaymentTypes = paymentTypeRepository.findAll();
        return paymentTypeMapper.paymentTypesToPmtTypePageResponse(allPaymentTypes);
    }

    public PaymentTypeResponse getById(Long id) {
        PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(()-> new PmtTypeNotFoundException(String.format("%s с id = %s не найден", "Тип выплаты",id)));
        return paymentTypeMapper.pmtTypeToPmtTypeResponse(paymentType);
    }

    public PaymentTypeResponse update(UpdatePmtTypeRequest request) {
        Long paymentTypeId = request.getId();
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId)
                .orElseThrow(() -> new PmtTypeNotFoundException(String.format("%s с id = %s не найден", "Тип выплаты", paymentTypeId)));

        PaymentType updatedPmtType = paymentTypeMapper.updatePmtTypeRequestToPmtType(request);

        paymentTypeRepository.update(updatedPmtType);

        return paymentTypeMapper.pmtTypeToPmtTypeResponse(updatedPmtType);
    }

    public PaymentTypeResponse delete(Long id) {

        PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(() -> new PmtTypeNotFoundException(String.format("%s с id = %s не найден", "Тип выплаты", id)));

        paymentTypeRepository.delete(id);

        return paymentTypeMapper.pmtTypeToPmtTypeResponse(paymentType);
    }
}
