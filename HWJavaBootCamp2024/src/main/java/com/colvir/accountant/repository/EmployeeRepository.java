package com.colvir.accountant.repository;

<<<<<<< HEAD
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
=======
<<<<<<< HEAD
>>>>>>> master
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.Employee;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
<<<<<<< HEAD
    private final JdbcTemplate jdbcTemplate;
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
        return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{id, idDepartment}).stream().findFirst();
=======
=======
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
    public Optional<Employee> findByIdAndIdDept(Integer id, Integer idDepartment) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id) &&
                        employee.getIdDepartment().equals(idDepartment))
                .findFirst();
>>>>>>> master
    }

    // Сотрудник может работать по совместительству в нескольких подразделениях
    public Employee update(Employee empForUpdate) {
<<<<<<< HEAD

        String statementString = "UPDATE employees SET surname= ?, name= ?, patronymic= ?, salary = ? WHERE id = ? AND iddepartment = ?";

        jdbcTemplate.update(statementString, empForUpdate.getSurname(), empForUpdate.getName(), empForUpdate.getPatronymic(), empForUpdate.getSalary(), empForUpdate.getId(), empForUpdate.getIdDepartment());

        return empForUpdate;
    }
    public Employee delete(Integer id, Integer idDepartment) {

        Employee empForDelete = findByIdAndIdDept(id, idDepartment).get();

        String statementString = "DELETE FROM employees WHERE id = ? AND iddepartment = ?";

        jdbcTemplate.update(statementString, id, idDepartment);

=======
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
    public Employee delete(Integer id, Integer idDepartment) {
        Employee empForDelete = employees.stream()
                .filter(employee -> employee.getId().equals(id) &&
                        employee.getIdDepartment().equals(idDepartment))
                .findFirst().get();
        employees.remove(empForDelete);
>>>>>>> master
        return empForDelete;
    }

    public Employee getByNmPatSrName(String empName, String empPatronymic, String empSurname) {
<<<<<<< HEAD

        String statementString = "SELECT * FROM employees WHERE surname= ? AND name= ? AND patronymic= ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, empSurname, empName, empPatronymic).stream()
                .findFirst().get();
=======
        return employees.stream()
                .filter(employee -> employee.getName().equals(empName) &&
                                    employee.getPatronymic().equals(empPatronymic) &&
                                    employee.getSurname().equals(empSurname) )
                .findFirst()
                .orElse(null);
>>>>>>> master
    }

    public Employee getBySrNamePatNm(String empSurname, String empPatronymic, String empName) {
        return getByNmPatSrName(empName, empPatronymic, empSurname);
    }

    public  Integer generateIdEmp() {
<<<<<<< HEAD
            Integer id = jdbcTemplate.query("SELECT nextval('employee_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence employee_seq.");
                        }
                    });

        return id;
    }

    public Employee generateNewEmployee(Integer empIdDepartment, String empSurname, String empPatronymic, String empName, Double empSalary){
        Employee fndEmployee;
        try {
            String statementString = "SELECT * FROM employees WHERE surname= ? AND name= ? AND patronymic= ?";

            fndEmployee = jdbcTemplate.queryForObject(statementString, beanPropertyRowMapper, empSurname, empName, empPatronymic);
        
        } catch (EmptyResultDataAccessException e) {
            fndEmployee = null;
        }

=======
        Random randomIdEmp = new Random();
        return randomIdEmp.nextInt();
    }

    public Employee generateNewEmployee(Integer empIdDepartment, String empSurname, String empPatronymic, String empName, Double empSalary){
        Employee fndEmployee =   getByNmPatSrName(empName, empPatronymic, empSurname);
>>>>>>> master
        if (fndEmployee == null) {
            Integer newId = generateIdEmp();
            Employee newEmployee = new Employee(newId, empIdDepartment, empSurname, empPatronymic, empName, empSalary);
            return save(newEmployee);
        } else {
            return fndEmployee;
        }

    }


<<<<<<< HEAD
=======
>>>>>>> 68020e89b9af49acf8c8a6d413334b4b974d7bc9
>>>>>>> master
}
