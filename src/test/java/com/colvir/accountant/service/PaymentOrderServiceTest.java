package com.colvir.accountant.service;

import com.colvir.accountant.config.TestConfig;
import com.colvir.accountant.dto.*;
import com.colvir.accountant.mapper.PaymentOrderMapper;
import com.colvir.accountant.model.PaymentOrder;
import com.colvir.accountant.repository.PaymentOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PaymentOrderService.class,
        PaymentOrderMapper.class
})
@SpringBootTest(classes = {TestConfig.class})
class PaymentOrderServiceTest {

    @Autowired
    private PaymentOrderService paymentOrderService;

    @MockBean
    private PaymentOrderRepository paymentOrderRepository;

    @Test
    void generatePmtOrder() {
        LocalDate currDate = LocalDate.now();
        //Подготовка входных данных
        GeneratePmtOrderRequest request = new GeneratePmtOrderRequest();
        request.setIdType(123);
        request.setIdEmployee(456);
        request.setIdDepartment(789);
        request.setDatePayment(currDate);
        request.setAmount(123.45);
        PaymentOrder newPaymentOrder1 = new PaymentOrder( 456, 789, 908, currDate, 123.45);
        newPaymentOrder1.setId(123);


        //Подготовка ожидаемого результата
        GeneratePmtOrderResponse expectedResponse = new GeneratePmtOrderResponse();
        expectedResponse.setIdType(123);
        expectedResponse.setIdEmployee(456);
        expectedResponse.setIdDepartment(789);
        expectedResponse.setDatePayment(currDate);
        expectedResponse.setAmount(123.45);

        //Начало теста
        when(paymentOrderRepository.save(any())).thenReturn(newPaymentOrder1);

        GeneratePmtOrderResponse actualResponse = paymentOrderService.generatePmtOrder(request);
        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository).save(any());
        verifyNoMoreInteractions(paymentOrderRepository);
    }

    @Test
    void getAll() {
        LocalDate currDate = LocalDate.now();
        //Подготовка входных данных
        PaymentOrder newPaymentOrder1 = new PaymentOrder( 456, 789, 908, currDate, 123.45);
        PaymentOrder newPaymentOrder2 = new PaymentOrder( 543, 987, 809, currDate, 678.09);
        newPaymentOrder1.setId(123);
        newPaymentOrder2.setId(321);
        List<PaymentOrder> allPaymentOrders =  new ArrayList<>();
        allPaymentOrders.add(newPaymentOrder1);
        allPaymentOrders.add(newPaymentOrder2);

        //Подготовка ожидаемого результата
        PaymentOrderResponse pmtOrderResponse1 = new PaymentOrderResponse();
        pmtOrderResponse1.setId(123);
        pmtOrderResponse1.setIdType(456);
        pmtOrderResponse1.setIdDepartment(789);
        pmtOrderResponse1.setIdEmployee(908);
        pmtOrderResponse1.setDatePayment(currDate);
        pmtOrderResponse1.setAmount(123.45);

        PaymentOrderResponse pmtOrderResponse2 = new PaymentOrderResponse();
        pmtOrderResponse2.setId(321);
        pmtOrderResponse2.setIdType(543);
        pmtOrderResponse2.setIdDepartment(987);
        pmtOrderResponse2.setIdEmployee(809);
        pmtOrderResponse2.setDatePayment(currDate);
        pmtOrderResponse2.setAmount(678.09);

        List<PaymentOrderResponse> allPaymentOrdersResponse = new ArrayList<>();
        allPaymentOrdersResponse.add(pmtOrderResponse1);
        allPaymentOrdersResponse.add(pmtOrderResponse2);
        PmtOrderPageResponse expectedResponse = new PmtOrderPageResponse(allPaymentOrdersResponse);

        //Начало теста

        when(paymentOrderRepository.findAll()).thenReturn(allPaymentOrders);
        PmtOrderPageResponse actualPageResponse = paymentOrderService.getAll();

        assertEquals(expectedResponse, actualPageResponse);
        verify(paymentOrderRepository, times(1)).findAll();
        verifyNoMoreInteractions(paymentOrderRepository);

    }

    @Test
    void getById() {
        LocalDate currDate = LocalDate.now();
        //Подготовка входных данных
        PaymentOrder newPaymentOrder1 = new PaymentOrder( 456, 789, 908, currDate, 123.45);
        PaymentOrder newPaymentOrder2 = new PaymentOrder( 543, 987, 809, currDate, 678.09);
        newPaymentOrder1.setId(123);
        newPaymentOrder2.setId(321);
        List<PaymentOrder> allPaymentOrders =  new ArrayList<>();
        allPaymentOrders.add(newPaymentOrder1);
        allPaymentOrders.add(newPaymentOrder2);
        Optional<PaymentOrder> optionalPaymentOrder = allPaymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        PaymentOrderResponse expectedResponse = new PaymentOrderResponse();
        expectedResponse.setId(123);
        expectedResponse.setIdType(456);
        expectedResponse.setIdDepartment(789);
        expectedResponse.setIdEmployee(908);
        expectedResponse.setDatePayment(currDate);
        expectedResponse.setAmount(123.45);

        //Начало теста

        when(paymentOrderRepository.findById(any())).thenReturn(optionalPaymentOrder);
        PaymentOrderResponse actualResponse = paymentOrderService.getById(123);

        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository, times(1)).findById(any());
        verifyNoMoreInteractions(paymentOrderRepository);
    }

    @Test
    void update() {
        LocalDate currDate = LocalDate.now();
        //Подготовка входных данных
        UpdatePmtOrderRequest request = new UpdatePmtOrderRequest();
        request.setId(123);
        request.setIdType(333);
        request.setIdDepartment(444);
        request.setIdEmployee(555);
        request.setDatePayment(currDate);
        request.setAmount(777.89);

        PaymentOrder newPaymentOrder1 = new PaymentOrder( 456, 789, 908, currDate, 123.45);
        PaymentOrder newPaymentOrder2 = new PaymentOrder( 543, 987, 809, currDate, 678.09);
        newPaymentOrder1.setId(123);
        newPaymentOrder2.setId(321);
        List<PaymentOrder> allPaymentOrders =  new ArrayList<>();
        allPaymentOrders.add(newPaymentOrder1);
        allPaymentOrders.add(newPaymentOrder2);
        Optional<PaymentOrder> optionalPaymentOrder = allPaymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        PaymentOrderResponse expectedResponse = new PaymentOrderResponse();
        expectedResponse.setId(123);
        expectedResponse.setIdType(333);
        expectedResponse.setIdDepartment(444);
        expectedResponse.setIdEmployee(555);
        expectedResponse.setDatePayment(currDate);
        expectedResponse.setAmount(777.89);

        //Начало теста

        when(paymentOrderRepository.findById(any())).thenReturn(optionalPaymentOrder);
        when(paymentOrderRepository.update(any())).thenReturn(newPaymentOrder1);
        PaymentOrderResponse actualResponse = paymentOrderService.update(request);

        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository, times(1)).findById(any());
        verify(paymentOrderRepository).update(any());
        verifyNoMoreInteractions(paymentOrderRepository);
    }

    @Test
    void delete() {
        LocalDate currDate = LocalDate.now();
        Integer deletePaymentOrder = 123;

        //Подготовка входных данных
        PaymentOrder newPaymentOrder1 = new PaymentOrder( 456, 789, 908, currDate, 123.45);
        PaymentOrder newPaymentOrder2 = new PaymentOrder( 543, 987, 809, currDate, 678.09);
        newPaymentOrder1.setId(123);
        newPaymentOrder2.setId(321);
        List<PaymentOrder> allPaymentOrders =  new ArrayList<>();
        allPaymentOrders.add(newPaymentOrder1);
        allPaymentOrders.add(newPaymentOrder2);
        Optional<PaymentOrder> optionalPaymentOrder = allPaymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        PaymentOrderResponse expectedResponse = new PaymentOrderResponse();
        expectedResponse.setId(123);
        expectedResponse.setIdType(456);
        expectedResponse.setIdDepartment(789);
        expectedResponse.setIdEmployee(908);
        expectedResponse.setDatePayment(currDate);
        expectedResponse.setAmount(123.45);

        //Начало теста

        when(paymentOrderRepository.findById(any())).thenReturn(optionalPaymentOrder);
        when(paymentOrderRepository.delete(any())).thenReturn(newPaymentOrder1);
        PaymentOrderResponse actualResponse = paymentOrderService.delete(deletePaymentOrder);

        assertEquals(expectedResponse, actualResponse);
        verify(paymentOrderRepository, times(1)).findById(any());
        verify(paymentOrderRepository).delete(any());
        verifyNoMoreInteractions(paymentOrderRepository);
    }
}