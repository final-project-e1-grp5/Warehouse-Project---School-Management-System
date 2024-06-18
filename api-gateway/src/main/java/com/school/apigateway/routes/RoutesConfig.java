package com.school.apigateway.routes;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/admin/**")
                        .uri("lb://ADMIN-SERVICE"))
                .route(p -> p.path("/student/**")
                        .uri("lb://STUDENT-SERVICE"))
                .route(p -> p.path("/user/**")
                        .uri("lb://USER-SERVICE"))
                .route(p -> p.path("/teacher/**")
                        .uri("lb://TEACHER-SERVICE"))
                .route(p -> p.path("/schedule/**")
                        .uri("lb://SCHEDULE-SERVICE"))
                .route(p -> p.path("/class/**")
                        .uri("lb://CLASS-SERVICE"))
                .route(p -> p.path("/grade/**")
                        .uri("lb://GRADE-SERVICE"))
                .route(p -> p.path("/attendance/**")
                        .uri("lb://ATTENDANCE-SERVICE"))
                .build();
    }
}