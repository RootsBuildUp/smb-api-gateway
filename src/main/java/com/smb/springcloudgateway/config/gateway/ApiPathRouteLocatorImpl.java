//package com.smb.springcloudgateway.config.gateway;
//
//import com.smb.coremodel.model.workflow.CustomRouteLocatorInfo;
//import com.smb.coremodel.model.workflow.CustomRouteLocatorInfoRepo;
//import lombok.AllArgsConstructor;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.Route;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.BooleanSpec;
//import org.springframework.cloud.gateway.route.builder.Buildable;
//import org.springframework.cloud.gateway.route.builder.PredicateSpec;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import reactor.core.publisher.Flux;
//
//@AllArgsConstructor
//public class ApiPathRouteLocatorImpl implements RouteLocator {
//  @Autowired
//  private CustomRouteLocatorInfoRepo repo;
//  private final RouteLocatorBuilder routeLocatorBuilder;
//  @Override
//  public Flux<Route> getRoutes() {
//    RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
//    return repo.findAll().stream()
//        .map(apiRoute -> routesBuilder.route(String.valueOf(apiRoute.getId()),
//            predicateSpec -> setPredicateSpec(apiRoute, predicateSpec)))
//        .collectList()
//        .flatMapMany(builders -> routesBuilder.build()
//            .getRoutes());
//  }
//
//  private Buildable<Route> setPredicateSpec(CustomRouteLocatorInfo apiRoute, PredicateSpec predicateSpec) {
//    BooleanSpec booleanSpec = predicateSpec.path(apiRoute.getPathName());
//    if (!StringUtils.isEmpty(apiRoute.getMethod())) {
//      booleanSpec.and()
//          .method(apiRoute.ge());
//    }
//    return booleanSpec.uri(apiRoute.getUri());
//  }
//}
