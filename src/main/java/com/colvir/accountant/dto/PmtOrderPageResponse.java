package com.colvir.accountant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PmtOrderPageResponse {

    private List<PaymentOrderResponse> paymentOrders;

}
