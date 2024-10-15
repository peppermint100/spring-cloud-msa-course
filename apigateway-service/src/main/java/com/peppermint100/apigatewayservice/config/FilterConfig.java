package com.peppermint100.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// yml 파일에서 하던 설정을 Java Code로 작성
//@Configuration
public class FilterConfig {

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(
                                f -> f.addRequestHeader("first-request", "first-request-header-value")
                                        .addResponseHeader("first-response", "first-response-header-value")
                        )
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/second-service/**")
                        .filters(
                                f -> f.addRequestHeader("second-request", "second-request-header-value")
                                        .addResponseHeader("second-response", "second-response-header-value")
                        )
                        .uri("http://localhost:8082"))
                .build();
    }
}
