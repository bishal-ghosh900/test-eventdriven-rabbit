package com.example.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

    private final static String TEST1_PATH = "/myapp/test1";
    private final static String TEST2_PATH = "/myapp/test2";

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path(TEST1_PATH + "/**")
                        .filters(f -> f.rewritePath(TEST1_PATH + "/(?<segment>.*)", "/${segment}"))
                        .uri("lb://TEST1"))
                .route(p -> p.path(TEST2_PATH + "/**")
                        .filters(f -> f.rewritePath(TEST2_PATH + "/(?<segment>.*)", "/${segment}"))
                        .uri("lb://TEST2"))
                .build();
    }

}
