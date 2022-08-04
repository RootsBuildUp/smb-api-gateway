package com.babl.smbspringcloudgateway.exceptions;

public class UserAccessException extends RuntimeException {
    public UserAccessException(String code) {
        super(code);
    }
}
