package com.colvir.accountant.exception;

public class PmtOrderNotFoundException extends RuntimeException {
    public PmtOrderNotFoundException(String message) {
        super(message);
    }

}
