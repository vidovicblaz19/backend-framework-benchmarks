package com.example.reactiveapijava.services;

import com.example.reactiveapijava.models.Order;
import com.example.reactiveapijava.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public Mono<ServerResponse> getResponse(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(orderRepository.findFirst10By(),Order.class);
    }
}