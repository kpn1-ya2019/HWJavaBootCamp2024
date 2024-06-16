package com.colvir.accountant.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentOrderResponse {

    private Long   id;
    private Long   idType;
    private Long   idEmployee;
    private Long   idDepartment;
    private Date   datePayment;
    private Double amount;

}
