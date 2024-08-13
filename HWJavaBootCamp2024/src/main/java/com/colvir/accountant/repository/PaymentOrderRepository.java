package com.colvir.accountant.repository;

<<<<<<< HEAD
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
=======
<<<<<<< HEAD
>>>>>>> master
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.PaymentOrder;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentOrderRepository {
<<<<<<< HEAD

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<PaymentOrder> beanPropertyRowMapper = new BeanPropertyRowMapper<>(PaymentOrder.class);

    public PaymentOrder save(PaymentOrder paymentOrder) {

        String preparedStatementString = "INSERT INTO paymentorders(id, idtype, iddepartment, idemployee, datepayment, amount ) VALUES(?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(preparedStatementString, paymentOrder.getId(), paymentOrder.getIdType(), paymentOrder.getIdDepartment(),
                paymentOrder.getIdEmployee(), paymentOrder.getDatePayment(), paymentOrder.getAmount());

=======
=======
import com.colvir.accountant.model.PaymentOrder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Repository
public class PaymentOrderRepository {
    private final Set<PaymentOrder> paymentOrders = new HashSet<>();

    public PaymentOrder save(PaymentOrder paymentOrder) {
        paymentOrders.add(paymentOrder);
>>>>>>> master
        return paymentOrder;
    }

    public List<PaymentOrder> findAll() {
<<<<<<< HEAD
        String statementString = "SELECT * FROM paymentorders";
        return jdbcTemplate.query(statementString, beanPropertyRowMapper);
    }

    public Optional<PaymentOrder> findById(Integer id) {
        String statementString = "SELECT * FROM paymentorders WHERE ID = ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{id}).stream().findFirst();
    }

    public PaymentOrder update(PaymentOrder pmtForUpdate) {

            String statementString = "UPDATE paymentorders SET  " +
                    "idtype = ?, " +
                    "iddepartment = ?, " +
                    "idemployee = ?, " +
                    "datepayment = ?, " +
                    "amount = ? " +
                    "WHERE id = ? ";

            jdbcTemplate.update(statementString,
                    pmtForUpdate.getIdType(),
                    pmtForUpdate.getIdDepartment(),
                    pmtForUpdate.getIdEmployee(),
                    pmtForUpdate.getDatePayment(),
                    pmtForUpdate.getAmount(),
                    pmtForUpdate.getId());


=======
        return new ArrayList<>(paymentOrders);
    }

    public Optional<PaymentOrder> findById(Integer id) {
        return paymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(id))
                .findFirst();
    }

    public PaymentOrder update(PaymentOrder pmtForUpdate) {
        for (PaymentOrder paymentOrder : paymentOrders) {
            if (paymentOrder.getId().equals(pmtForUpdate.getId())) {
                paymentOrder.setIdType(pmtForUpdate.getIdType());
                paymentOrder.setIdEmployee(pmtForUpdate.getIdEmployee());
                paymentOrder.setDatePayment(pmtForUpdate.getDatePayment());
                paymentOrder.setAmount(pmtForUpdate.getAmount());
            }
        }
>>>>>>> master
        return pmtForUpdate;
    }

    public PaymentOrder delete(Integer id) {
<<<<<<< HEAD
        PaymentOrder pmtForDelete = findById(id).get();

        String statementString = "DELETE FROM paymentorders WHERE id = ?";

        jdbcTemplate.update(statementString, id);

        return pmtForDelete;
    }
    public  Integer generateIdPaymentOrder() {
            Integer id = jdbcTemplate.query("SELECT nextval('paymentorder_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence paymentorder_seq.");
                        }
                    });

        return id;
=======
        PaymentOrder pmtForDelete = paymentOrders.stream()
                .filter(paymentOrder -> paymentOrder.getId().equals(id))
                .findFirst().get();
        paymentOrders.remove(pmtForDelete);
        return pmtForDelete;
    }

    public PaymentOrder getByAllFld( Integer   pmtOrderIdType,
                                     Integer   pmtOrderIdDepartment,
                                     Integer   pmtOrderIdEmployee,
                                     LocalDate   pmtOrderDatePayment,
                                     Double pmtOrderAmount) {
        return paymentOrders.stream()
                .filter(paymentOrder ->  (
                        paymentOrder.getIdType().equals(pmtOrderIdType)            &&
                        paymentOrder.getIdDepartment().equals(pmtOrderIdDepartment) &&
                        paymentOrder.getIdEmployee().equals(pmtOrderIdEmployee) )  &&
                        paymentOrder.getDatePayment().equals(pmtOrderDatePayment)  &&
                        String.format("%.2f",paymentOrder.getAmount()) == String.format("%.2f",pmtOrderAmount)
                )
                .findFirst()
                .orElse(null);
    }

    public  Integer generateIdPaymentOrder() {
        Random randomPaymentOrder = new Random();
        return randomPaymentOrder.nextInt();
>>>>>>> master
    }
    public PaymentOrder generateNewPaymentOrder( Integer   pmtOrderIdType,
                                                 Integer   pmtOrderIdDepartment,
                                                 Integer   pmtOrderIdEmployee,
                                                 LocalDate   pmtOrderDatePayment,
                                                 Double pmtOrderAmount
                                                 ) {

<<<<<<< HEAD
        PaymentOrder fndPaymentOrder;
        try {
            String statementString = "SELECT * FROM paymentorders WHERE "+
            "idtype = ? AND " +
            "iddepartment = ? AND " +
            "idemployee = ? AND " +
            "datepayment = ? AND " +
            "amount = ? ";

            fndPaymentOrder =  jdbcTemplate.queryForObject(statementString, beanPropertyRowMapper,
                    pmtOrderIdType,
                    pmtOrderIdDepartment,
                    pmtOrderIdEmployee,
                    pmtOrderDatePayment,
                    pmtOrderAmount);

        } catch (EmptyResultDataAccessException e) {
            fndPaymentOrder = null;
        }

=======
        PaymentOrder fndPaymentOrder=   getByAllFld(pmtOrderIdType, pmtOrderIdDepartment, pmtOrderIdEmployee, pmtOrderDatePayment, pmtOrderAmount);
>>>>>>> master
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
<<<<<<< HEAD
        }catch (DateTimeParseException e) {
            parsingDate = null;
=======
            System.out.println(parsingDate);
        }catch (DateTimeParseException e) {
            parsingDate = null;
            System.out.println("Нераспаршена с помощью " + e.getParsedString());
            System.out.println("Ошибка " + e.getMessage());
>>>>>>> master
        }
        return parsingDate;
    }

<<<<<<< HEAD
=======
>>>>>>> 68020e89b9af49acf8c8a6d413334b4b974d7bc9
>>>>>>> master
}
