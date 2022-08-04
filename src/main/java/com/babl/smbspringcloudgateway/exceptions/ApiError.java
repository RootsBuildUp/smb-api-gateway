package com.babl.smbspringcloudgateway.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiError {
    private String timestamp;

    private int status;

    private String error;

    private String message;

    private Object body;

    public ApiError(HttpStatus httpStatus, String errorMessage) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = httpStatus.value();
        this.error = httpStatus.toString();
        this.message = errorMessage;
    }

    public ApiError(HttpStatus httpStatus, String errorMessage, Object body) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = httpStatus.value();
        this.error = httpStatus.toString();
        this.message = errorMessage;
        this.body = body;
    }

    public ApiError(int code, String error, String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = code;
        this.error = error;
        this.message = message;
    }
}
