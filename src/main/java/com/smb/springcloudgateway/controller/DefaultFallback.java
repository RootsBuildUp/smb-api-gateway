//package com.smb.springcloudgateway.controller;
//
//import com.smb.springcloudgateway.exceptions.ApiError;
//import com.smb.springcloudgateway.service.GlobalFilterSecurityService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
//import org.springframework.cloud.gateway.support.TimeoutException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.ServerWebExchangeDecorator;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class DefaultFallback {
//    final Logger logger = LoggerFactory.getLogger(GlobalFilterSecurityService.class);
//
//    @RequestMapping(value = "/defaultfallback")
//    @ResponseStatus
//    public Mono<ApiError> fallback(ServerWebExchange exchange) {
//        ApiError apiError = new ApiError();
//        apiError.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
//
//        Exception exception = exchange.getAttribute(ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR);
//        ServerWebExchange delegate = ((ServerWebExchangeDecorator) exchange).getDelegate();
//        logger.error(" Service invocation failed ,URL={}", delegate.getRequest().getURI(), exception);
//
//        apiError.setApi(delegate.getRequest().getURI().toString());
//
//        if (exception instanceof TimeoutException) {
//            apiError.setError("Service timeout ");
//        }
//        else if (exception != null && exception.getMessage() != null) {
//            apiError.setError(" Service error : " + exception.getMessage());
//        }
//        else {
//            apiError.setError(" Service error ");
//        }
//        apiError.setTimestamp(LocalDateTime.now().toString());
//        return Mono.just(apiError);
//    }
//}
