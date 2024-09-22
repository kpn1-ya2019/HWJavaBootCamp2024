package com.colvir.accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import org.springframework.data.annotation.Id;
=======
>>>>>>> master

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgrPaymentOrder {
<<<<<<< HEAD
    //Сущность: агрегат зарплаты платы (кому, сумма)
    @Id
=======
    //Сущность: агрегат зарплаты платы (кому, сумма, дата перечисления)
>>>>>>> master
    private Integer    id;
    private String  paymentTypeName;
    private String  departmentCode;
    private String  departmentName;
    private String  employeeSurname;
    private String  employeeName;
    private String  employeePatronymic;
    private Double  amountPaymentOrder;

}
