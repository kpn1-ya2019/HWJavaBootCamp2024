package com.colvir.accountant.service;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.exception.EmpNotFoundException;
import com.colvir.accountant.mapper.EmployeeMapper;
import com.colvir.accountant.model.Employee;
import com.colvir.accountant.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;

    private final EmployeeRepository employeeRepository;

    private final Random randomEmp = new Random();

    public GenerateEmpResponse generateEmp(GenerateEmpRequest request) {
        Integer   idDepartment = request.getIdDepartment();
        String surname = request.getSurname();
        String name = request.getName();
        String patronymic = request.getPatronymic();
        Double salary = request.getSalary();
        Employee newEmployee = new Employee(randomEmp.nextInt(), idDepartment, surname, name, patronymic, salary);
        employeeRepository.save(newEmployee);
        return  employeeMapper.empToGenerateEmpResponse(newEmployee);
    }

    public EmpPageResponse getAll() {
        List<Employee> allEmployees = employeeRepository.findAll();
        return employeeMapper.employeesToEmpPageResponse(allEmployees);
    }

    public EmployeeResponse getByIdAndIdDept(Integer id, Integer idDepartment) {
        Employee employee = employeeRepository.findByIdAndIdDept(id, idDepartment)
                .orElseThrow(()-> new EmpNotFoundException(String.format("%s с id = %s из %s с id = %s не найден", "Сотрудник", id, "подразделения", idDepartment)));
        return employeeMapper.empToEmpResponse(employee);
    }

    public EmployeeResponse update(UpdateEmployeeRequest request) {
        Integer employeeId = request.getId();
        Integer employeeIdDept = request.getIdDepartment();
        Employee employee = employeeRepository.findByIdAndIdDept(employeeId, employeeIdDept)
                .orElseThrow(() -> new EmpNotFoundException(String.format("%s с id = %s из %s с id = %s не найден", "Сотрудник", employeeId, "подразделения", employeeIdDept)));

        Employee updatedEmp = employeeMapper.updateEmpRequestToEmp(request);

        employeeRepository.update(updatedEmp);

        return employeeMapper.empToEmpResponse(updatedEmp);
    }

    public EmployeeResponse delete(Integer id, Integer idDepartment) {

        Employee employee = employeeRepository.findByIdAndIdDept(id, idDepartment)
                .orElseThrow(() -> new EmpNotFoundException(String.format("%s с id = %s из %s с id = %s не найден", "Сотрудник", id, "подразделения", idDepartment)));

        employeeRepository.delete(id, idDepartment);

        return employeeMapper.empToEmpResponse(employee);
    }
}
