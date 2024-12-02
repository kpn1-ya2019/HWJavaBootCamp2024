package com.colvir.accountant.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.colvir.accountant.dto.AgrPaymentOrderResponse;
import com.colvir.accountant.dto.AgrPmtOrderPageResponse;
import com.colvir.accountant.dto.GenerateAgrPmtOrderRequest;
import com.colvir.accountant.dto.GenerateAgrPmtOrderResponse;
import com.colvir.accountant.dto.UpdateAgrPmtOrderRequest;
import com.colvir.accountant.exception.AgrPmtOrderNotFoundException;
import com.colvir.accountant.mapper.AgrPaymentOrderMapper;
import com.colvir.accountant.model.AgrPaymentOrder;
import com.colvir.accountant.repository.AgrPaymentOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgrPaymentOrderService {

    private final AgrPaymentOrderMapper agrPaymentOrderMapper;

    private final AgrPaymentOrderRepository agrPaymentOrderRepository;

    public GenerateAgrPmtOrderResponse generateAgrPmtOrder(GenerateAgrPmtOrderRequest request) {
        String    paymentTypeName = request.getPaymentTypeName();
        String    departmentCode = request.getDepartmentCode();
        String    departmentName= request.getDepartmentName();
        String    employeeSurname= request.getEmployeeSurname();
        String    employeeName= request.getEmployeeName();
        String    employeePatronymic= request.getEmployeePatronymic();
        Double    amountPaymentOrder= request.getAmountPaymentOrder();

        AgrPaymentOrder newAgrPaymentOrder = new AgrPaymentOrder(paymentTypeName,departmentCode,departmentName,employeeSurname,employeeName,employeePatronymic,amountPaymentOrder );
        agrPaymentOrderRepository.save(newAgrPaymentOrder);
        return  agrPaymentOrderMapper.agrPmtOrderToGenerateAgrPmtOrderResponse(newAgrPaymentOrder);
    }

    public AgrPmtOrderPageResponse getAll() {
        List<AgrPaymentOrder> allAgrPaymentOrders = agrPaymentOrderRepository.findAll();
        return agrPaymentOrderMapper.agrPmtOrdersToAgrPmtOrderPageResponse(allAgrPaymentOrders);
    }

    public AgrPaymentOrderResponse getById(Integer id) {
        AgrPaymentOrder agrPaymentOrder = agrPaymentOrderRepository.findById(id)
                .orElseThrow(()-> new AgrPmtOrderNotFoundException(String.format("%s с id = %s не найден", "Агрегат выплаты",id)));
        return agrPaymentOrderMapper.agrPmtOrderToAgrPmtOrderResponse(agrPaymentOrder);
    }

    public AgrPaymentOrderResponse update(UpdateAgrPmtOrderRequest request) {
        Integer agrPaymentOrderId = request.getId();
        AgrPaymentOrder agrPaymentOrder = agrPaymentOrderRepository.findById(agrPaymentOrderId)
                .orElseThrow(() -> new AgrPmtOrderNotFoundException(String.format("%s с id = %s не найден", "Агрегат выплаты",agrPaymentOrderId)));

        AgrPaymentOrder updatedAgrPmtOrder = agrPaymentOrderMapper.updateAgrPmtOrderRequestToAgrPmtOrder(request);

        agrPaymentOrderRepository.update(updatedAgrPmtOrder);

        return agrPaymentOrderMapper.agrPmtOrderToAgrPmtOrderResponse(updatedAgrPmtOrder);
    }

    public AgrPaymentOrderResponse delete(Integer id) {

        AgrPaymentOrder agrPaymentOrder = agrPaymentOrderRepository.findById(id)
                .orElseThrow(() -> new AgrPmtOrderNotFoundException(String.format("%s с id = %s не найдена", "Агрегат выплаты",id)));

        agrPaymentOrderRepository.delete(id);

        return agrPaymentOrderMapper.agrPmtOrderToAgrPmtOrderResponse(agrPaymentOrder);
    }

    public AgrPmtOrderPageResponse getByPmtTypeName(String pmtTypeName) {
        List<AgrPaymentOrder> allAgrPaymentOrders = agrPaymentOrderRepository.findPmtTypeName(pmtTypeName);
        return agrPaymentOrderMapper.agrPmtOrdersToAgrPmtOrderPageResponse(allAgrPaymentOrders);
    }

    public AgrPmtOrderPageResponse calculate(LocalDate dtFrom, LocalDate dtTo) {

        List<AgrPaymentOrder> calcAgrPaymentOrders =   agrPaymentOrderRepository.calculate(dtFrom, dtTo);

        return agrPaymentOrderMapper.agrPmtOrdersToAgrPmtOrderPageResponse(calcAgrPaymentOrders);

    }
}
