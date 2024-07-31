package com.colvir.accountant.component;

import com.colvir.accountant.model.AgrPaymentOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class GetAgrPaymentOrderSequenceValueCallback implements BeforeConvertCallback<AgrPaymentOrder> {

    private final Logger log = LogManager.getLogger(GetAgrPaymentOrderSequenceValueCallback.class);

    private final JdbcTemplate jdbcTemplate;

    public GetAgrPaymentOrderSequenceValueCallback(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AgrPaymentOrder onBeforeConvert(AgrPaymentOrder agrPaymentOrder) {
        if (agrPaymentOrder.getId() == null) {
            log.info("Get the agrpaymentorder_seq.next value from a database sequence and use it as the primary key");

            Integer id = jdbcTemplate.query("SELECT nextval('agrpaymentorder_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence agrpaymentorder_seq.");
                        }
                    });
            agrPaymentOrder.setId(id);
        }

        return agrPaymentOrder;
    }
}

