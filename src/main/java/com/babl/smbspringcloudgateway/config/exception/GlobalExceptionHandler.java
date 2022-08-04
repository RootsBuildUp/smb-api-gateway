package com.babl.smbspringcloudgateway.config.exception;

import com.babl.smbspringcloudgateway.exceptions.ApiError;
import com.babl.smbspringcloudgateway.service.GlobalExceptionHandleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <a href="https://stackoverflow.com/questions/47958622/spring-security-webflux-reactive-exception-handling">Global Exception Handler</a>
 * <p>
 * The solution I found is creating a component implementing ErrorWebExceptionHandler.
 * The instances of ErrorWebExceptionHandler bean run before Spring Security filters.
 * Here's a sample that I use
 * </p>
 * <p>
 * If you're injecting the HttpHandler instead, then it's a bit different but the
 * idea is the same.
 * </p>
 *
 * @author Rashedul Islam
 * @since 01-08-2022
 */
@Slf4j
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

  @Autowired
  private DataBufferWriter bufferWriter;
  @Autowired
  private GlobalExceptionHandleService exceptionHandleService;

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    ApiError apiError = new ApiError();
    HttpStatus status = null;
    exceptionHandleService.errorHandle(ex,apiError,status);
    if (exchange.getResponse().isCommitted()) {
        return Mono.error(ex);
    }
    exchange.getResponse().setStatusCode(status);
    exchange.getResponse().getHeaders().set("Content-Type","application/json;charset=UTF-8");
    return bufferWriter.write(exchange.getResponse(), apiError);
  }
}
