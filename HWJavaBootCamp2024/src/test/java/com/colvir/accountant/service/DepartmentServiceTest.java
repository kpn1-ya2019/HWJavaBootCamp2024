package com.colvir.accountant.service;

import com.colvir.accountant.config.TestConfig;
import com.colvir.accountant.dto.*;
import com.colvir.accountant.mapper.DepartmentMapper;
import com.colvir.accountant.model.Department;
import com.colvir.accountant.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DepartmentService.class,
        DepartmentMapper.class
})
@SpringBootTest(classes = {TestConfig.class})
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Test
    void generateDept_success() {

        //Подготовка входных данных
        GenerateDeptRequest request = new GenerateDeptRequest();
        request.setCode("001");
        request.setName("Dept1");
        Department newDepartment1 = new Department(12345, "001", "Dept1");

        //Подготовка ожидаемого результата
        GenerateDeptResponse expectedResponse = new GenerateDeptResponse();
        expectedResponse.setCode("001");
        expectedResponse.setName("Dept1");

        //Начало теста

        when(departmentRepository.generateIdDept()).thenReturn(12345);
        when(departmentRepository.save(any())).thenReturn(newDepartment1);

        GenerateDeptResponse actualResponse = departmentService.generateDept(request);
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository).save(any());
        verify(departmentRepository).generateIdDept();
        verifyNoMoreInteractions(departmentRepository);

    }

    @Test
    void getAll() {
        //Подготовка входных данных
        Department newDepartment1 = new Department(null, "001", "Dept1");
        Department newDepartment2 = new Department(null, "002", "Dept2");
        List<Department> allDepts =  new ArrayList<>();
        allDepts.add(newDepartment1);
        allDepts.add(newDepartment2);

        //Подготовка ожидаемого результата
        DepartmentResponse departmentResponse1 = new DepartmentResponse();
        departmentResponse1.setCode("001");
        departmentResponse1.setName("Dept1");

        DepartmentResponse departmentResponse2 = new DepartmentResponse();
        departmentResponse2.setCode("002");
        departmentResponse2.setName("Dept2");

        List<DepartmentResponse> allDepartmentsResponse = new ArrayList<>();
        allDepartmentsResponse.add(departmentResponse1);
        allDepartmentsResponse.add(departmentResponse2);
        DeptPageResponse expectedResponse = new DeptPageResponse(allDepartmentsResponse);

        //Начало теста

        when(departmentRepository.findAll()).thenReturn(allDepts);
        DeptPageResponse actualPageResponse = departmentService.getAll();

        assertEquals(expectedResponse, actualPageResponse);
        verify(departmentRepository, times(1)).findAll();
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    void getById() {
        //Подготовка входных данных
        Department newDepartment1 = new Department(123, "001", "Dept1");
        Department newDepartment2 = new Department(456, "002", "Dept2");
        List<Department> allDepts = new ArrayList<>();
        allDepts.add(newDepartment1);
        allDepts.add(newDepartment2);
        Optional<Department>  optionalDepartment = allDepts.stream()
                .filter(department -> department.getId().equals(123))
                .findFirst();


        //Подготовка ожидаемого результата
        DepartmentResponse expectedResponse = new DepartmentResponse();
        expectedResponse.setId(123);
        expectedResponse.setCode("001");
        expectedResponse.setName("Dept1");

        //Начало теста
        when(departmentRepository.findById(any())).thenReturn(optionalDepartment);
        DepartmentResponse actualResponse = departmentService.getById(123);
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository).findById(any());
        verifyNoMoreInteractions(departmentRepository);

    }

    @Test
    void getByCode() {
        //Подготовка входных данных
        Department newDepartment1 = new Department(123, "001", "Dept1");
        Department newDepartment2 = new Department(456, "002", "Dept2");
        List<Department> allDepts = new ArrayList<>();
        allDepts.add(newDepartment1);
        allDepts.add(newDepartment2);
        Optional<Department>  optionalDepartment = allDepts.stream()
                .filter(department -> department.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        DepartmentResponse expectedResponse = new DepartmentResponse();
        expectedResponse.setId(123);
        expectedResponse.setCode("001");
        expectedResponse.setName("Dept1");

        //Начало теста
        when(departmentRepository.findByCode(any())).thenReturn(optionalDepartment);
        DepartmentResponse actualResponse = departmentService.getByCode("001");
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository).findByCode(any());
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    void update() {
        //Подготовка входных данных
        UpdateDeptRequest request = new UpdateDeptRequest();
        request.setId(123);
        request.setCode("003");
        request.setName("Dept3");

        Department newDepartment1 = new Department(123, "001", "Dept1");
        Department newDepartment2 = new Department(456, "002", "Dept2");
        List<Department> allDepts = new ArrayList<>();
        allDepts.add(newDepartment1);
        allDepts.add(newDepartment2);
        Optional<Department>  optionalDepartment = allDepts.stream()
                .filter(department -> department.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        DepartmentResponse expectedResponse = new DepartmentResponse();
        expectedResponse.setId(123);
        expectedResponse.setCode("003");
        expectedResponse.setName("Dept3");

        //Начало теста
        when(departmentRepository.findById(any())).thenReturn(optionalDepartment);
        when(departmentRepository.update(any())).thenReturn(newDepartment1);
        DepartmentResponse actualResponse = departmentService.update(request);
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository).findById(any());
        verify(departmentRepository).update(any());
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    void delete() {
        //Подготовка входных данных
        Integer deleteDept = 123;

        Department newDepartment1 = new Department(123, "001", "Dept1");
        Department newDepartment2 = new Department(456, "002", "Dept2");
        List<Department> allDepts = new ArrayList<>();
        allDepts.add(newDepartment1);
        allDepts.add(newDepartment2);
        Optional<Department>  optionalDepartment = allDepts.stream()
                .filter(department -> department.getId().equals(123))
                .findFirst();

        //Подготовка ожидаемого результата
        DepartmentResponse expectedResponse = new DepartmentResponse();
        expectedResponse.setId(123);
        expectedResponse.setCode("001");
        expectedResponse.setName("Dept1");

        //Начало теста
        when(departmentRepository.findById(any())).thenReturn(optionalDepartment);
        when(departmentRepository.delete(any())).thenReturn(newDepartment1);
        DepartmentResponse actualResponse = departmentService.delete(deleteDept);
        assertEquals(expectedResponse, actualResponse);
        verify(departmentRepository).findById(any());
        verify(departmentRepository).delete(any());
        verifyNoMoreInteractions(departmentRepository);
    }
}