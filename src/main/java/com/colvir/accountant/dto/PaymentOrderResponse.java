package com.colvir.accountant.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentOrderResponse {

    private Integer   id;
    private Integer   idType;
    private Integer   idEmployee;
    private Integer   idDepartment;
    private LocalDate datePayment;
    private Double amount;

}
