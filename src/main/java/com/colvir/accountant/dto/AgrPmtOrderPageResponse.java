package com.colvir.accountant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AgrPmtOrderPageResponse {

    private List<AgrPaymentOrderResponse> agrPaymentOrders;

}
