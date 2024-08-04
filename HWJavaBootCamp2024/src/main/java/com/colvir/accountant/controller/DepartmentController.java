package com.colvir.accountant.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colvir.accountant.dto.DepartmentResponse;
import com.colvir.accountant.dto.DeptPageResponse;
import com.colvir.accountant.dto.GenerateDeptRequest;
import com.colvir.accountant.dto.GenerateDeptResponse;
import com.colvir.accountant.dto.UpdateDeptRequest;
import com.colvir.accountant.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("department")
@RequiredArgsConstructor
public class DepartmentController {

    private  final DepartmentService departmentService;

    @PostMapping("generate")
    public GenerateDeptResponse generateDepartment(@RequestBody GenerateDeptRequest request) {
        return departmentService.generateDept(request);
    }

    @GetMapping
    public DeptPageResponse getAll() {return  departmentService.getAll(); }

    @GetMapping("id/{id}")
    public DepartmentResponse getById(@PathVariable("id") Integer id) { 
        return departmentService.getById(id); 
    }

    @GetMapping("code/{code}")
    public DepartmentResponse getByCode(@PathVariable String code) { 
        return departmentService.getByCode(code); 
    }

    @PutMapping
    public DepartmentResponse update(@RequestBody UpdateDeptRequest request) {return departmentService.update(request); }

    @DeleteMapping("/{id}")
    public DepartmentResponse delete(@PathVariable Integer id) {return departmentService.delete(id); }
}
