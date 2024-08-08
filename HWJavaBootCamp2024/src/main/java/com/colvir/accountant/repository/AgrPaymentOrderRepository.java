package com.colvir.accountant.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.colvir.accountant.model.AgrPaymentOrder;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AgrPaymentOrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<AgrPaymentOrder> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AgrPaymentOrder.class);
    private final Logger log = LogManager.getLogger(AgrPaymentOrderRepository.class);

    public AgrPaymentOrder save(AgrPaymentOrder agrPaymentOrder) {
        String preparedStatementString = "INSERT INTO agrpaymentorders (id, paymenttypename, departmentcode, departmentname, employeesurname, employeename, employeepatronymic, amountpaymentorder) VALUES(?, ?, ?, ?, ?, ?, ?,?);";
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
            Integer id = jdbcTemplate.query("SELECT nextval('agrpaymentorder_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence agrpaymentorder_seq.");
                        }
                    });

        return id;
    }

 

    public List<AgrPaymentOrder>  calculate(LocalDate dtFrom, LocalDate dtTo) {

        String statementString = "DELETE FROM agrpaymentorders";

        jdbcTemplate.update(statementString);

        statementString = "INSERT INTO agrpaymentorders(id, paymenttypename, departmentcode, departmentname, employeesurname, employeename, employeepatronymic, amountpaymentorder) "+
                                 "SELECT nextval('agrpaymentorder_seq'), p.name paymenttypename, d.code departmentcode, d.name departmentname, "+
                                 "       e.surname employeesurname, e.name employeename, e.patronymic employeepatronymic, sum(po.amount)  "+
                                 "  FROM paymentorders po "+
                                 "       INNER JOIN paymenttypes p on (p.id = po.idtype) "+
                                 "       INNER JOIN departments  d on (d.id = po.iddepartment) "+
                                 "       INNER JOIN employees    e on (e.id = po.idemployee and e.iddepartment = po.iddepartment) "+
                                 "  WHERE po.datepayment >= ? and po.datepayment <=? "+
                                 "  GROUP BY p.name, d.code, d.name, e.surname, e.name, e.patronymic";

        Integer iRes = jdbcTemplate.update(statementString, dtFrom, dtTo);
        log.info("Method calculate do it {} rows", iRes);

        statementString = "SELECT * FROM agrpaymentorders";
        return jdbcTemplate.query(statementString, beanPropertyRowMapper);



  }

}
