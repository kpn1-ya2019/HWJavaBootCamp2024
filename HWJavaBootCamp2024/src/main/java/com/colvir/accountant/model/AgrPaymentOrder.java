package com.colvir.accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgrPaymentOrder {
    //Сущность: агрегат зарплаты платы (кому, сумма, дата перечисления)
    private Long    id;
    private String  paymentTypeName;
    private String  departmentCode;
    private String  departmentName;
    private String  employeeSurname;
    private String  employeeName;
    private String  employeePatronymic;
    private Double  amountPaymentOrder;

}
