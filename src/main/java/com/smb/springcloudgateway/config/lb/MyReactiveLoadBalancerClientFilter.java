package com.smb.springcloudgateway.config.lb;


import com.smb.springcloudgateway.service.GlobalFilterSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @Author: Rashedul Islam
 * @Date: 2022/8/20 10:10 pm
 * @Description:
 */
@Slf4j
public class MyReactiveLoadBalancerClientFilter extends ReactiveLoadBalancerClientFilter {

    private static final int LOAD_BALANCER_CLIENT_FILTER_ORDER = 10150;
    @Autowired
    private GlobalFilterSecurityService securityService;
    private GatewayLoadBalancerProperties properties;
    private MyLoadBalancer myLoadBalancer;


    public MyReactiveLoadBalancerClientFilter(GatewayLoadBalancerProperties properties, MyLoadBalancer myLoadBalancer) {

        super(null, null, null);
        this.properties = properties;
        this.myLoadBalancer = myLoadBalancer;
    }

    @Override
    public int getOrder() {

        return LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        if (url == null
                || (!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {

            return securityService.filter(exchange, chain);
        }
// preserve the original url
        ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
        if (log.isTraceEnabled()) {

            log.trace(ReactiveLoadBalancerClientFilter.class.getSimpleName()
                    + " url before: " + url);
        }
        return choose(exchange).doOnNext(response -> {

            if (!response.hasServer()) {

                return;
//                throw NotFoundException.create(this.properties.isUse404(), "Unable to find instance for " + url.getHost());
            }
            URI uri = exchange.getRequest().getURI();
// if the `lb:<scheme>` mechanism was used, use `<scheme>` as the default,
// if the loadbalancer doesn't provide one.
            ServiceInstance retrievedInstance = (ServiceInstance) response.getServer();
            String overrideScheme = retrievedInstance.isSecure() ? "https" : "http";
            if (schemePrefix != null) {

                overrideScheme = url.getScheme();
            }
            DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(
                    response.getServer(), overrideScheme);
            URI requestUrl = LoadBalancerUriTools.reconstructURI(serviceInstance, uri);
            if (log.isTraceEnabled()) {

                log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
            }
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
        }).then(securityService.filter(exchange, chain));
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {

        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        ServiceInstance serviceInstance = myLoadBalancer.choose(uri.getHost(), exchange.getRequest());
        return Mono.just(new DefaultResponse(serviceInstance));
    }
}
