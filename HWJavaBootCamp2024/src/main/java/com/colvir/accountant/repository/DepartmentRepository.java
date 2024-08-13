package com.colvir.accountant.repository;

<<<<<<< HEAD
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository {

}
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
