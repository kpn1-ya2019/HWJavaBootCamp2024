package com.colvir.accountant.repository;

import com.colvir.accountant.model.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final BeanPropertyRowMapper<Employee> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Employee.class);

    public Employee save(Employee employee) {
        String preparedStatementString = "INSERT INTO employees(ID, IDDEPARTMENT, SURNAME, NAME, PATRONYMIC, salary)" +
                " VALUES(?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(preparedStatementString,
                employee.getId(),
                employee.getIdDepartment(),
                employee.getSurname(),
                employee.getName(),
                employee.getPatronymic(),
                employee.getSalary()
        );

        return employee;
    }
    public List<Employee> findAll() {
        String statementString = "SELECT * FROM employees";
        return jdbcTemplate.query(statementString, beanPropertyRowMapper);
    }
    public Optional<Employee> findByIdAndIdDept(Integer id, Integer idDepartment) {

        String statementString = "SELECT * FROM employees WHERE id = ? AND iddepartment = ?";
        return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{id}, new Object[]{idDepartment}).stream().findFirst();
    }

    // Сотрудник может работать по совместительству в нескольких подразделениях
    public Employee update(Employee empForUpdate) {

        String statementString = "UPDATE employees SET surname= ?, name= ?, patronymic= ?, salary = ? WHERE id = ? AND iddepartment = ?";

        jdbcTemplate.update(statementString, empForUpdate.getSurname(), empForUpdate.getName(), empForUpdate.getPatronymic(), empForUpdate.getSalary(), empForUpdate.getId(), empForUpdate.getIdDepartment());

        return empForUpdate;
    }
    public Employee delete(Integer id, Integer idDepartment) {

        Employee empForDelete = findByIdAndIdDept(id, idDepartment).get();

        String statementString = "DELETE FROM employees WHERE id = ? AND iddepartment = ?";

        jdbcTemplate.update(statementString, id, idDepartment);

        return empForDelete;
    }

    public Employee getByNmPatSrName(String empName, String empPatronymic, String empSurname) {

        String statementString = "SELECT * FROM employees WHERE surname= ?, name= ?, patronymic= ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, empSurname, empName, empPatronymic).stream()
                .findFirst().get();
    }

    public Employee getBySrNamePatNm(String empSurname, String empPatronymic, String empName) {
        return getByNmPatSrName(empName, empPatronymic, empSurname);
    }

    public  Integer generateIdEmp() {
        Random randomIdEmp = new Random();
        return randomIdEmp.nextInt();
    }

    public Employee generateNewEmployee(Integer empIdDepartment, String empSurname, String empPatronymic, String empName, Double empSalary){
        Employee fndEmployee =   getByNmPatSrName(empName, empPatronymic, empSurname);
        if (fndEmployee == null) {
            Integer newId = generateIdEmp();
            Employee newEmployee = new Employee(newId, empIdDepartment, empSurname, empPatronymic, empName, empSalary);
            return save(newEmployee);
        } else {
            return fndEmployee;
        }

    }


}
