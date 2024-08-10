package com.colvir.accountant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "employees")
@Entity
@NoArgsConstructor
public class Employee {
  //Сущность: сотрудник (имя, отдел, фамилия, зарплата)
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
  @SequenceGenerator(name = "employee_seq", sequenceName = "employee_sequence", allocationSize = 1)
  private Integer   id;
  private Integer   idDepartment;
  private String surname;
  private String name;
  private String patronymic;
  private Double salary;
  public Employee(Integer   idDepartment, String surname, String name, String patronymic, Double salary) {
    this.idDepartment = idDepartment;
    this.surname = surname;
    this.name = name;
    this.patronymic = patronymic;
    this.salary = salary;
  }

}
