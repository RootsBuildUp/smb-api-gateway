package com.smb.springcloudgateway.config.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;

import javax.ws.rs.HttpMethod;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <a href="https://www.baeldung.com/spring-cloud-gateway#routing-factories">CustomRouteLocator</a>
 * <p><a href="https://www.tabnine.com/code/java/methods/org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder/routes">Example</a></p>
 * <p><a href="https://www.baeldung.com/spring-cloud-gateway">spring-cloud-gateway</a></p>
 * <p>2. Routing Handler</p>
 * <p>Being focused on routing requests, the Spring Cloud Gateway forwards requests to a Gateway
 * Handler Mapping – which determines what should be done with requests matching a specific
 * route.</p>
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
    public CorsConfiguration corsConfiguration(RoutePredicateHandlerMapping routePredicateHandlerMapping) {
        System.err.println("================ route predicate handler ===================");
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        Arrays.asList(HttpMethod.OPTIONS, HttpMethod.PUT, HttpMethod.GET, HttpMethod.DELETE, HttpMethod.POST)
                .forEach(m -> corsConfiguration.addAllowedMethod(m));
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        routePredicateHandlerMapping.setCorsConfigurations(new HashMap<>() {{
            put("/**", corsConfiguration);
        }});
        return corsConfiguration;
    }

    @Bean
    public RouteLocator customGatewayRoutes(RouteLocatorBuilder builder) {
        System.err.println("================ Route Locator Builder ===================");
        return builder.routes()
                .route(r -> r.path("/um-module/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("um-module") // circuit breaker
                                .setFallbackUri("/defaultfallback")))
                        .uri("lb://um-module"))// If the URL has a lb scheme (e.g., lb://um-module), it'll use the Spring Cloud LoadBalancerClient to resolve the name (i.e., um-module) to an actual host and port.
                .route(r -> r.path("/remittance-module/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("remittance-module") // circuit breaker
                                .setFallbackUri("/defaultfallback")))
                        .uri("lb://remittance-module"))
//                .route(r -> r.path("/mobil/mobile_api/api/**")
//                        .uri("http://15.235.86.71"))
                .build();
    }
}
