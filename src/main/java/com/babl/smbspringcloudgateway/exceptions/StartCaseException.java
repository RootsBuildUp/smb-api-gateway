package com.babl.smbspringcloudgateway.exceptions;

public class StartCaseException extends RuntimeException {
    public StartCaseException(String code) {
        super(code);
    }
}
