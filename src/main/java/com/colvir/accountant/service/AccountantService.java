package com.colvir.accountant.service;

import com.colvir.accountant.dto.GenerateAccountantResponse;
import com.colvir.accountant.exception.PmtOrderNotFoundException;
import com.colvir.accountant.mapper.AccountantMapper;
import com.colvir.accountant.model.Employee;
import com.colvir.accountant.model.PaymentOrder;
import com.colvir.accountant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountantService {

    private final AccountantMapper accountantMapper;

    private final PaymentTypeRepository paymentTypeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final PaymentOrderRepository paymentOrderRepository;


    private enum TypeAction {
        UPDATE_LIST_PAYMENT_ORDER,
        INSERT_LIST_PAYMENT_ORDER

    }

    private void oneListPaymentOrder(List<PaymentOrder> paymentOrders, Integer idPaymentType, Double prcPayment, String payDate, TypeAction typeAction) {
        if (payDate == null){
            throw new PmtOrderNotFoundException("Дата выплаты пуста (payDate = null)");
        }
        if (prcPayment == null || prcPayment <= 0.0 ){
            throw new PmtOrderNotFoundException(String.format("Коэффициент выплаты некорректен (prcPayment = %s) ", prcPayment));
        }
        switch (typeAction){
            case UPDATE_LIST_PAYMENT_ORDER : {
                for (PaymentOrder paymentOrder: paymentOrders) {
                    paymentOrder.setDatePayment(paymentOrderRepository.payStrToDate(payDate));
                    paymentOrder.setAmount(paymentOrder.getAmount() * prcPayment);
                }
                break;
            }
            case INSERT_LIST_PAYMENT_ORDER : {
                if (idPaymentType == null){
                    throw new PmtOrderNotFoundException("Тип выплаты пуст (idPaymentType = null)");
                }
                for (PaymentOrder paymentOrder: paymentOrders) {
                    paymentOrderRepository
                            .generateNewPaymentOrder(
                                    idPaymentType,
                                    paymentOrder.getIdDepartment(),
                                    paymentOrder.getIdEmployee(),
                                    paymentOrderRepository.payStrToDate(payDate),
                                    paymentOrder.getAmount() * prcPayment
                                    );
                }
                break;
            }
            default:{
                throw new PmtOrderNotFoundException(String.format("Не известный тип действия с листом выплат. typeAction = %s не найден", typeAction));
            }
        }
    }
    public GenerateAccountantResponse fillTest() {
        try {
            //Типы Выплат
            Integer idAdvancePaymentType = paymentTypeRepository.generateNewPaymentType("Аванс").getId();
            Integer idPremPaymentType = paymentTypeRepository.generateNewPaymentType("Премия").getId();
            Integer idSalaryPaymentType = paymentTypeRepository.generateNewPaymentType("Зарплата").getId();
            //Отделы
            Integer idManagmentDept = departmentRepository.generateNewDepartment("001", "Руководство").getId();
            Integer idHRDept = departmentRepository.generateNewDepartment("002", "Отдет кадров").getId();
            Integer idAccDept = departmentRepository.generateNewDepartment("003", "Бухгалтерия").getId();
            Integer idManufDept = departmentRepository.generateNewDepartment("004", "Отдел по производству шелковых простыней без каких-либо изображений").getId();

            //Сотрудники
            Employee empIvanov = employeeRepository.generateNewEmployee(idManagmentDept, "Ivanov", "Ivanovich", "Ivan", 9123.67);
            Employee empPetrov = employeeRepository.generateNewEmployee(idHRDept, "Petrov", "Petrovich", "Petr", 7654.32);
            Employee empSidorov = employeeRepository.generateNewEmployee(idAccDept, "Sidorov", "Sidorovich", "Sidr", 6543.98);
            Employee empDmitriev = employeeRepository.generateNewEmployee(idManufDept, "Dmitriev", "Dmitrievich", "Dmitry", 5432.76);
            Employee empSidorov2 = employeeRepository.generateNewEmployee(idManufDept, "Sidorov", "Sidorovich", "Sidr", 4323.54);


            //Выплаты
            List<PaymentOrder> paymentOrders = new ArrayList<>();
            paymentOrders.add(paymentOrderRepository
                                    .generateNewPaymentOrder(idAdvancePaymentType, idManagmentDept, empIvanov.getId(),
                                    null,
                                            empIvanov.getSalary() ));

            paymentOrders.add(paymentOrderRepository
                                    .generateNewPaymentOrder(idAdvancePaymentType, idHRDept, empPetrov.getId(),
                                      null,
                                            empPetrov.getSalary() ));

            paymentOrders.add(paymentOrderRepository
                                    .generateNewPaymentOrder(idAdvancePaymentType, idAccDept, empSidorov.getId(),
                                    null,
                                            empSidorov.getSalary() ));

            paymentOrders.add(paymentOrderRepository
                                    .generateNewPaymentOrder(idAdvancePaymentType, idManufDept, empDmitriev.getId(),
                            null,
                                            empDmitriev.getSalary() ));

            paymentOrders.add(paymentOrderRepository
                                    .generateNewPaymentOrder(idAdvancePaymentType, idManufDept, empSidorov2.getId(),
                            null,
                                             empSidorov2.getSalary() ));

            //Аванс
            Double prcAdvance = 0.1;
            String payAdvanceDate = "2024-05-10";
            oneListPaymentOrder(paymentOrders, null, prcAdvance, payAdvanceDate, TypeAction.UPDATE_LIST_PAYMENT_ORDER);

            //Премия
            Double prcPrem = 0.15;
            String payPremDate = "2024-05-20";
            oneListPaymentOrder(paymentOrders, idPremPaymentType, prcPrem, payPremDate, TypeAction.INSERT_LIST_PAYMENT_ORDER);

            Double prcSalary = 1.00;
            String paySalaryDate = "2024-05-20";
            oneListPaymentOrder(paymentOrders, idSalaryPaymentType, prcSalary, paySalaryDate, TypeAction.INSERT_LIST_PAYMENT_ORDER);

            String response =  "Dear Accountant! The test data is fill! Have a nice day!";
            return accountantMapper.accountantToGenerateAccResponse(response);

        } catch (Exception e) {
            String response = "Dear Accountant! The test data is not fill! Look:" + e.getMessage();
            return accountantMapper.accountantToGenerateAccResponse(response);
        }
    }
}
