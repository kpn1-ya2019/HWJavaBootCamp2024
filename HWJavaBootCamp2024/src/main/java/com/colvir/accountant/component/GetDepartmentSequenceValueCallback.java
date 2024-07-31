package com.colvir.accountant.component;

import com.colvir.accountant.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class GetDepartmentSequenceValueCallback implements BeforeConvertCallback<Department> {

    private final Logger log = LogManager.getLogger(GetDepartmentSequenceValueCallback.class);

    private final JdbcTemplate jdbcTemplate;

    public GetDepartmentSequenceValueCallback(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Department onBeforeConvert(Department department) {
        if (department.getId() == null) {
            log.info("Get the department_seq.next value from a database sequence and use it as the primary key");

            Integer id = jdbcTemplate.query("SELECT nextval('department_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence department_seq.");
                        }
                    });
            department.setId(id);
        }

        return department;
    }
}
