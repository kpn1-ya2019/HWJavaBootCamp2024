package com.colvir.accountant.controller;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private  final EmployeeService employeeService;

    @PostMapping("generate")
    public GenerateEmpResponse generateEmployee(@RequestBody GenerateEmpRequest request) {
        return employeeService.generateEmp(request);
    }

    @GetMapping
    public EmpPageResponse getAll() {return  employeeService.getAll(); }

    @GetMapping("/{id}{idDepartment}")
    public EmployeeResponse getByIdAndIdDept(@PathVariable("id") Long id, @PathVariable("idDepartment") Long idDepartment) { return employeeService.getByIdAndIdDept(id, idDepartment); }

    @PutMapping
    public EmployeeResponse update(@RequestBody UpdateEmployeeRequest request) {return employeeService.update(request); }

    @DeleteMapping("/id")
    public EmployeeResponse delete(@RequestBody UpdateEmployeeRequest request) {return employeeService.update(request); }
}
