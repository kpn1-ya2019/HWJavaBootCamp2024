package com.colvir.accountant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "paymenttypes")
@Entity
@NoArgsConstructor
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymenttype_seq")
    @SequenceGenerator(name = "paymenttype_seq", sequenceName = "paymenttype_seq", allocationSize = 1)
    private Integer   id;
    private String name;

    public PaymentType(String name) {
        this.name = name;
    }
}
