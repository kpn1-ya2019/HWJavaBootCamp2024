package com.colvir.accountant.repository;

import com.colvir.accountant.model.PaymentOrder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Repository
public class PaymentOrderRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final BeanPropertyRowMapper<PaymentOrder> beanPropertyRowMapper = new BeanPropertyRowMapper<>(PaymentOrder.class);

    public PaymentOrder save(PaymentOrder paymentOrder) {

        String preparedStatementString = "INSERT INTO paymentorders VALUES(?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(preparedStatementString, paymentOrder.getId(), paymentOrder.getIdType(), paymentOrder.getIdDepartment(),
                paymentOrder.getIdEmployee(), paymentOrder.getDatePayment(), paymentOrder.getAmount());

        return paymentOrder;
    }

    public List<PaymentOrder> findAll() {
        String statementString = "SELECT * FROM paymentorders";
        return jdbcTemplate.query(statementString, beanPropertyRowMapper);
    }

    public Optional<PaymentOrder> findById(Integer id) {
        String statementString = "SELECT * FROM paymentorders WHERE ID = ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{id}).stream().findFirst();
    }

    public PaymentOrder update(PaymentOrder pmtForUpdate) {

            String statementString = "UPDATE paymentorders SET  " +
                    "idtype = ?," +
                    "iddepartment = ?," +
                    "idemployee = ?," +
                    "datepayment = ?," +
                    "amount = ?" +
                    "WHERE id = ?";

            jdbcTemplate.update(statementString,
                    pmtForUpdate.getIdType(),
                    pmtForUpdate.getIdDepartment(),
                    pmtForUpdate.getIdEmployee(),
                    pmtForUpdate.getDatePayment(),
                    pmtForUpdate.getAmount(),
                    pmtForUpdate.getId());


        return pmtForUpdate;
    }

    public PaymentOrder delete(Integer id) {
        PaymentOrder pmtForDelete = findById(id).get();

        String statementString = "DELETE FROM paymentorders WHERE id = ?";

        jdbcTemplate.update(statementString, id);

        return pmtForDelete;
    }

    public PaymentOrder getByAllFld( Integer   pmtOrderIdType,
                                     Integer   pmtOrderIdDepartment,
                                     Integer   pmtOrderIdEmployee,
                                     LocalDate   pmtOrderDatePayment,
                                     Double pmtOrderAmount) {

        String statementString = "SELECT * FROM paymentorders WHERE "+
                "idtype = ?," +
                "iddepartment = ?," +
                "idemployee = ?," +
                "datepayment = ?," +
                "amount = ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper,
                        pmtOrderIdType,
                        pmtOrderIdDepartment,
                        pmtOrderIdEmployee,
                        pmtOrderDatePayment,
                        pmtOrderAmount).stream()
                .findFirst().get() ;


    }

    public  Integer generateIdPaymentOrder() {
        Random randomPaymentOrder = new Random();
        return randomPaymentOrder.nextInt();
    }
    public PaymentOrder generateNewPaymentOrder( Integer   pmtOrderIdType,
                                                 Integer   pmtOrderIdDepartment,
                                                 Integer   pmtOrderIdEmployee,
                                                 LocalDate   pmtOrderDatePayment,
                                                 Double pmtOrderAmount
                                                 ) {

        PaymentOrder fndPaymentOrder=   getByAllFld(pmtOrderIdType, pmtOrderIdDepartment, pmtOrderIdEmployee, pmtOrderDatePayment, pmtOrderAmount);
        if (fndPaymentOrder == null) {
            Integer newId = generateIdPaymentOrder();
            PaymentOrder newPaymentOrder = new PaymentOrder(newId, pmtOrderIdType, pmtOrderIdDepartment, pmtOrderIdEmployee, pmtOrderDatePayment, pmtOrderAmount);
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
