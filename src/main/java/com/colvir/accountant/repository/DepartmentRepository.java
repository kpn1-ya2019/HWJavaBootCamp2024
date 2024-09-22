package com.colvir.accountant.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.Department;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class DepartmentRepository {

    private final SessionFactory sessionFactory;

  public Department save(Department department) {

      Session session = sessionFactory.getCurrentSession();
      session.persist(department);
      return department;
  }

  public List<Department> findAll() {

      Session session = sessionFactory.getCurrentSession();

      return session.createQuery("select d from Department d", Department.class)
              .getResultList();

  }

  public Optional<Department> findById(Integer id) {

      Session session = sessionFactory.getCurrentSession();

      return session.createQuery("select d from Department d where d.id = :id", Department.class)
              .setParameter("id", id)
              .getResultList().stream().findFirst();
  }

  public Department update(Department updatedDept) {


      Session session = sessionFactory.getCurrentSession();

      Department deptForUpdate = session.get(Department.class, updatedDept.getId());

      deptForUpdate.setCode(updatedDept.getCode());
      deptForUpdate.setName(updatedDept.getName());

      return updatedDept;
  }

  public Department delete(Integer id) {

      Session session = sessionFactory.getCurrentSession();

      Department deptForDelete = session.get(Department.class, id);

      session.remove(deptForDelete);

      return deptForDelete;
  }

  public Department getByCode(String deptCode) {

      Session session = sessionFactory.getCurrentSession();

      return session.createQuery("select d from Department d where d.code = :deptCode", Department.class)
              .setParameter("deptCode", deptCode)
              .getResultList().stream().findFirst().get();

  }

  public Department getByName(String deptName) {


      Session session = sessionFactory.getCurrentSession();

      return session.createQuery("select d from Department d where d.name = :deptName", Department.class)
              .setParameter("deptName", deptName)
              .getResultList().stream().findFirst().get();

  }

  public Optional<Department> findByCode(String deptCode) {

      Session session = sessionFactory.getCurrentSession();

      return session.createQuery("select d from Department d where d.code = :deptCode", Department.class)
              .setParameter("deptCode", deptCode)
              .getResultList().stream().findFirst();

  }
  public Department generateNewDepartment(String deptCode, String deptName) {

      Session session = sessionFactory.getCurrentSession();

      Optional<Department> optDepartment = session.createQuery("select d from Department d where d.code = :deptCode", Department.class)
                .setParameter("deptCode", deptCode)
                .setFirstResult(0)
                .setMaxResults(1)
                .uniqueResultOptional();
      if (optDepartment.isEmpty()) {
          Department newDepartment = new Department(deptCode, deptName);
          return save(newDepartment);
      } else {
          return optDepartment.stream().findFirst().get();
      }
  }

}

