package com.colvir.accountant.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.PaymentType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentTypeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<PaymentType> beanPropertyRowMapper = new BeanPropertyRowMapper<>(PaymentType.class);


    public  Integer generateIdPmtType() {
            Integer id = jdbcTemplate.query("SELECT nextval('paymenttype_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence paymenttype_seq.");
                        }
                    });

        return id;
    }

    public PaymentType save(PaymentType paymentType) {
        String preparedStatementString = "INSERT INTO paymenttypes VALUES(?, ?);";
        jdbcTemplate.update(preparedStatementString, paymentType.getId(), paymentType.getName());
        return paymentType;
    }

    public List<PaymentType> findAll() {

        String statementString = "SELECT * FROM paymenttypes";
        return jdbcTemplate.query(statementString, beanPropertyRowMapper);

    }
    public Optional<PaymentType> findById(Integer id) {
        String statementString = "SELECT * FROM paymenttypes WHERE ID = ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{id}).stream().findFirst();
    }

    public PaymentType update(PaymentType pmtForUpdate) {

        String statementString = "UPDATE paymenttypes SET name = ? WHERE id = ?";

        jdbcTemplate.update(statementString, pmtForUpdate.getName(), pmtForUpdate.getId());

        return pmtForUpdate;
    }

    public PaymentType delete(Integer id) {

        PaymentType pmtForDelete = findById(id).get();

        String statementString = "DELETE FROM paymenttypes WHERE id = ?";

        jdbcTemplate.update(statementString, id);

        return pmtForDelete;
    }
    public PaymentType getByName(String pmtTypeName) {
        String statementString = "SELECT * FROM paymenttypes WHERE name = ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, pmtTypeName).stream()
                .findFirst().get();

    }
    public PaymentType generateNewPaymentType(String pmtTypeName) {
        PaymentType fndPaymentType;
        RowMapper<PaymentType> paymentTypeRowMapper = (rs, rowNum)-> 
        new PaymentType(rs.getInt("ID"),rs.getString("NAME"));
        try {
            String statementString = "SELECT * FROM paymenttypes WHERE name = ?";

            fndPaymentType =  jdbcTemplate.queryForObject(statementString, paymentTypeRowMapper, pmtTypeName);
    
            //fndPaymentType =   getByName(pmtTypeName);
        } catch (EmptyResultDataAccessException e) {
            fndPaymentType = null;
        }

        if (fndPaymentType == null) {
            Integer newId = generateIdPmtType();
            PaymentType newPaymentType = new PaymentType(newId, pmtTypeName);
            return save(newPaymentType);
        } else {
            return fndPaymentType;
        }
    }

    public PaymentType getById(Integer idPmtType) {

        String statementString = "SELECT * FROM paymenttypes WHERE id = ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, idPmtType).stream()
                .findFirst().get();
    }
}
