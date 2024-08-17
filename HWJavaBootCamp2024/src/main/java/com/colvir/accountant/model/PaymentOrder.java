package com.colvir.accountant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Table(name = "paymentorders")
@Entity
@NoArgsConstructor
public class PaymentOrder {
    //Сущность: платежные поручения перевода заработной платы (кому, сумма, дата перечисления)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentorder_seq")
    @SequenceGenerator(name = "paymentorder_seq", sequenceName = "paymentorder_seq", allocationSize = 1)
    private Integer   id;
    private Integer   idType;
    private Integer   idDepartment;
    private Integer   idEmployee;
    private LocalDate datePayment;
    private Double    amount;

    public PaymentOrder(Integer idType, Integer idDepartment, Integer idEmployee, LocalDate datePayment, Double amount) {
        this.idType = idType;
        this.idDepartment = idDepartment;
        this.idEmployee = idEmployee;
        this.datePayment = datePayment;
        this.amount = amount;
    }
}
