package com.colvir.accountant.dto;

import lombok.Data;

@Data
public class UpdateEmployeeRequest {

    private Integer   id;
    private Integer   idDepartment;
    private String surname;
    private String name;
    private String patronymic;
    private Double salary;

}
