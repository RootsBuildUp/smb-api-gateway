package com.smb.springcloudgateway.exceptions;

public class VariableNotSetException extends RuntimeException {
    public VariableNotSetException(String msg) {
        super(msg);
    }
}
