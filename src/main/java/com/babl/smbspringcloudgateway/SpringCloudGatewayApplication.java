package com.babl.smbspringcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>Dynamic Routing with Gateway</p>
 * <p>The Spring Cloud Gateway has three important parts to it. Those are −</p>
 * <ul>
 *     <li>Route − These are the building blocks of the gateway which contain URL to which
 *     request is to be forwarded to and the predicates and filters that are applied on the
 *     incoming requests.
 *     </li>
 *     <li>Predicate − These are the set of criteria which should match for the incoming
 *     requests to be forwarded to internal microservices. For example, a path predicate
 *     will forward the request only if the incoming URL contains that path.
 *     </li>
 *     <li>Filters − These act as the place where you can modify the incoming requests
 *     before sending the requests to the internal microservices or before responding
 *     back to the client.
 *     </li>
 * </ul>
 *
 *<p>@EnableDiscoveryClient lives in spring-cloud-commons and picks the implementation
 * on the classpath. @EnableEurekaClient lives in spring-cloud-netflix and only works
 * for eureka. If eureka is on your classpath,they are effectively the same.
 * </p>
 *
 * @author rashedul
 * @since 21-07-22
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
@ComponentScan(basePackages = {"com.babl.smbspringcloudgateway","com.babl.smbummodel.model"})
@EntityScan("com.babl.smbummodel.model")
@EnableJpaRepositories("com.babl.smbummodel.model")
public class SpringCloudGatewayApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewayApplication.class, args);
	}

}
