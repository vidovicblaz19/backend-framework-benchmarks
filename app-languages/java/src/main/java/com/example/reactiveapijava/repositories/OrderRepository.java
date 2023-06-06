package com.example.reactiveapijava.repositories;

import com.example.reactiveapijava.models.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends R2dbcRepository<Order,Integer> {
    @Query("SELECT * FROM orders LIMIT 10")
    Flux<Order> findFirst10By();
}
