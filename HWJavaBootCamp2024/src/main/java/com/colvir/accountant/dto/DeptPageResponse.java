package com.colvir.accountant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DeptPageResponse {

    private List<DepartmentResponse> departments;

}
