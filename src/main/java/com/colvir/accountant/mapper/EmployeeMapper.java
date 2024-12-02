package com.colvir.accountant.mapper;

import com.colvir.accountant.dto.*;
import com.colvir.accountant.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmployeeMapper {

    GenerateEmpResponse empToGenerateEmpResponse(Employee employee);

    EmployeeResponse empToEmpResponse(Employee employee);

    List<EmployeeResponse> employeesToEmpResponse(List<Employee> employees);

    Employee updateEmpRequestToEmp(UpdateEmployeeRequest request);

    default EmpPageResponse employeesToEmpPageResponse(List<Employee> employees) {
        List<EmployeeResponse> employeeResponses = employeesToEmpResponse(employees);
        return  new EmpPageResponse(employeeResponses);
    }
}
