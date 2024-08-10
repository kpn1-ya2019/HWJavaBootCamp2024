package com.colvir.accountant.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.colvir.accountant.model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.PaymentType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentTypeRepository {
    private final SessionFactory sessionFactory;

    public PaymentType save(PaymentType paymentType) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(paymentType);

        return paymentType;
    }

    public List<PaymentType> findAll() {

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from PaymentType p", PaymentType.class)
                .getResultList();

    }
    public Optional<PaymentType> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from PaymentType p where p.id = :id", PaymentType.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    public PaymentType update(PaymentType updatedPmpType) {

        Session session = sessionFactory.getCurrentSession();

        PaymentType pmtForUpdate = session.get(PaymentType.class, updatedPmpType.getId());
        pmtForUpdate.setName(updatedPmpType.getName());
        return pmtForUpdate;
    }

    public PaymentType delete(Integer id) {

        Session session = sessionFactory.getCurrentSession();

        PaymentType pmtForDelete = session.get(PaymentType.class, id);

        session.remove(pmtForDelete);

        return pmtForDelete;

    }
    public PaymentType getByName(String pmtTypeName) {

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from PaymentType p where p.name = :pmtTypeName", PaymentType.class)
                .setParameter("pmtTypeName", pmtTypeName)
                .getResultList().stream().findFirst().get();
    }
    public PaymentType generateNewPaymentType(String pmtTypeName) {
        PaymentType fndPaymentType;

        Session session = sessionFactory.getCurrentSession();

        try {

            fndPaymentType = session.createQuery("select p from PaymentType p where p.name = :pmtTypeName", PaymentType.class)
                    .setParameter("pmtTypeName", pmtTypeName)
                    .getResultList().stream().findFirst().get();

            fndPaymentType = getByName(pmtTypeName);

        } catch (EmptyResultDataAccessException e) {
            fndPaymentType = null;
        }

        if (fndPaymentType == null) {
            PaymentType newPaymentType = new PaymentType(pmtTypeName);
            return save(newPaymentType);
        } else {
            return fndPaymentType;
        }
    }
    public PaymentType getById(Integer idPmtType) {

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from PaymentType p where p.id = :id", PaymentType.class)
                .setParameter("id", idPmtType)
                .getResultList().stream().findFirst().get();

    }
}
