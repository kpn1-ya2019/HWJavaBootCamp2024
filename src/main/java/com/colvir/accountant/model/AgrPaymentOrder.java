package com.colvir.accountant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "agrpaymentorders")
@Entity
@NoArgsConstructor
public class AgrPaymentOrder {
    //Сущность: агрегат зарплаты платы (кому, сумма)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agrpaymentorder_seq")
    @SequenceGenerator(name = "agrpaymentorder_seq", sequenceName = "agrpaymentorder_seq", allocationSize = 1)
    private Integer    id;
    private String  paymentTypeName;
    private String  departmentCode;
    private String  departmentName;
    private String  employeeSurname;
    private String  employeeName;
    private String  employeePatronymic;
    private Double  amountPaymentOrder;

    public AgrPaymentOrder(String paymentTypeName, String departmentCode, String departmentName, String employeeSurname, String employeeName, String employeePatronymic, Double amountPaymentOrder) {
        this.paymentTypeName = paymentTypeName;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.employeeSurname = employeeSurname;
        this.employeeName = employeeName;
        this.employeePatronymic = employeePatronymic;
        this.amountPaymentOrder = amountPaymentOrder;
    }
}
