package com.colvir.accountant.service;

import com.colvir.accountant.config.TestConfig;
import com.colvir.accountant.dto.*;
import com.colvir.accountant.mapper.EmployeeMapper;
import com.colvir.accountant.model.Employee;
import com.colvir.accountant.repository.EmployeeRepository;
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
        EmployeeService.class,
        EmployeeMapper.class
})
@SpringBootTest(classes = {TestConfig.class})
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void generateEmp_success() {
        //Подготовка входных данных
        GenerateEmpRequest request = new GenerateEmpRequest();
        request.setSurname("Ivanov");
        request.setName("Ivan");
        request.setPatronymic("Ivanovich");
        request.setSalary(100.23);


        //Подготовка ожидаемого результата
        GenerateEmpResponse expectedResponse = new GenerateEmpResponse();
        expectedResponse.setSurname("Ivanov");
        expectedResponse.setName("Ivan");
        expectedResponse.setPatronymic("Ivanovich");
        expectedResponse.setSalary(100.23);

        //Начало теста

        when(employeeRepository.save(any())).thenReturn(any());

        GenerateEmpResponse actualResponse = employeeService.generateEmp(request);
        assertEquals(expectedResponse, actualResponse);
        verify(employeeRepository).save(any());
        verifyNoMoreInteractions(employeeRepository);

    }

    @Test
    void getAll() {
        //Подготовка входных данных
        Employee newEmployee1 = new Employee(123, 10123, "Ivanov","Ivan", "Ivanovich", 1023.45);
        Employee newEmployee2 = new Employee(234, 20456, "Sidorov","Sidor", "Sidorovich", 2045.67);
        List<Employee> allEmps =  new ArrayList<>();
        allEmps.add(newEmployee1);
        allEmps.add(newEmployee2);

        //Подготовка ожидаемого результата
        EmployeeResponse employeeResponse1 = new EmployeeResponse();
        employeeResponse1.setId(123);
        employeeResponse1.setIdDepartment(10123);
        employeeResponse1.setSurname("Ivanov");
        employeeResponse1.setName("Ivan");
        employeeResponse1.setPatronymic("Ivanovich");
        employeeResponse1.setSalary(1023.45);

        EmployeeResponse employeeResponse2 = new EmployeeResponse();
        employeeResponse2.setId(234);
        employeeResponse2.setIdDepartment(20456);
        employeeResponse2.setSurname("Sidorov");
        employeeResponse2.setName("Sidor");
        employeeResponse2.setPatronymic("Sidorovich");
        employeeResponse2.setSalary(2045.67);

        List<EmployeeResponse> allEmployeesResponse = new ArrayList<>();
        allEmployeesResponse.add(employeeResponse1);
        allEmployeesResponse.add(employeeResponse2);
        EmpPageResponse expectedResponse = new EmpPageResponse(allEmployeesResponse);

        //Начало теста

        when(employeeRepository.findAll()).thenReturn(allEmps);
        EmpPageResponse actualPageResponse = employeeService.getAll();

        assertEquals(expectedResponse, actualPageResponse);
        verify(employeeRepository, times(1)).findAll();
        verifyNoMoreInteractions(employeeRepository);

    }

    @Test
    void getByIdAndIdDept() {
        //Подготовка входных данных
        Employee newEmployee1 = new Employee(123, 10123, "Ivanov","Ivan", "Ivanovich", 1023.45);
        Employee newEmployee2 = new Employee(234, 20456, "Sidorov","Sidor", "Sidorovich", 2045.67);
        List<Employee> allEmps =  new ArrayList<>();
        allEmps.add(newEmployee1);
        allEmps.add(newEmployee2);
        Optional<Employee> optionalEmployee = allEmps.stream()
                .filter(employee -> employee.getId().equals(123) &&
                                    employee.getIdDepartment().equals(10123))
                .findFirst();

        //Подготовка ожидаемого результата
        EmployeeResponse expectedResponse = new EmployeeResponse();
        expectedResponse.setId(123);
        expectedResponse.setIdDepartment(10123);
        expectedResponse.setSurname("Ivanov");
        expectedResponse.setName("Ivan");
        expectedResponse.setPatronymic("Ivanovich");
        expectedResponse.setSalary(1023.45);

        //Начало теста

        when(employeeRepository.findByIdAndIdDept(any(), any())).thenReturn(optionalEmployee);
        EmployeeResponse actualResponse = employeeService.getByIdAndIdDept(123,10123);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeRepository, times(1)).findByIdAndIdDept(any(), any());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void update() {
        //Подготовка входных данных
        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setId(987);
        request.setIdDepartment(87654);
        request.setSurname("Petrov");
        request.setName("Petr");
        request.setPatronymic("Petrovich");
        request.setSalary(100.23);

        Employee newEmployee1 = new Employee(123, 10123, "Ivanov","Ivan", "Ivanovich", 1023.45);
        Employee newEmployee2 = new Employee(234, 20456, "Sidorov","Sidor", "Sidorovich", 2045.67);
        List<Employee> allEmps =  new ArrayList<>();
        allEmps.add(newEmployee1);
        allEmps.add(newEmployee2);
        Optional<Employee> optionalEmployee = allEmps.stream()
                .filter(employee -> employee.getId().equals(123) &&
                        employee.getIdDepartment().equals(10123))
                .findFirst();

        //Подготовка ожидаемого результата
        EmployeeResponse expectedResponse = new EmployeeResponse();
        expectedResponse.setId(987);
        expectedResponse.setIdDepartment(87654);
        expectedResponse.setSurname("Petrov");
        expectedResponse.setName("Petr");
        expectedResponse.setPatronymic("Petrovich");
        expectedResponse.setSalary(100.23);

        //Начало теста

        when(employeeRepository.findByIdAndIdDept(any(), any())).thenReturn(optionalEmployee);
        when(employeeRepository.update(any())).thenReturn(newEmployee1);
        EmployeeResponse actualResponse = employeeService.update(request);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeRepository, times(1)).findByIdAndIdDept(any(), any());
        verify(employeeRepository, times(1)).update(any());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void delete() {
        Integer deleteIdEmp = 123;
        Integer deleteIdDept = 10123;
        Employee newEmployee1 = new Employee(123, 10123, "Ivanov","Ivan", "Ivanovich", 1023.45);
        Employee newEmployee2 = new Employee(234, 20456, "Sidorov","Sidor", "Sidorovich", 2045.67);
        List<Employee> allEmps =  new ArrayList<>();
        allEmps.add(newEmployee1);
        allEmps.add(newEmployee2);
        Optional<Employee> optionalEmployee = allEmps.stream()
                .filter(employee -> employee.getId().equals(123) &&
                        employee.getIdDepartment().equals(10123))
                .findFirst();

        //Подготовка ожидаемого результата
        EmployeeResponse expectedResponse = new EmployeeResponse();
        expectedResponse.setId(123);
        expectedResponse.setIdDepartment(10123);
        expectedResponse.setSurname("Ivanov");
        expectedResponse.setName("Ivan");
        expectedResponse.setPatronymic("Ivanovich");
        expectedResponse.setSalary(1023.45);

        //Начало теста

        when(employeeRepository.findByIdAndIdDept(any(), any())).thenReturn(optionalEmployee);
        when(employeeRepository.delete(any(), any())).thenReturn(newEmployee1);
        EmployeeResponse actualResponse = employeeService.delete(deleteIdEmp, deleteIdDept);

        assertEquals(expectedResponse, actualResponse);
        verify(employeeRepository, times(1)).findByIdAndIdDept(any(), any());
        verify(employeeRepository, times(1)).delete(any(), any());
        verifyNoMoreInteractions(employeeRepository);
    }
}