package com.colvir.accountant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GenerateDeptPageRequest {

    private List<GenerateDeptRequest> allDepartments;

}
