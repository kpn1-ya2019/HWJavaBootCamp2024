package com.colvir.accountant.model;

<<<<<<< HEAD
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
=======
<<<<<<< HEAD
>>>>>>> master
public class Department {
    //сущность
    @Id
    private Integer   id;

    private String code;

<<<<<<< HEAD
    private String name;

=======
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
>>>>>>> master
}
