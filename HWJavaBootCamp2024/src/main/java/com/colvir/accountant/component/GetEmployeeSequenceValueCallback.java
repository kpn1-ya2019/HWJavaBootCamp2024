package com.colvir.accountant.component;

import com.colvir.accountant.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class GetEmployeeSequenceValueCallback implements BeforeConvertCallback<Employee> {

    private final Logger log = LogManager.getLogger(GetEmployeeSequenceValueCallback.class);

    private final JdbcTemplate jdbcTemplate;

    public GetEmployeeSequenceValueCallback(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee onBeforeConvert(Employee employee) {
        if (employee.getId() == null) {
            log.info("Get the employee_seq.next value from a database sequence and use it as the primary key");

            Integer id = jdbcTemplate.query("SELECT nextval('employee_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence employee_seq.");
                        }
                    });
            employee.setId(id);
        }

        return employee;
    }
}
