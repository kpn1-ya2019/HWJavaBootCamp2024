package com.colvir.accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {
    //Сущность: платежные поручения перевода заработной платы (кому, сумма, дата перечисления)
    private Long id;
    private Long idType;
    private Long idDepartment;
    private Long idEmployee;
    private Double amount;
    private Date datePayment;

}
