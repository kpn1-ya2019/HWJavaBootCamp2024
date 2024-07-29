package com.colvir.accountant.repository;

import com.colvir.accountant.model.Department;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DepartmentRepository {
  private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
  private final BeanPropertyRowMapper<Department> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Department.class);

  public Department save(Department department) {

      String preparedStatementString = "INSERT INTO departments VALUES(?, ?, ?);";
      jdbcTemplate.update(preparedStatementString, department.getId(), department.getCode(), department.getName());
      return department;
  }

  public List<Department> findAll() {

      String statementString = "SELECT * FROM departments";
      return jdbcTemplate.query(statementString, beanPropertyRowMapper);

  }

  public Optional<Department> findById(Integer id) {
      String statementString = "SELECT * FROM departments WHERE ID = ?";

      return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{id}).stream().findFirst();

  }

  public Department update(Department deptForUpdate) {

      String statementString = "UPDATE departments SET code = ?, name = ? WHERE id = ?";

      jdbcTemplate.update(statementString, deptForUpdate.getCode(), deptForUpdate.getName(), deptForUpdate.getId());

      return deptForUpdate;
  }

  public Department delete(Integer id) {

      Department deptForDelete = findById(id).get();

      String statementString = "DELETE FROM departments WHERE id = ?";

      jdbcTemplate.update(statementString, id);


      return deptForDelete;
  }

  public Department getByCode(String deptCode) {

      String statementString = "SELECT * FROM departments WHERE code = ?";

      return jdbcTemplate.query(statementString, beanPropertyRowMapper, deptCode).stream()
              .findFirst().get();
  }

  public Department getByName(String deptName) {

      String statementString = "SELECT * FROM departments WHERE name = ?";

      return jdbcTemplate.query(statementString, beanPropertyRowMapper, deptName).stream()
              .findFirst().get();

  }

  public Optional<Department> findByCode(String code) {

      String statementString = "SELECT * FROM departments WHERE code = ?";

      return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{code}).stream().findFirst();

  }
    private Integer generateIdDept() {
        Random randomDept = new Random();
        return randomDept.nextInt();
    }
  public Department generateNewDepartment(String codeDept, String nameDept) {
        Department fndDepartment =   getByCode(codeDept);
        if (fndDepartment == null) {
            Integer newId = generateIdDept();
            Department newDepartment = new Department(newId, codeDept, nameDept);
            return save(newDepartment);
        } else {
            return fndDepartment;
        }
  }

}

