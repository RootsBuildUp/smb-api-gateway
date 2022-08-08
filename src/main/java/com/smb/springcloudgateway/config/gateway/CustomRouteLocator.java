package com.smb.springcloudgateway.config.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * <a href="https://www.baeldung.com/spring-cloud-gateway#routing-factories">CustomRouteLocator</a>
 * <p><a href="https://www.tabnine.com/code/java/methods/org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder/routes">Example</a></p>
 * <p><a href="https://www.baeldung.com/spring-cloud-gateway">spring-cloud-gateway</a></p>
 * <p>2. Routing Handler</p>
 *<p>Being focused on routing requests, the Spring Cloud Gateway forwards requests to a Gateway
 *  Handler Mapping – which determines what should be done with requests matching a specific
 *  route.</p>
 * <p>Let's start with a quick example of how to the Gateway Handler
 * resolves route configurations by using RouteLocator
 * </p>
 *
 * @author rashedul
 * @since 21-07-22
 */
@Configuration
public class CustomRouteLocator {


    @Bean
    public RouteLocator customGatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/um/v1/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("um-module") // circuit breaker
                                .setFallbackUri("/inCaseOfFailureUseThis")))
                        .uri("lb://um-module"))
                .route(r -> r.path("/catalogs/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("movie-catalog-service")// circuit breaker
                                .setFallbackUri("/inCaseOfFailureUseThis")))
                        .uri("lb://movie-catalog-service")) // If the URL has a lb scheme (e.g., lb://movie-catalog-service), it'll use the Spring Cloud LoadBalancerClient to resolve the name (i.e., movie-catalog-service) to an actual host and port.
                .route(r -> r.path("/ratings/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("ratings-info-service") // circuit breaker
                                .setFallbackUri("/inCaseOfFailureUseThis")))
                        .uri("lb://ratings-info-service"))
//                .route(r -> r.path("/mobil/mobile_api/api/**")
//                        .uri("http://15.235.86.71"))
                .build();
    }
}