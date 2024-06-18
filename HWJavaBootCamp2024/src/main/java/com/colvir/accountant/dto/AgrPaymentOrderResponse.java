package com.colvir.accountant.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AgrPaymentOrderResponse {

    private Long    id;
    private String  paymentTypeName;
    private String  departmentCode;
    private String  departmentName;
    private String  employeeSurname;
    private String  employeeName;
    private String  employeePatronymic;
    private Double  amountPaymentOrder;

}
