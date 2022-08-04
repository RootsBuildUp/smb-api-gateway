package com.babl.smbspringcloudgateway.exceptions;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(String msg) {
        super(msg);
    }
}
