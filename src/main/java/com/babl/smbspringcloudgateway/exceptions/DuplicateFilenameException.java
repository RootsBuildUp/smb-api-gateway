package com.babl.smbspringcloudgateway.exceptions;

public class DuplicateFilenameException extends RuntimeException {
    private Object body;

    public DuplicateFilenameException(String msg, Object body) {
        super(msg);
        this.body = body;
    }

    public Object getBody() {
        return body;
    }
}
