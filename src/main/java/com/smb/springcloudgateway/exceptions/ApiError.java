package com.smb.springcloudgateway.exceptions;

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
    private String api;
}
