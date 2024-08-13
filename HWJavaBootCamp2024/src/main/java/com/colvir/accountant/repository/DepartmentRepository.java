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

import com.colvir.accountant.model.Department;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DepartmentRepository {
  private final JdbcTemplate jdbcTemplate;

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
  public Integer generateIdDept() {
            Integer id = jdbcTemplate.query("SELECT nextval('department_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence department_seq.");
                        }
                    });

        return id;
    }
  public Department generateNewDepartment(String codeDept, String nameDept) {
        Department fndDepartment;

        try {
            String statementString = "SELECT * FROM departments WHERE code = ?";

            fndDepartment = jdbcTemplate.queryForObject(statementString, beanPropertyRowMapper, codeDept);
        
        } catch (EmptyResultDataAccessException e) {
            fndDepartment = null;
        }
  
        if (fndDepartment == null) {
            Integer newId = generateIdDept();
            Department newDepartment = new Department(newId, codeDept, nameDept);
            return save(newDepartment);
        } else {
            return fndDepartment;
        }
  }

}
<<<<<<< HEAD

=======
=======
import com.colvir.accountant.model.Department;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DepartmentRepository {
  private final Set<Department> departments = new HashSet<>();

  public Department save(Department department) {
      departments.add(department);
      return department;
  }

  public List<Department> findAll() {
      return new ArrayList<Department>(departments);
  }

  public Optional<Department> findById(Integer id) {
      return departments.stream()
              .filter(department -> department.getId().equals(id))
              .findFirst();

  }

  public Department update(Department deptForUpdate) {
      for (Department department: departments) {
          if (department.getId().equals(deptForUpdate.getId())) {
              department.setCode(deptForUpdate.getCode());
              department.setName(deptForUpdate.getName());
          }
      }
      return deptForUpdate;
  }

  public Department delete(Integer id) {
      Department deptForDelete = departments.stream()
                  .filter(department -> department.getId().equals(id))
                  .findFirst().get();
      departments.remove(deptForDelete);
      return deptForDelete;
  }

  public Department getByCode(String deptCode) {
      return departments.stream()
              .filter(department -> department.getCode().equals(deptCode))
              .findFirst()
              .orElse(null);
  }

  public Department getByName(String deptName) {
      return departments.stream()
              .filter(department -> department.getCode().equals(deptName))
              .findFirst()
              .orElse(null);
  }

  public Optional<Department> findByCode(String code) {
        return departments.stream()
                .filter(department -> department.getCode().equals(code))
                .findFirst();
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

>>>>>>> 68020e89b9af49acf8c8a6d413334b4b974d7bc9
>>>>>>> master
