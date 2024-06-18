package com.colvir.accountant.controller;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable("id") Long id) { return departmentService.getById(id); }
    @GetMapping("/{code}")
    public DepartmentResponse getByCode(@PathVariable("code") String code) { return departmentService.getByCode(code); }

    @PutMapping
    public DepartmentResponse update(@RequestBody UpdateDeptRequest request) {return departmentService.update(request); }

    @DeleteMapping("/id")
    public DepartmentResponse delete(@RequestBody UpdateDeptRequest request) {return departmentService.update(request); }
}
