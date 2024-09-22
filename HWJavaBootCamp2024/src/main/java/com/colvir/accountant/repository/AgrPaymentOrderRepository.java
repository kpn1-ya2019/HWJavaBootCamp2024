package com.colvir.accountant.repository;

<<<<<<< HEAD
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
=======
import com.colvir.accountant.model.AgrPaymentOrder;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AgrPaymentOrderRepository {
    private final Set<AgrPaymentOrder> agrPaymentOrders = new HashSet<>();

    public AgrPaymentOrder save(AgrPaymentOrder agrPaymentOrder) {
        agrPaymentOrders.add(agrPaymentOrder);
>>>>>>> master
        return agrPaymentOrder;
    }

    public List<AgrPaymentOrder> findAll() {
<<<<<<< HEAD
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

=======
        return new ArrayList<>(agrPaymentOrders);
    }
    public Optional<AgrPaymentOrder> findById(Integer id) {
        return agrPaymentOrders.stream()
                .filter(agrPaymentOrder -> agrPaymentOrder.getId().equals(id))
                .findFirst();
    }
    public List<AgrPaymentOrder>  findPmtTypeName(String pmtTypeName) {

        return new ArrayList<>(agrPaymentOrders.stream()
                .filter(agrPaymentOrder -> agrPaymentOrder.getPaymentTypeName().equals(pmtTypeName))
                .collect(Collectors.toSet()));
    }
    public AgrPaymentOrder update(AgrPaymentOrder pmtForUpdate) {
        for (AgrPaymentOrder agrPaymentOrder : agrPaymentOrders) {
            if (agrPaymentOrder.getId().equals(pmtForUpdate.getId())) {
                agrPaymentOrder.setPaymentTypeName(pmtForUpdate.getPaymentTypeName());
                agrPaymentOrder.setDepartmentCode(pmtForUpdate.getDepartmentCode());
                agrPaymentOrder.setDepartmentName(pmtForUpdate.getDepartmentName());
                agrPaymentOrder.setEmployeeSurname(pmtForUpdate.getEmployeeSurname());
                agrPaymentOrder.setEmployeeName(pmtForUpdate.getEmployeeName());
                agrPaymentOrder.setEmployeePatronymic(pmtForUpdate.getEmployeePatronymic());
            }
        }
>>>>>>> master
        return pmtForUpdate;
    }

    public AgrPaymentOrder delete(Integer id) {
<<<<<<< HEAD

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
=======
        AgrPaymentOrder pmtForDelete = agrPaymentOrders.stream()
                .filter(agrPaymentOrder -> agrPaymentOrder.getId().equals(id))
                .findFirst().get();
        agrPaymentOrders.remove(pmtForDelete);
        return pmtForDelete;
    }
    public Integer generateIdAgrPaymentOrder() {
        Random randomId = new Random();
        return randomId.nextInt();
    }

>>>>>>> master

}
