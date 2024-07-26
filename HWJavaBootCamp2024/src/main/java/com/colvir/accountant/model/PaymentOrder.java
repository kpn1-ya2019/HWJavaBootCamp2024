package com.colvir.accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaymentOrder {
    //Сущность: платежные поручения перевода заработной платы (кому, сумма, дата перечисления)
    private Integer   id;
    private Integer   idType;
    private Integer   idDepartment;
    private Integer   idEmployee;
    private LocalDate datePayment;
    private Double amount;
}
