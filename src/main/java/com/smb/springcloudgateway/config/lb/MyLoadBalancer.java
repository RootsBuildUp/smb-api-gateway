package com.smb.springcloudgateway.config.lb;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.netflix.discovery.CommonConstants;
import com.smb.springcloudgateway.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class MyLoadBalancer {

    private DiscoveryClient discoveryClient;

    /**
     * according to serviceId Filter available services * * @param serviceId service ID * @param request The current request * @return
     */
    public ServiceInstance choose(String serviceId, ServerHttpRequest request) {

        System.err.println(serviceId);
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
// The registry has no instances Throw an exception
        if (CollUtil.isEmpty(instances)) {
            log.warn("No instance available for {}", serviceId);
//            throw new NotFoundException("No instance available for " + serviceId);
            return null;
        }
// Get request version, If not, the available instances are returned randomly
        String reqVersion = request.getHeaders().getFirst(CommonConstants.CONFIG_FILE_NAME);
        if (StrUtil.isBlank(reqVersion)) {

            return instances.get(RandomUtil.randomInt(instances.size()));
        }
// Traversing instance metadata , If it matches, return this instance
        for (ServiceInstance instance : instances) {

// TODO You can directly select one to return
        }
        return instances.get(RandomUtil.randomInt(instances.size()));
    }
}
