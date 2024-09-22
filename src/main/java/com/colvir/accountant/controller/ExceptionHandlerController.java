package com.colvir.accountant.controller;

import com.colvir.accountant.dto.ErrorResponse;
import com.colvir.accountant.exception.DeptNotFoundException;
import com.colvir.accountant.exception.EmpNotFoundException;
import com.colvir.accountant.exception.PmtOrderNotFoundException;
import com.colvir.accountant.exception.PmtTypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.colvir.accountant.model.InternalErrorStatus.*;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(DeptNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeptNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(DEPARTMENT_DOES_NOT_EXIST, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmpNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmpNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(EMPLOYEE_DOES_NOT_EXIST, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PmtOrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePmtOrderNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(PAYMENT_ORDER_DOES_NOT_EXIST, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PmtTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePmtTypeNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(PAYMENT_TYPE_DOES_NOT_EXIST, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
