package com.example.reactiveapijava.routers;

import com.example.reactiveapijava.services.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class OrderRouter {
    @Bean
    public RouterFunction<ServerResponse> route(OrderService orderService) {
        return RouterFunctions
                .route(GET("/"), orderService::getResponse);
    }
}
