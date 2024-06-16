package com.colvir.accountant.dto;

import lombok.Data;

@Data
public class UpdateEmployeeRequest {

    private Long   id;
    private Long   idDepartment;
    private String surname;
    private String name;
    private String patronymic;
    private Double salary;

}
