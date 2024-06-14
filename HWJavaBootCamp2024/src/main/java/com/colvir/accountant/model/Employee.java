package com.colvir.accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
  //Сущность: сотрудник (имя, фамилия, зарплата, отдел)
  private Long id;
  private String surname;
  private String name;
  private String patronymic;
  private Double salary;
  private Long idDepartment;

}
