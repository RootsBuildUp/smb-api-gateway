package com.smb.springcloudgateway.exceptions;

public class StartCaseException extends RuntimeException {
    public StartCaseException(String code) {
        super(code);
    }
}
