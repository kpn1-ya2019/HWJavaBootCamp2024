package com.colvir.accountant.dto;

import lombok.Data;

@Data
public class UpdateDeptRequest {

    private Integer   id;

    private String code;

    private String name;

}
