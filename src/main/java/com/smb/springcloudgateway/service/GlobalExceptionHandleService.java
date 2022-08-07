package com.smb.springcloudgateway.service;

import com.smb.springcloudgateway.exceptions.ApiError;
import com.smb.springcloudgateway.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
public class GlobalExceptionHandleService {

    public void errorHandle(Throwable ex, ApiError apiError,HttpStatus status){
        if (ex instanceof UnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
        }
        apiError.setError(ex.getMessage())
                .setMessage(ex.getMessage())
                .setStatus(status.value())
                .setTimestamp(LocalDateTime.now().toString());
        log.debug(apiError.toString());

    }
}
