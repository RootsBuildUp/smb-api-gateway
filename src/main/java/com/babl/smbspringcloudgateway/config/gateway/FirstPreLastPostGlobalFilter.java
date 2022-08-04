package com.babl.smbspringcloudgateway.config.gateway;

import com.babl.smbspringcloudgateway.constant.Constants;
import com.babl.smbspringcloudgateway.exceptions.UnauthorizedException;
import com.babl.smbspringcloudgateway.service.GlobalFilterSecurityService;
import com.babl.smbummodel.model.user.BearerToken;
import com.babl.smbummodel.model.user.BearerTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    final Logger logger =
            LoggerFactory.getLogger(FirstPreLastPostGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        logger.info("Pre-Filter executed");
        ServerHttpRequest httpRequest = exchange.getRequest();
        ServerHttpResponse httpResponse = exchange.getResponse();

        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod().toString())) {
            httpResponse.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        } else {
            securityService.validateToken(httpRequest, httpResponse);
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        logger.info("Last Post Global Filter");

                    }));
        }

    }

    @Override
    public int getOrder() {
        return -1;
    }

}
