package com.colvir.accountant.exception;

public class DeptNotFoundException extends RuntimeException {
    public DeptNotFoundException(String message) {
        super(message);
    }

}
