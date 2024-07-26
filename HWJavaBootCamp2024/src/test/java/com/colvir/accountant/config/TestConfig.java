package com.colvir.accountant.config;

import com.colvir.accountant.mapper.*;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public AccountantMapper getAccountantMapper() {return Mappers.getMapper(AccountantMapper.class);}

    @Bean
    public AgrPaymentOrderMapper getAgrPaymentOrderMapper() {return Mappers.getMapper(AgrPaymentOrderMapper.class);}

    @Bean
    public DepartmentMapper getDepartmentMapper() {return Mappers.getMapper(DepartmentMapper.class);}

    @Bean
    public EmployeeMapper getEmployeeMapper() {return Mappers.getMapper(EmployeeMapper.class);}

    @Bean
    public PaymentOrderMapper getPaymentOrderMapper() {return Mappers.getMapper(PaymentOrderMapper.class);}

    @Bean
    public PaymentTypeMapper getPaymentTypeMapper() {
        return Mappers.getMapper(PaymentTypeMapper.class);
    }

}
