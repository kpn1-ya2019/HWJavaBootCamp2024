package com.colvir.accountant.repository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.PaymentOrder;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class PaymentOrderRepository {

    private final SessionFactory sessionFactory;
    public PaymentOrder save(PaymentOrder paymentOrder) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(paymentOrder);

        return paymentOrder;
    }

    public List<PaymentOrder> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select po from PaymentOrder po", PaymentOrder.class)
                .getResultList();
    }

    public Optional<PaymentOrder> findById(Integer id) {

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select po from PaymentOrder po where po.id = :id", PaymentOrder.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();

    }

    public PaymentOrder update(PaymentOrder updatedPaymentOrder) {

        Session session = sessionFactory.getCurrentSession();

        PaymentOrder pmtForUpdate = session.get(PaymentOrder.class, updatedPaymentOrder.getId());

        pmtForUpdate.setIdType(updatedPaymentOrder.getIdType());
        pmtForUpdate.setIdDepartment(updatedPaymentOrder.getIdDepartment());
        pmtForUpdate.setIdEmployee(updatedPaymentOrder.getIdEmployee());
        pmtForUpdate.setDatePayment(updatedPaymentOrder.getDatePayment());
        pmtForUpdate.setAmount(updatedPaymentOrder.getAmount());

        return pmtForUpdate;
    }

    public PaymentOrder delete(Integer id) {

        Session session = sessionFactory.getCurrentSession();

        PaymentOrder pmtForDelete = session.get(PaymentOrder.class, id);

        session.remove(pmtForDelete);

        return pmtForDelete;
    }
    public PaymentOrder generateNewPaymentOrder( Integer   pmtOrderIdType,
                                                 Integer   pmtOrderIdDepartment,
                                                 Integer   pmtOrderIdEmployee,
                                                 LocalDate   pmtOrderDatePayment,
                                                 Double pmtOrderAmount
                                                 ) {

        PaymentOrder fndPaymentOrder;
        Session session = sessionFactory.getCurrentSession();

        try {

            fndPaymentOrder = session.createQuery("select po from PaymentOrder po " +
                            "where po.idType = :pmtOrderIdType and po.idDepartment = :pmtOrderIdDepartment and " +
                            "      po.idEmployee = :pmtOrderIdEmployee and " +
                            "      po.datePayment = :pmtOrderDatePayment and" +
                            "      po.amount = :pmtOrderAmount", PaymentOrder.class)
                    .setParameter("pmtOrderIdType", pmtOrderIdType)
                    .setParameter("pmtOrderIdDepartment", pmtOrderIdDepartment)
                    .setParameter("pmtOrderIdEmployee", pmtOrderIdEmployee)
                    .setParameter("pmtOrderDatePayment", pmtOrderDatePayment)
                    .setParameter("pmtOrderAmount", pmtOrderAmount)
                    .getResultList().stream().findFirst().get();

        } catch (EmptyResultDataAccessException e) {
            fndPaymentOrder = null;
        }

        if (fndPaymentOrder == null) {
            PaymentOrder newPaymentOrder = new PaymentOrder( pmtOrderIdType, pmtOrderIdDepartment, pmtOrderIdEmployee, pmtOrderDatePayment, pmtOrderAmount);
            return save(newPaymentOrder);
        } else {
            return fndPaymentOrder;
        }
    }

    public LocalDate payStrToDate(String stringPayDate){
        LocalDate parsingDate ;
        try {
            parsingDate = LocalDate.parse(stringPayDate);
        }catch (DateTimeParseException e) {
            parsingDate = null;
        }
        return parsingDate;
    }

}
