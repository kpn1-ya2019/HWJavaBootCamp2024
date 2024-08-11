package com.colvir.accountant.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.AgrPaymentOrder;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class AgrPaymentOrderRepository {

    private final SessionFactory sessionFactory;
    private final Logger log = LogManager.getLogger(AgrPaymentOrderRepository.class);

    public AgrPaymentOrder save(AgrPaymentOrder agrPaymentOrder) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(agrPaymentOrder);
        return agrPaymentOrder;
    }

    public List<AgrPaymentOrder> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select a from AgrPaymentOrder a", AgrPaymentOrder.class)
                .getResultList();

    }
    public Optional<AgrPaymentOrder> findById(Integer id) {

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select a from AgrPaymentOrder a where a.id = :id", AgrPaymentOrder.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();

    }
    public List<AgrPaymentOrder>  findPmtTypeName(String pmtTypeName) {

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select a from AgrPaymentOrder a where a.paymentTypeName = :pmtTypeName", AgrPaymentOrder.class)
                .setParameter("pmtTypeName", pmtTypeName)
                .getResultList();

    }
    public AgrPaymentOrder update(AgrPaymentOrder updatedAgrPaymentOrder) {

        Session session = sessionFactory.getCurrentSession();

        AgrPaymentOrder pmtForUpdate = session.get(AgrPaymentOrder.class, updatedAgrPaymentOrder.getId());

        pmtForUpdate.setPaymentTypeName(updatedAgrPaymentOrder.getPaymentTypeName());
        pmtForUpdate.setDepartmentCode(updatedAgrPaymentOrder.getDepartmentCode());
        pmtForUpdate.setDepartmentName(updatedAgrPaymentOrder.getDepartmentName());
        pmtForUpdate.setEmployeeSurname(updatedAgrPaymentOrder.getEmployeeSurname());
        pmtForUpdate.setEmployeeName(updatedAgrPaymentOrder.getEmployeeName());
        pmtForUpdate.setEmployeePatronymic(updatedAgrPaymentOrder.getEmployeePatronymic());
        pmtForUpdate.setAmountPaymentOrder(updatedAgrPaymentOrder.getAmountPaymentOrder());

        return pmtForUpdate;
    }

    public AgrPaymentOrder delete(Integer id) {

        Session session = sessionFactory.getCurrentSession();

        AgrPaymentOrder pmtForDelete = session.get(AgrPaymentOrder.class, id);

        session.remove(pmtForDelete);

        return pmtForDelete;
    }

    public List<AgrPaymentOrder>  calculate(LocalDate dtFrom, LocalDate dtTo) {

        Session session = sessionFactory.getCurrentSession();

        Integer cntDelRows =  session.createQuery("delete from AgrPaymentOrder", AgrPaymentOrder.class).executeUpdate();

        log.info("Method delete {} rows before calculate", cntDelRows);

        String statementString = "INSERT INTO AgrPaymentOrder (paymentTypeName, departmentCode, departmentName, employeeSurname, employeeName, employeePatronymic, amountPaymentOrder) "+
                                 "SELECT p.name AS paymenttypename, d.code AS departmentcode, d.name AS departmentname, "+
                                 "       e.surname AS employeesurname, e.name AS employeename, e.patronymic AS employeepatronymic, sum(po.amount)  "+
                                 "  FROM PaymentOrder po "+
                                 "       INNER JOIN PaymentType p on (p.id = po.idType) "+
                                 "       INNER JOIN Department  d on (d.id = po.idDepartment) "+
                                 "       INNER JOIN Employee    e on (e.id = po.idEmployee and e.idDepartment = po.idDepartment) "+
                                 "  WHERE po.datePayment >= :dtFrom and po.datePayment <=:dtTo "+
                                 "  GROUP BY p.name, d.code, d.name, e.surname, e.name, e.patronymic";


        Integer  iRes =     session.createMutationQuery(statementString)
                                   .setParameter("dtFrom", dtFrom)
                                   .setParameter("dtTo", dtTo)
                                   .executeUpdate();
/*deprecated!!!
        Integer  iRes =  session.createQuery(statementString)
                .setParameter("dtFrom", dtFrom)
                .setParameter("dtTo", dtTo)
                .executeUpdate();
*/

        log.info("Method calculate do it {} rows", iRes);

        return session.createQuery("select a from AgrPaymentOrder a", AgrPaymentOrder.class)
                .getResultList();

  }

}
