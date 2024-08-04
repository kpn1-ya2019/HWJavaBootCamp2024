package com.colvir.accountant.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    //сущность
    @Id
    private Integer   id;

    private String code;

    private String name;

}
