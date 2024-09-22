package com.colvir.accountant.service;

<<<<<<< HEAD
import java.util.List;

import org.springframework.stereotype.Service;

import com.colvir.accountant.dto.DepartmentResponse;
import com.colvir.accountant.dto.DeptPageResponse;
import com.colvir.accountant.dto.GenerateDeptRequest;
import com.colvir.accountant.dto.GenerateDeptResponse;
import com.colvir.accountant.dto.UpdateDeptRequest;
=======
import com.colvir.accountant.dto.*;
>>>>>>> master
import com.colvir.accountant.exception.DeptNotFoundException;
import com.colvir.accountant.mapper.DepartmentMapper;
import com.colvir.accountant.model.Department;
import com.colvir.accountant.repository.DepartmentRepository;
<<<<<<< HEAD

import lombok.RequiredArgsConstructor;
=======
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
>>>>>>> master

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentMapper departmentMapper;

    private final DepartmentRepository departmentRepository;

<<<<<<< HEAD
    public GenerateDeptResponse generateDept(GenerateDeptRequest request) {
        Integer newId = departmentRepository.generateIdDept();
        String code = request.getCode();
        String name = request.getName();
        Department newDepartment = new Department(newId, code, name);
=======
    private final Random randomDept = new Random();

    public GenerateDeptResponse generateDept(GenerateDeptRequest request) {
        String code = request.getCode();
        String name = request.getName();
        Department newDepartment = new Department(randomDept.nextInt(), code, name);
>>>>>>> master
        departmentRepository.save(newDepartment);
        return  departmentMapper.deptToGenerateDeptResponse(newDepartment);
    }

    public DeptPageResponse getAll() {
        List<Department> allDepartments = departmentRepository.findAll();
        return departmentMapper.departmentsToDeptPageResponse(allDepartments);
    }

    public DepartmentResponse getById(Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(()-> new DeptNotFoundException(String.format("%s с id = %s не найдено", "Подразделение",id)));
        return departmentMapper.deptToDeptResponse(department);
    }
    public DepartmentResponse getByCode(String code) {
        Department department = departmentRepository.findByCode(code)
                .orElseThrow(()-> new DeptNotFoundException(String.format("%s с кодом = %s не найдено", "Подразделение", code)));
        return departmentMapper.deptToDeptResponse(department);
    }

    public DepartmentResponse update(UpdateDeptRequest request) {
        Integer departmentId = request.getId();
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DeptNotFoundException(String.format("%s с id = %s не найдено", "Подразделение", departmentId)));

<<<<<<< HEAD
        Department updatedDept = departmentMapper.updateDeptRequestToDept(department, request);
=======
        Department updatedDept = departmentMapper.updateDeptRequestToDept(request);
>>>>>>> master

        departmentRepository.update(updatedDept);

        return departmentMapper.deptToDeptResponse(updatedDept);
    }

    public DepartmentResponse delete(Integer id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DeptNotFoundException(String.format("%s с id = %s не найдено", "Подразделение", id)));

        departmentRepository.delete(id);

        return departmentMapper.deptToDeptResponse(department);
    }
}
