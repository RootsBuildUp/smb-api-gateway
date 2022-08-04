package com.babl.smbspringcloudgateway.config.gateway;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <a href="https://www.baeldung.com/spring-cloud-gateway#routing-factories">GatewayDiscoveryConfiguration</a>
 * <p>6. Spring Cloud DiscoveryClient Support:</p>
 * <p>Spring Cloud Gateway can be easily integrated with Service
 * Discovery and Registry libraries, such as Eureka Server and Consul.
 * </p>
 * @author rashedul
 * @since 21-07-22
 */

@Configuration
public class GatewayDiscoveryConfiguration {

    @Bean
    DiscoveryClientRouteDefinitionLocator discoveryRoutes(ReactiveDiscoveryClient rdc,
                                                          DiscoveryLocatorProperties dlp) {
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }
}
