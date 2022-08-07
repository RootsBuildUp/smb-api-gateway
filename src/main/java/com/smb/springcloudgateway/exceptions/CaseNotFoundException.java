package com.smb.springcloudgateway.exceptions;


public class CaseNotFoundException extends RuntimeException {
    public CaseNotFoundException(String ss) {
        super(ss);
    }
}
