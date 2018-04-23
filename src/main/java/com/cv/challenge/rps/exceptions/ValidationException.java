package com.cv.challenge.rps.exceptions;

import com.cv.challenge.rps.domain.enums.ValidationErrorEnum;

public class ValidationException extends RuntimeException {

    private ValidationErrorEnum errorCode;

    public ValidationException(String error, ValidationErrorEnum errorCode) {
        super(error);
        this.errorCode = errorCode;
    }

    public ValidationErrorEnum getErrorCode() {
        return errorCode;
    }
}
