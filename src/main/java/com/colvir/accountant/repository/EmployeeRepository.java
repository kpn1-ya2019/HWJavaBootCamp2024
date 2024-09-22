package com.colvir.accountant.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.Employee;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class EmployeeRepository {

    private final SessionFactory sessionFactory;

    public Employee save(Employee employee) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(employee);

        return employee;
    }
    public List<Employee> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select e from Employee e", Employee.class)
                .getResultList();
    }
    public Optional<Employee> findByIdAndIdDept(Integer id, Integer idDepartment) {

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select e from Employee e where e.id = :id and e.idDepartment = :idDepartment", Employee.class)
                .setParameter("id", id)
                .setParameter("idDepartment", idDepartment)
                .getResultList().stream().findFirst();
    }

    // Сотрудник может работать по совместительству в нескольких подразделениях
    public Employee update(Employee updatedEmp) {

        Session session = sessionFactory.getCurrentSession();

        Employee empForUpdate = session.createQuery("select e from Employee e where e.id= :id AND e.idDepartment = :idDepartment", Employee.class)
                .setParameter("id", updatedEmp.getId())
                .setParameter("idDepartment", updatedEmp.getIdDepartment())
                .getResultList().stream().findFirst().get();

        empForUpdate.setIdDepartment(updatedEmp.getIdDepartment());
        empForUpdate.setSurname(updatedEmp.getSurname());
        empForUpdate.setName(updatedEmp.getName());
        empForUpdate.setPatronymic(updatedEmp.getPatronymic());
        empForUpdate.setSalary(updatedEmp.getSalary());
        return empForUpdate;

    }
    public Employee delete(Integer id, Integer idDepartment) {

        Session session = sessionFactory.getCurrentSession();

        Employee empForDelete = session.createQuery("select e from Employee e where e.id= :id AND e.idDepartment = :idDepartment", Employee.class)
                .setParameter("id", id)
                .setParameter("idDepartment", idDepartment)
                .getResultList().stream().findFirst().get();

        session.remove(empForDelete);

        return empForDelete;
    }

    public Employee getByNmPatSrName(String empName, String empPatronymic, String empSurname) {

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select e from Employee e where e.surname= :empSurname AND e.name= :empName AND e.patronymic=  :empPatronymic", Employee.class)
                .setParameter("empSurname", empSurname)
                .setParameter("empName", empName)
                .setParameter("empPatronymic", empPatronymic)
                .getResultList().stream().findFirst().get();

    }

    public Employee getBySrNamePatNm(String empSurname, String empPatronymic, String empName) {
        return getByNmPatSrName(empName, empPatronymic, empSurname);
    }

    public Employee generateNewEmployee(Integer empIdDepartment, String empSurname, String empPatronymic, String empName, Double empSalary){
        Session session = sessionFactory.getCurrentSession();

        Optional<Employee> optEmploee = 

         session.createQuery("select e from Employee e where e.surname= :empSurname AND e.name= :empName AND e.patronymic=  :empPatronymic", Employee.class)
                .setParameter("empSurname", empSurname)
                .setParameter("empName", empName)
                .setParameter("empPatronymic", empPatronymic)
                .setFirstResult(0)
                .setMaxResults(1)
                .uniqueResultOptional();
                
        if  ( optEmploee.isEmpty())
        {
            Employee newEmployee = new Employee(empIdDepartment, empSurname, empName, empPatronymic, empSalary);
            return save(newEmployee);
        }
        else {
            return optEmploee.stream().findFirst().get();        
        }

    }

}
