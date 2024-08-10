package com.colvir.accountant.service;

import com.colvir.accountant.config.TestConfig;
import com.colvir.accountant.dto.*;
import com.colvir.accountant.exception.PmtTypeNotFoundException;
import com.colvir.accountant.mapper.PaymentTypeMapper;
import com.colvir.accountant.model.Department;
import com.colvir.accountant.model.PaymentType;
import com.colvir.accountant.repository.PaymentTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PaymentTypeService.class,
        PaymentTypeMapper.class
})
@SpringBootTest(classes = {TestConfig.class})
public class PaymentTypeServiceTest {
    @Autowired
    private PaymentTypeService paymentTypeService;

    @MockBean
    private PaymentTypeRepository paymentTypeRepository;
    @Test
    void generatePaymentType_success() {

        //Подготовка входных данных
        GeneratePmtTypeRequest request = new GeneratePmtTypeRequest();
        request.setName("Аванс");
        PaymentType newPaymentType1 = new PaymentType("Type1");
        newPaymentType1.setId(123);

        //Подготовка ожидаемого результата
        GeneratePmtTypeResponse expectedResponse = new GeneratePmtTypeResponse();
        expectedResponse.setName("Аванс");

        //Начало теста

        when(paymentTypeRepository.save(any())).thenReturn(newPaymentType1);

        GeneratePmtTypeResponse actualResponse = paymentTypeService.generatePmtType(request);
        assertEquals(expectedResponse, actualResponse);
        verify(paymentTypeRepository,times(1)).save(any(PaymentType.class));
        verifyNoMoreInteractions(paymentTypeRepository);
    }

    @Test
    void getByIdPaymentType_withException() {

        //Подготовка входных данных
        Integer id = 1;

        //Подготовка ожидаемого результата
        String expectedMessage = "Тип выплаты с id = 1 не найден";
        when(paymentTypeRepository.findById(id)).thenReturn(Optional.empty());

        //Начало теста
        Exception exception = assertThrows(PmtTypeNotFoundException.class, () -> paymentTypeService.getById(id));
        assertEquals(expectedMessage, exception.getMessage());
        verify(paymentTypeRepository).findById(id);
        verifyNoMoreInteractions(paymentTypeRepository);

    }

    @Test
    void getAll() {
        //Подготовка входных данных
        PaymentType newPaymentType1 = new PaymentType( "Type1");
        PaymentType newPaymentType2 = new PaymentType( "Type2");
        newPaymentType1.setId(123);
        newPaymentType2.setId(456);
        List<PaymentType> allPmtTypes =  new ArrayList<>();
        allPmtTypes.add(newPaymentType1);
        allPmtTypes.add(newPaymentType2);

        //Подготовка ожидаемого результата
        PaymentTypeResponse paymentTypeResponse1 = new PaymentTypeResponse();
        paymentTypeResponse1.setId(123);
        paymentTypeResponse1.setName("Type1");

        PaymentTypeResponse paymentTypeResponse2 = new PaymentTypeResponse();
        paymentTypeResponse2.setId(456);
        paymentTypeResponse2.setName("Type2");

        List<PaymentTypeResponse> allPaymentTypeResponse = new ArrayList<>();
        allPaymentTypeResponse.add(paymentTypeResponse1);
        allPaymentTypeResponse.add(paymentTypeResponse2);
        PmtTypePageResponse expectedResponse = new PmtTypePageResponse(allPaymentTypeResponse);

        //Начало теста

        when(paymentTypeRepository.findAll()).thenReturn(allPmtTypes);
        PmtTypePageResponse actualPageResponse = paymentTypeService.getAll();

        assertEquals(expectedResponse, actualPageResponse);
        verify(paymentTypeRepository, times(1)).findAll();
        verifyNoMoreInteractions(paymentTypeRepository);
    }

    @Test
    void getById() {
        //Подготовка входных данных
        PaymentType newPaymentType1 = new PaymentType("Type1");
        PaymentType newPaymentType2 = new PaymentType("Type1");
        newPaymentType1.setId(123);
        newPaymentType2.setId(456);
        List<PaymentType> allPmtTypes =  new ArrayList<>();
        allPmtTypes.add(newPaymentType1);
        allPmtTypes.add(newPaymentType2);
        Optional<PaymentType>  optionalPaymentType = allPmtTypes.stream()
                .filter(PaymentType -> PaymentType.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        PaymentTypeResponse expectedResponse = new PaymentTypeResponse();
        expectedResponse.setId(123);
        expectedResponse.setName("Type1");

        //Начало теста
        when(paymentTypeRepository.findById(any())).thenReturn(optionalPaymentType);
        PaymentTypeResponse actualResponse = paymentTypeService.getById(123);
        assertEquals(expectedResponse, actualResponse);
        verify(paymentTypeRepository).findById(any());
        verifyNoMoreInteractions(paymentTypeRepository);

    }

    @Test
    void update() {
        //Подготовка входных данных
        UpdatePmtTypeRequest request = new UpdatePmtTypeRequest();
        request.setId(123);
        request.setName("Type3");

        PaymentType newPaymentType1 = new PaymentType( "Type1");
        PaymentType newPaymentType2 = new PaymentType( "Type2");
        newPaymentType1.setId(123);
        newPaymentType2.setId(456);
        List<PaymentType> allPmtTypes =  new ArrayList<>();
        allPmtTypes.add(newPaymentType1);
        allPmtTypes.add(newPaymentType2);
        Optional<PaymentType>  optionalPaymentType = allPmtTypes.stream()
                .filter(PaymentType -> PaymentType.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        PaymentTypeResponse expectedResponse = new PaymentTypeResponse();
        expectedResponse.setId(123);
        expectedResponse.setName("Type3");

        //Начало теста
        when(paymentTypeRepository.findById(any())).thenReturn(optionalPaymentType);
        when(paymentTypeRepository.update(any())).thenReturn(newPaymentType1);
        PaymentTypeResponse actualResponse = paymentTypeService.update(request);
        assertEquals(expectedResponse, actualResponse);
        verify(paymentTypeRepository).findById(any());
        verify(paymentTypeRepository).update(any());
        verifyNoMoreInteractions(paymentTypeRepository);
    }

    @Test
    void delete() {
        //Подготовка входных данных
        Integer deletePaymentType = 123;

        PaymentType newPaymentType1 = new PaymentType( "Type1");
        PaymentType newPaymentType2 = new PaymentType( "Type1");
        newPaymentType1.setId(123);
        newPaymentType2.setId(456);
        List<PaymentType> allPmtTypes =  new ArrayList<>();
        allPmtTypes.add(newPaymentType1);
        allPmtTypes.add(newPaymentType2);
        Optional<PaymentType>  optionalPaymentType = allPmtTypes.stream()
                .filter(PaymentType -> PaymentType.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        PaymentTypeResponse expectedResponse = new PaymentTypeResponse();
        expectedResponse.setId(123);
        expectedResponse.setName("Type1");

        //Начало теста
        when(paymentTypeRepository.findById(any())).thenReturn(optionalPaymentType);
        when(paymentTypeRepository.delete(any())).thenReturn(newPaymentType1);
        PaymentTypeResponse actualResponse = paymentTypeService.delete(deletePaymentType);
        assertEquals(expectedResponse, actualResponse);
        verify(paymentTypeRepository).findById(any());
        verify(paymentTypeRepository).delete(any());
        verifyNoMoreInteractions(paymentTypeRepository);
    }
}
