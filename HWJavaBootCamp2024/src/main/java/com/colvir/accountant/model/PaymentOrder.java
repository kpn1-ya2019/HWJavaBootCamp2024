package com.colvir.accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

<<<<<<< HEAD
import java.time.LocalDate;
=======
<<<<<<< HEAD
import java.util.Date;
=======
import java.time.LocalDate;
>>>>>>> 68020e89b9af49acf8c8a6d413334b4b974d7bc9
>>>>>>> master

@Data
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD

public class PaymentOrder {
    //Сущность: платежные поручения перевода заработной платы (кому, сумма, дата перечисления)
    @Id
=======
<<<<<<< HEAD
public class PaymentOrder {
    //Сущность: платежные поручения перевода заработной платы (кому, сумма, дата перечисления)
    private Long id;
    private Long idType;
    private Long idDepartment;
    private Long idEmployee;
    private Double amount;
    private Date datePayment;

=======

public class PaymentOrder {
    //Сущность: платежные поручения перевода заработной платы (кому, сумма, дата перечисления)
>>>>>>> master
    private Integer   id;
    private Integer   idType;
    private Integer   idDepartment;
    private Integer   idEmployee;
    private LocalDate datePayment;
<<<<<<< HEAD
    private Double    amount;
=======
    private Double amount;
>>>>>>> 68020e89b9af49acf8c8a6d413334b4b974d7bc9
>>>>>>> master
}
