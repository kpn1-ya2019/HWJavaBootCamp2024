package com.colvir.accountant.repository;

import com.colvir.accountant.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EmployeeRepository {
    private final Set<Employee> employees = new HashSet<>();
    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }
    public Optional<Employee> findByIdAndIdDept(Long id, Long idDepartment) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id) &&
                        employee.getIdDepartment().equals(idDepartment))
                .findFirst();
    }

    // Сотрудник может работать по совместительству в нескольких подразделениях
    public Employee update(Employee empForUpdate) {
        for (Employee employee : employees) {
            if (employee.getId().equals(empForUpdate.getId()) &&
                    employee.getIdDepartment().equals(empForUpdate.getIdDepartment())) {
                employee.setName(empForUpdate.getName());
                employee.setPatronymic(empForUpdate.getPatronymic());
                employee.setSurname(empForUpdate.getSurname());
                employee.setSalary(empForUpdate.getSalary());
            }
        }
        return empForUpdate;
    }
    public Employee delete(Long id, Long idDepartment) {
        Employee empForDelete = employees.stream()
                .filter(employee -> employee.getId().equals(id) &&
                        employee.getIdDepartment().equals(idDepartment))
                .findFirst().get();
        employees.remove(empForDelete);
        return empForDelete;
    }

    public Employee getByNmPatSrName(String empName, String empPatronymic, String empSurname) {
        return employees.stream()
                .filter(employee -> employee.getName().equals(empName) &&
                                    employee.getPatronymic().equals(empPatronymic) &&
                                    employee.getSurname().equals(empSurname) )
                .findFirst()
                .orElse(null);
    }

    public Employee getBySrNamePatNm(String empSurname, String empPatronymic, String empName) {
        return getByNmPatSrName(empName, empPatronymic, empSurname);
    }
}
