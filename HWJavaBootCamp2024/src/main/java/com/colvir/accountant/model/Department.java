package com.colvir.accountant.model;

<<<<<<< HEAD
public class Department {
    //сущность Подразделение (наименование, код)
    private Long id;
    private String name;
    private String code;

=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    //сущность
    private Integer   id;

    private String code;

    private String name;

>>>>>>> 68020e89b9af49acf8c8a6d413334b4b974d7bc9
}
