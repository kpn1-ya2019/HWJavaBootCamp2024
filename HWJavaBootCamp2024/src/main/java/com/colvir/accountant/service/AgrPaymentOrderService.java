package com.colvir.accountant.service;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.exception.AgrPmtOrderNotFoundException;
import com.colvir.accountant.mapper.AgrPaymentOrderMapper;
import com.colvir.accountant.model.AgrPaymentOrder;
import com.colvir.accountant.model.PaymentOrder;
import com.colvir.accountant.repository.*;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class AgrPaymentOrderService {

    private final AgrPaymentOrderMapper agrPaymentOrderMapper;

    private final AgrPaymentOrderRepository agrPaymentOrderRepository;

    private final PaymentOrderRepository paymentOrderRepository;

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    private final Random randomAgrPmtOrder = new Random();

    public GenerateAgrPmtOrderResponse generateAgrPmtOrder(GenerateAgrPmtOrderRequest request) {
        String  paymentTypeName = request.getPaymentTypeName();
        String  departmentCode = request.getDepartmentCode();
        String  departmentName= request.getDepartmentName();
        String  employeeSurname= request.getEmployeeSurname();
        String  employeeName= request.getEmployeeName();
        String  employeePatronymic= request.getEmployeePatronymic();
        Double  amountPaymentOrder= request.getAmountPaymentOrder();

        AgrPaymentOrder newAgrPaymentOrder = new AgrPaymentOrder(randomAgrPmtOrder.nextInt(),paymentTypeName,departmentCode,departmentName,employeeSurname,employeeName,employeePatronymic,amountPaymentOrder );
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

        //https://stackoverflow.com/questions/28342814/group-by-multiple-field-names-in-java-8
        //https://for-each.dev/lessons/b/-java-groupingby-collector

        List<PaymentOrder> paymentOrders =paymentOrderRepository.findAll();

        Map<Pair, Double> map=
                paymentOrders.stream()
                        .filter(
                                PaymentOrder-> PaymentOrder.getIdType() != null &&
                                        PaymentOrder.getIdDepartment() != null &&
                                        PaymentOrder.getIdEmployee() != null &&
                                        PaymentOrder.getDatePayment() != null &&
                                        ( ( PaymentOrder.getDatePayment().isAfter(dtFrom) || dtFrom.equals(PaymentOrder.getDatePayment() ))
                                          &&
                                          ( PaymentOrder.getDatePayment().isBefore(dtTo)  || dtTo.equals(PaymentOrder.getDatePayment()   ))
                                        )
                        )
                        .collect(groupingBy(paymentOrder -> new Pair(paymentOrder.getIdDepartment(), paymentOrder.getIdEmployee()),
                                 summingDouble(PaymentOrder::getAmount)));

        List<AgrPaymentOrder> calcAgrPaymentOrders =  new ArrayList<>();
        map.forEach((e, agrAmount)-> calcAgrPaymentOrders.add(
                                new AgrPaymentOrder(agrPaymentOrderRepository.generateIdAgrPaymentOrder(),
                                        "", departmentRepository.findById((Integer) e.getValue0()).get().getCode(),
                                              departmentRepository.findById((Integer) e.getValue0()).get().getName(),
                                              employeeRepository.findByIdAndIdDept((Integer) e.getValue1(), (Integer) e.getValue0()).get().getSurname(),
                                              employeeRepository.findByIdAndIdDept((Integer) e.getValue1(), (Integer) e.getValue0()).get().getName(),
                                              employeeRepository.findByIdAndIdDept((Integer) e.getValue1(), (Integer) e.getValue0()).get().getPatronymic(),
                                              agrAmount)));

        return agrPaymentOrderMapper.agrPmtOrdersToAgrPmtOrderPageResponse(calcAgrPaymentOrders);

    }
}
