package com.smb.springcloudgateway.exceptions;

public class IntegrationException extends RuntimeException {
    public IntegrationException(String msg) {
        super(msg);
    }
}
