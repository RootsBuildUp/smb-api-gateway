package com.smb.springcloudgateway.exceptions;

public class UserAccessException extends RuntimeException {
    public UserAccessException(String code) {
        super(code);
    }
}
