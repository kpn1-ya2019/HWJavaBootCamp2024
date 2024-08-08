package com.colvir.accountant.dto;

import com.colvir.accountant.model.InternalErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private InternalErrorStatus status;

    private String message;
}
