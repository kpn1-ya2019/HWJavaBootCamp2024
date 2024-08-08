package com.colvir.accountant.service;

import com.colvir.accountant.config.TestConfig;
import com.colvir.accountant.dto.*;
import com.colvir.accountant.mapper.AgrPaymentOrderMapper;
import com.colvir.accountant.model.AgrPaymentOrder;
import com.colvir.accountant.model.Department;
import com.colvir.accountant.model.Employee;
import com.colvir.accountant.model.PaymentOrder;
import com.colvir.accountant.repository.AgrPaymentOrderRepository;
import com.colvir.accountant.repository.DepartmentRepository;
import com.colvir.accountant.repository.EmployeeRepository;
import com.colvir.accountant.repository.PaymentOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AgrPaymentOrderService.class,
        AgrPaymentOrderMapper.class
})
@SpringBootTest(classes = {TestConfig.class})
class AgrPaymentOrderServiceTest {

    @Autowired
    private AgrPaymentOrderService agrPaymentOrderService;

    @MockBean
    private AgrPaymentOrderRepository agrPaymentOrderRepository;

    @MockBean
    private  PaymentOrderRepository paymentOrderRepository;
    @MockBean
    private  DepartmentRepository departmentRepository;
    @MockBean
    private  EmployeeRepository employeeRepository;

    @Test
    void generateAgrPmtOrder_success() {
        //Подготовка входных данных
        GenerateAgrPmtOrderRequest request = new GenerateAgrPmtOrderRequest();
        request.setPaymentTypeName("Type1");
        request.setDepartmentCode("001");
        request.setDepartmentName("Dept1");
        request.setEmployeeSurname("Ivanov");
        request.setEmployeeName("Ivan");
        request.setEmployeePatronymic("Ivanovich");
        request.setAmountPaymentOrder(1.23);
        AgrPaymentOrder newAgrPaymentOrder1 = new AgrPaymentOrder(123,"Type1", "001", "Dept1", "Ivanov", "Ivan", "Ivanovich", 1.23);

        //Подготовка ожидаемого результата
        GenerateAgrPmtOrderResponse expectedResponse = new GenerateAgrPmtOrderResponse();
        expectedResponse.setPaymentTypeName("Type1");
        expectedResponse.setDepartmentCode("001");
        expectedResponse.setDepartmentName("Dept1");
        expectedResponse.setEmployeeSurname("Ivanov");
        expectedResponse.setEmployeeName("Ivan");
        expectedResponse.setEmployeePatronymic("Ivanovich");
        expectedResponse.setAmountPaymentOrder(1.23);

        //Начало теста

        when(agrPaymentOrderRepository.generateIdAgrPaymentOrder()).thenReturn(123);
        when(agrPaymentOrderRepository.save(any())).thenReturn(newAgrPaymentOrder1);

        GenerateAgrPmtOrderResponse actualResponse = agrPaymentOrderService.generateAgrPmtOrder(request);
        assertEquals(expectedResponse, actualResponse);
        verify(agrPaymentOrderRepository).save(any());
        verify(agrPaymentOrderRepository).generateIdAgrPaymentOrder();
        verifyNoMoreInteractions(agrPaymentOrderRepository);
    }

    @Test
    void getAll() {
        //Подготовка входных данных
        AgrPaymentOrder newAgrPaymentOrder1 = new AgrPaymentOrder(null,"Type1", "001", "Dept1", "Ivanov", "Ivan", "Ivanovich", 1.23);
        AgrPaymentOrder newAgrPaymentOrder2 = new AgrPaymentOrder(null,"Type2", "002", "Dept2", "Petrov", "Petr", "Petrovich", 4.56);
        List<AgrPaymentOrder> allAgrPaymentOrders =  new ArrayList<>();
        allAgrPaymentOrders.add(newAgrPaymentOrder1);
        allAgrPaymentOrders.add(newAgrPaymentOrder2);

        //Подготовка ожидаемого результата
        AgrPaymentOrderResponse AgrPmtOrderResponse1 = new AgrPaymentOrderResponse();
        AgrPmtOrderResponse1.setPaymentTypeName("Type1");
        AgrPmtOrderResponse1.setDepartmentCode("001");
        AgrPmtOrderResponse1.setDepartmentName("Dept1");
        AgrPmtOrderResponse1.setEmployeeSurname("Ivanov");
        AgrPmtOrderResponse1.setEmployeeName("Ivan");
        AgrPmtOrderResponse1.setEmployeePatronymic("Ivanovich");
        AgrPmtOrderResponse1.setAmountPaymentOrder(1.23);

        AgrPaymentOrderResponse AgrPmtOrderResponse2 = new AgrPaymentOrderResponse();
        AgrPmtOrderResponse2.setPaymentTypeName("Type2");
        AgrPmtOrderResponse2.setDepartmentCode("002");
        AgrPmtOrderResponse2.setDepartmentName("Dept2");
        AgrPmtOrderResponse2.setEmployeeSurname("Petrov");
        AgrPmtOrderResponse2.setEmployeeName("Petr");
        AgrPmtOrderResponse2.setEmployeePatronymic("Petrovich");
        AgrPmtOrderResponse2.setAmountPaymentOrder(4.56);

        List<AgrPaymentOrderResponse> allAgrPmtOrdersResponse = new ArrayList<>();
        allAgrPmtOrdersResponse.add(AgrPmtOrderResponse1);
        allAgrPmtOrdersResponse.add(AgrPmtOrderResponse2);
        AgrPmtOrderPageResponse expectedResponse = new AgrPmtOrderPageResponse(allAgrPmtOrdersResponse);

        //Начало теста

        when(agrPaymentOrderRepository.findAll()).thenReturn(allAgrPaymentOrders);
        AgrPmtOrderPageResponse actualPageResponse = agrPaymentOrderService.getAll();

        assertEquals(expectedResponse, actualPageResponse);
        verify(agrPaymentOrderRepository, times(1)).findAll();
        verifyNoMoreInteractions(agrPaymentOrderRepository);
    }

    @Test
    void getById() {
        //Подготовка входных данных
        AgrPaymentOrder newAgrPaymentOrder1 = new AgrPaymentOrder(123,"Type1", "001", "Dept1", "Ivanov", "Ivan", "Ivanovich", 1.23);
        AgrPaymentOrder newAgrPaymentOrder2 = new AgrPaymentOrder(456,"Type2", "002", "Dept2", "Petrov", "Petr", "Petrovich", 4.56);
        List<AgrPaymentOrder> allAgrPaymentOrders =  new ArrayList<>();
        allAgrPaymentOrders.add(newAgrPaymentOrder1);
        allAgrPaymentOrders.add(newAgrPaymentOrder2);
        Optional<AgrPaymentOrder> optionalAgrPaymentOrder = allAgrPaymentOrders.stream()
                .filter(AgrPaymentOrder -> AgrPaymentOrder.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        AgrPaymentOrderResponse expectedResponse = new AgrPaymentOrderResponse();
        expectedResponse.setId(123);
        expectedResponse.setPaymentTypeName("Type1");
        expectedResponse.setDepartmentCode("001");
        expectedResponse.setDepartmentName("Dept1");
        expectedResponse.setEmployeeSurname("Ivanov");
        expectedResponse.setEmployeeName("Ivan");
        expectedResponse.setEmployeePatronymic("Ivanovich");
        expectedResponse.setAmountPaymentOrder(1.23);

        //Начало теста

        when(agrPaymentOrderRepository.findById(any())).thenReturn(optionalAgrPaymentOrder);
        AgrPaymentOrderResponse actualResponse = agrPaymentOrderService.getById(123);

        assertEquals(expectedResponse, actualResponse);
        verify(agrPaymentOrderRepository).findById(any());
        verifyNoMoreInteractions(agrPaymentOrderRepository);
    }

    @Test
    void update() {
        //Подготовка входных данных
        UpdateAgrPmtOrderRequest request = new UpdateAgrPmtOrderRequest();
        request.setId(123);
        request.setPaymentTypeName("Type4");
        request.setDepartmentCode("004");
        request.setDepartmentName("Dept4");
        request.setEmployeeSurname("Sidorov");
        request.setEmployeeName("Sidor");
        request.setEmployeePatronymic("Sidorovich");
        request.setAmountPaymentOrder(7.83);

        AgrPaymentOrder newAgrPaymentOrder1 = new AgrPaymentOrder(123,"Type1", "001", "Dept1", "Ivanov", "Ivan", "Ivanovich", 1.23);
        AgrPaymentOrder newAgrPaymentOrder2 = new AgrPaymentOrder(456,"Type2", "002", "Dept2", "Petrov", "Petr", "Petrovich", 4.56);
        List<AgrPaymentOrder> allAgrPaymentOrders =  new ArrayList<>();
        allAgrPaymentOrders.add(newAgrPaymentOrder1);
        allAgrPaymentOrders.add(newAgrPaymentOrder2);
        Optional<AgrPaymentOrder> optionalAgrPaymentOrder = allAgrPaymentOrders.stream()
                .filter(AgrPaymentOrder -> AgrPaymentOrder.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        AgrPaymentOrderResponse expectedResponse = new AgrPaymentOrderResponse();
        expectedResponse.setId(123);
        expectedResponse.setPaymentTypeName("Type4");
        expectedResponse.setDepartmentCode("004");
        expectedResponse.setDepartmentName("Dept4");
        expectedResponse.setEmployeeSurname("Sidorov");
        expectedResponse.setEmployeeName("Sidor");
        expectedResponse.setEmployeePatronymic("Sidorovich");
        expectedResponse.setAmountPaymentOrder(7.83);

        //Начало теста
        when(agrPaymentOrderRepository.findById(any())).thenReturn(optionalAgrPaymentOrder);
        when(agrPaymentOrderRepository.update(any())).thenReturn(newAgrPaymentOrder1);
        AgrPaymentOrderResponse actualResponse = agrPaymentOrderService.update(request);

        assertEquals(expectedResponse, actualResponse);
        verify(agrPaymentOrderRepository).findById(any());
        verify(agrPaymentOrderRepository).update(any());
        verifyNoMoreInteractions(agrPaymentOrderRepository);
    }

    @Test
    void delete() {
        Integer deleteAgrPmtOrder = 123;

        AgrPaymentOrder newAgrPaymentOrder1 = new AgrPaymentOrder(123,"Type1", "001", "Dept1", "Ivanov", "Ivan", "Ivanovich", 1.23);
        AgrPaymentOrder newAgrPaymentOrder2 = new AgrPaymentOrder(456,"Type2", "002", "Dept2", "Petrov", "Petr", "Petrovich", 4.56);
        List<AgrPaymentOrder> allAgrPaymentOrders =  new ArrayList<>();
        allAgrPaymentOrders.add(newAgrPaymentOrder1);
        allAgrPaymentOrders.add(newAgrPaymentOrder2);
        Optional<AgrPaymentOrder> optionalAgrPaymentOrder = allAgrPaymentOrders.stream()
                .filter(AgrPaymentOrder -> AgrPaymentOrder.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        AgrPaymentOrderResponse expectedResponse = new AgrPaymentOrderResponse();
        expectedResponse.setId(123);
        expectedResponse.setPaymentTypeName("Type1");
        expectedResponse.setDepartmentCode("001");
        expectedResponse.setDepartmentName("Dept1");
        expectedResponse.setEmployeeSurname("Ivanov");
        expectedResponse.setEmployeeName("Ivan");
        expectedResponse.setEmployeePatronymic("Ivanovich");
        expectedResponse.setAmountPaymentOrder(1.23);

        //Начало теста
        when(agrPaymentOrderRepository.findById(any())).thenReturn(optionalAgrPaymentOrder);
        when(agrPaymentOrderRepository.delete(any())).thenReturn(newAgrPaymentOrder1);
        AgrPaymentOrderResponse actualResponse = agrPaymentOrderService.delete(deleteAgrPmtOrder);

        assertEquals(expectedResponse, actualResponse);
        verify(agrPaymentOrderRepository).findById(any());
        verify(agrPaymentOrderRepository).delete(any());
        verifyNoMoreInteractions(agrPaymentOrderRepository);
    }

    @Test
    void getByPmtTypeName() {
        //Подготовка входных данных
        AgrPaymentOrder newAgrPaymentOrder1 = new AgrPaymentOrder(null,"Type1", "001", "Dept1", "Ivanov", "Ivan", "Ivanovich", 1.23);
        List<AgrPaymentOrder> allAgrPaymentOrders =  new ArrayList<>();
        allAgrPaymentOrders.add(newAgrPaymentOrder1);

        //Подготовка ожидаемого результата
        AgrPaymentOrderResponse AgrPmtOrderResponse1 = new AgrPaymentOrderResponse();
        AgrPmtOrderResponse1.setPaymentTypeName("Type1");
        AgrPmtOrderResponse1.setDepartmentCode("001");
        AgrPmtOrderResponse1.setDepartmentName("Dept1");
        AgrPmtOrderResponse1.setEmployeeSurname("Ivanov");
        AgrPmtOrderResponse1.setEmployeeName("Ivan");
        AgrPmtOrderResponse1.setEmployeePatronymic("Ivanovich");
        AgrPmtOrderResponse1.setAmountPaymentOrder(1.23);

        List<AgrPaymentOrderResponse> allAgrPmtOrdersResponse = new ArrayList<>();
        allAgrPmtOrdersResponse.add(AgrPmtOrderResponse1);
        AgrPmtOrderPageResponse expectedResponse = new AgrPmtOrderPageResponse(allAgrPmtOrdersResponse);

        //Начало теста

        when(agrPaymentOrderRepository.findPmtTypeName(any())).thenReturn(allAgrPaymentOrders);
        AgrPmtOrderPageResponse actualResponse = agrPaymentOrderService.getByPmtTypeName("Type1");

        assertEquals(expectedResponse, actualResponse);
        verify(agrPaymentOrderRepository).findPmtTypeName(any());
        verifyNoMoreInteractions(agrPaymentOrderRepository);
    }

    @Test
    void calculate() {


        LocalDate paymentOrderDate1 = LocalDate.of(2024, 7, 3);
        LocalDate paymentOrderDate2 = LocalDate.of(2024, 7, 4);
        LocalDate paymentOrderDate3 = LocalDate.of(2024, 7, 10);

        //Подготовка входных данных
        PaymentOrder newPaymentOrder1 = new PaymentOrder(123, 456, 789, 908, paymentOrderDate1, 123.45);
        PaymentOrder newPaymentOrder2 = new PaymentOrder(321, 543, 987, 809, paymentOrderDate3, 678.09);
        PaymentOrder newPaymentOrder3 = new PaymentOrder(456, 876, 657, 405, paymentOrderDate3, 33.309);
        PaymentOrder newPaymentOrder4 = new PaymentOrder(789, 543, 789, 908, paymentOrderDate2, 432.11);
        List<PaymentOrder> allPaymentOrders =  new ArrayList<>();
        allPaymentOrders.add(newPaymentOrder1);
        allPaymentOrders.add(newPaymentOrder2);
        allPaymentOrders.add(newPaymentOrder3);
        allPaymentOrders.add(newPaymentOrder4);

        AgrPaymentOrder newAgrPaymentOrder1 = new AgrPaymentOrder(123,"", "001", "Dept1", "Ivanov", "Ivan", "Ivanovich", 555.56);
        List<AgrPaymentOrder> allAgrPaymentOrders =  new ArrayList<>();
        allAgrPaymentOrders.add(newAgrPaymentOrder1);


        Department newDepartment1 = new Department(789, "001", "Dept1");
        Department newDepartment2 = new Department(987, "002", "Dept2");
        List<Department> allDepts = new ArrayList<>();
        allDepts.add(newDepartment1);
        allDepts.add(newDepartment2);
        Optional<Department>  optionalDepartment = allDepts.stream()
                .filter(department -> department.getId().equals(789))
                .findFirst();

        Employee newEmployee1 = new Employee(908, 789, "Ivanov","Ivan", "Ivanovich", 1023.45);
        Employee newEmployee2 = new Employee(809, 987, "Sidorov","Sidor", "Sidorovich", 2045.67);
        List<Employee> allEmps =  new ArrayList<>();
        allEmps.add(newEmployee1);
        allEmps.add(newEmployee2);
        Optional<Employee> optionalEmployee = allEmps.stream()
                .filter(employee -> employee.getId().equals(908) &&
                        employee.getIdDepartment().equals(789))
                .findFirst();

        //Подготовка ожидаемого результата
        AgrPaymentOrderResponse AgrPmtOrderResponse1 = new AgrPaymentOrderResponse();
        AgrPmtOrderResponse1.setId(123);
        AgrPmtOrderResponse1.setPaymentTypeName("");
        AgrPmtOrderResponse1.setDepartmentCode("001");
        AgrPmtOrderResponse1.setDepartmentName("Dept1");
        AgrPmtOrderResponse1.setEmployeeSurname("Ivanov");
        AgrPmtOrderResponse1.setEmployeeName("Ivan");
        AgrPmtOrderResponse1.setEmployeePatronymic("Ivanovich");
        AgrPmtOrderResponse1.setAmountPaymentOrder(555.56);

        List<AgrPaymentOrderResponse> allAgrPmtOrdersResponse = new ArrayList<>();
        allAgrPmtOrdersResponse.add(AgrPmtOrderResponse1);
        AgrPmtOrderPageResponse expectedResponse = new AgrPmtOrderPageResponse(allAgrPmtOrdersResponse);

        //Начало теста

        when(agrPaymentOrderRepository.calculate(any(), any())).thenReturn(allAgrPaymentOrders);

        LocalDate dtFrom = LocalDate.of(2024, 7, 3);
        LocalDate dtTo   = LocalDate.of(2024, 7, 4);

        AgrPmtOrderPageResponse actualResponse = agrPaymentOrderService.calculate(dtFrom, dtTo);

        assertEquals(expectedResponse, actualResponse);
        verify(agrPaymentOrderRepository).calculate(any(), any());

    }
}