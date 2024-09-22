package com.colvir.accountant.model;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
=======
<<<<<<< HEAD
>>>>>>> master
public class Employee {
  //Сущность: сотрудник (имя, отдел, фамилия, зарплата)
  @Id
  private Integer   id;
  private Integer   idDepartment;
  private String surname;
  private String name;
  private String patronymic;
  private Double salary;
<<<<<<< HEAD
=======
  private Long idDepartment;
=======
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
>>>>>>> 68020e89b9af49acf8c8a6d413334b4b974d7bc9
>>>>>>> master

}
