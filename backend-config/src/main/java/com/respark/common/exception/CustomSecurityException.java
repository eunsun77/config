package com.respark.common.exception;

import lombok.Getter;

@Getter
public class CustomSecurityException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomSecurityException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
