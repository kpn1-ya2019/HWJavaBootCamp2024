package com.colvir.accountant.component;

import com.colvir.accountant.model.PaymentOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class GetPaymentOrderSequenceValueCallback implements BeforeConvertCallback<PaymentOrder> {

    private final Logger log = LogManager.getLogger(GetPaymentOrderSequenceValueCallback.class);

    private final JdbcTemplate jdbcTemplate;

    public GetPaymentOrderSequenceValueCallback(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PaymentOrder onBeforeConvert(PaymentOrder paymentOrder) {
        if (paymentOrder.getId() == null) {
            log.info("Get the paymentorder_seq.next value from a database sequence and use it as the primary key");

            Integer id = jdbcTemplate.query("SELECT nextval('paymentorder_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence paymentorder_seq.");
                        }
                    });
            paymentOrder.setId(id);
        }

        return paymentOrder;
    }
}
