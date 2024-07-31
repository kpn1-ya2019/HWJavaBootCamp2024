package com.colvir.accountant.component;

import com.colvir.accountant.model.PaymentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class GetPaymentTypeSequenceValueCallback implements BeforeConvertCallback<PaymentType> {

    private final Logger log = LogManager.getLogger(GetPaymentTypeSequenceValueCallback.class);

    private final JdbcTemplate jdbcTemplate;

    public GetPaymentTypeSequenceValueCallback(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PaymentType onBeforeConvert(PaymentType paymentType) {
        if (paymentType.getId() == null) {
            log.info("Get the paymenttype_seq.next value from a database sequence and use it as the primary key");

            Integer id = jdbcTemplate.query("SELECT nextval('paymenttype_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getInt(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence paymenttype_seq.");
                        }
                    });
            paymentType.setId(id);
        }

        return paymentType;
    }
}
