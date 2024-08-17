package com.colvir.accountant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "departments")
@Entity
@NoArgsConstructor
public class Department {
    //сущность
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    @SequenceGenerator(name = "department_seq", sequenceName = "department_seq", allocationSize = 1)
    private Integer   id;

    private String code;

    private String name;

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
