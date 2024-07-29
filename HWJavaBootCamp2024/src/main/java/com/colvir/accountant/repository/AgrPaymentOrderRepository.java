package com.colvir.accountant.repository;

import com.colvir.accountant.model.AgrPaymentOrder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AgrPaymentOrderRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final BeanPropertyRowMapper<AgrPaymentOrder> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AgrPaymentOrder.class);

    public AgrPaymentOrder save(AgrPaymentOrder agrPaymentOrder) {
        String preparedStatementString = "INSERT INTO agrpaymentorders VALUES(?, ?, ?, ?, ?, ?, ?,?);";
        jdbcTemplate.update(preparedStatementString, agrPaymentOrder.getId(),
                                                     agrPaymentOrder.getPaymentTypeName(),
                                                     agrPaymentOrder.getDepartmentCode(),
                                                     agrPaymentOrder.getDepartmentName(),
                                                     agrPaymentOrder.getEmployeeSurname(),
                                                     agrPaymentOrder.getEmployeeName(),
                                                     agrPaymentOrder.getEmployeePatronymic(),
                                                     agrPaymentOrder.getAmountPaymentOrder());
        return agrPaymentOrder;
    }

    public List<AgrPaymentOrder> findAll() {
        String statementString = "SELECT * FROM agrpaymentorders";
        return jdbcTemplate.query(statementString, beanPropertyRowMapper);

    }
    public Optional<AgrPaymentOrder> findById(Integer id) {
        String statementString = "SELECT * FROM agrpaymentorders WHERE ID = ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{id}).stream().findFirst();
    }
    public List<AgrPaymentOrder>  findPmtTypeName(String pmtTypeName) {

        String statementString = "SELECT * FROM agrpaymentorders WHERE paymenttypename = ?";

        return jdbcTemplate.query(statementString, beanPropertyRowMapper, new Object[]{pmtTypeName});
    }
    public AgrPaymentOrder update(AgrPaymentOrder pmtForUpdate) {
        String statementString =
                "UPDATE agrpaymentorders SET " +
                        "paymentTypeName = ?, " +
                        "departmentCode      = ?, " +
                        "departmentName      = ?, " +
                        "employeeSurname     = ?, " +
                        "employeeName        = ?, " +
                        "employeePatronymic  = ?, " +
                        "amountPaymentOrder  = ? " +
                      "WHERE id = ?";

        jdbcTemplate.update(statementString,
                pmtForUpdate.getPaymentTypeName(),
                pmtForUpdate.getDepartmentCode(),
                pmtForUpdate.getDepartmentName(),
                pmtForUpdate.getEmployeeSurname(),
                pmtForUpdate.getEmployeeName(),
                pmtForUpdate.getEmployeePatronymic(),
                pmtForUpdate.getAmountPaymentOrder(),
                pmtForUpdate.getId()
        );

        return pmtForUpdate;
    }

    public AgrPaymentOrder delete(Integer id) {

        AgrPaymentOrder pmtForDelete = findById(id).get();

        String statementString = "DELETE FROM agrpaymentorders WHERE id = ?";

        jdbcTemplate.update(statementString, id);
        return pmtForDelete;
    }
    public Integer generateIdAgrPaymentOrder() {
        Random randomId = new Random();
        return randomId.nextInt();
    }


}
