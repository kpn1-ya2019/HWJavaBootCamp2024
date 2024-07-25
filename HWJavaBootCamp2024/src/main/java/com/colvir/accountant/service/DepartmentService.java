package com.colvir.accountant.service;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.exception.DeptNotFoundException;
import com.colvir.accountant.mapper.DepartmentMapper;
import com.colvir.accountant.model.Department;
import com.colvir.accountant.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentMapper departmentMapper;

    private final DepartmentRepository departmentRepository;

    private final Random randomDept = new Random();

    public GenerateDeptResponse generateDept(GenerateDeptRequest request) {
        String code = request.getCode();
        String name = request.getName();
        Department newDepartment = new Department(randomDept.nextInt(), code, name);
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

        Department updatedDept = departmentMapper.updateDeptRequestToDept(request);

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
