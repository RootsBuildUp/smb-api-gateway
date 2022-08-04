package com.babl.smbspringcloudgateway.service;

import com.babl.smbspringcloudgateway.exceptions.ApiError;
import com.babl.smbspringcloudgateway.exceptions.UnauthorizedException;
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
