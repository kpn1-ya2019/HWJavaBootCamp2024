package com.colvir.accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
  //Сущность: сотрудник (имя, отдел, фамилия, зарплата)
  private Integer   id;
  private Integer   idDepartment;
  private String surname;
  private String name;
  private String patronymic;
  private Double salary;

}
