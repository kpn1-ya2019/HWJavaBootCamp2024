package com.colvir.accountant.dto;

import lombok.Data;

@Data
public class AgrPaymentOrderResponse {

    private Integer    id;
    private String  paymentTypeName;
    private String  departmentCode;
    private String  departmentName;
    private String  employeeSurname;
    private String  employeeName;
    private String  employeePatronymic;
    private Double  amountPaymentOrder;

}
