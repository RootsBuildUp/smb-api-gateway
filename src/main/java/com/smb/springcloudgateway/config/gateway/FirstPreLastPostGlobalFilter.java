package com.smb.springcloudgateway.config.gateway;

import com.smb.springcloudgateway.service.GlobalFilterSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <a href="https://www.baeldung.com/spring-cloud-custom-gateway-filters">spring-cloud-custom-gateway-filters</a>
 * <p>
 * All we have to do to create a custom global filter is to implement the Spring
 * Cloud Gateway GlobalFilter interface, and add it to the context as a bean:
 * </p>
 * <p>
 * As we can see, the filters are effectively executed before and after the
 * gateway forwards the request to the service.
 * </p>
 *
 * @author rashedul
 * @since 21-07-22
 */

@Component
public class FirstPreLastPostGlobalFilter
        implements GlobalFilter, Ordered {

    @Autowired
    private GlobalFilterSecurityService securityService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        return securityService.filter(exchange,chain);
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
