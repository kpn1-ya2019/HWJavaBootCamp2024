package com.colvir.accountant.dto;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Long   id;
    private Long   idDepartment;
    private String surname;
    private String name;
    private String patronymic;
    private Double salary;

}
