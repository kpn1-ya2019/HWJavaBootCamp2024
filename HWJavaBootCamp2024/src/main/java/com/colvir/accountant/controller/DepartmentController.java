package com.colvir.accountant.controller;

<<<<<<< HEAD
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
=======
import com.colvir.accountant.dto.*;
import com.colvir.accountant.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
>>>>>>> master

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

<<<<<<< HEAD
    @GetMapping("id/{id}")
    public DepartmentResponse getById(@PathVariable("id") Integer id) { 
        return departmentService.getById(id); 
    }

    @GetMapping("code/{code}")
    public DepartmentResponse getByCode(@PathVariable String code) { 
        return departmentService.getByCode(code); 
    }
=======
    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable("id") Integer id) { return departmentService.getById(id); }
    @GetMapping("/{code}")
    public DepartmentResponse getByCode(@PathVariable("code") String code) { return departmentService.getByCode(code); }
>>>>>>> master

    @PutMapping
    public DepartmentResponse update(@RequestBody UpdateDeptRequest request) {return departmentService.update(request); }

<<<<<<< HEAD
    @DeleteMapping("/{id}")
    public DepartmentResponse delete(@PathVariable Integer id) {return departmentService.delete(id); }
=======
    @DeleteMapping("/id")
    public DepartmentResponse delete(@RequestBody UpdateDeptRequest request) {return departmentService.update(request); }
>>>>>>> master
}
