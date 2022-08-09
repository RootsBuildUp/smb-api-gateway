//package com.smb.springcloudgateway.config.lb;
//
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
//import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
//import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
//import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author: Rashedul Islam * @Date: 2022/8/9 3:45 * @Description:
// */
//@Configuration
////@ConfigurationProperties(value="spring.cloud.loadbalancer")
//@EnableConfigurationProperties
//@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
//public class MyLoadBalancerClientConfiguration {
//
//    @Bean
//    public MyLoadBalancer getMyLoadBalancer(DiscoveryClient discoveryClient) {
//
//        return new MyLoadBalancer(discoveryClient);
//    }
//
//    @Bean
//    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(MyLoadBalancer myLoadBalancer, GatewayLoadBalancerProperties properties) {
//
//        return new MyReactiveLoadBalancerClientFilter(properties, myLoadBalancer);
//    }
//}
