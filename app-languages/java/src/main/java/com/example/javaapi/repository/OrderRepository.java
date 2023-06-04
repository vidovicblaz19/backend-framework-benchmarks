package com.example.javaapi.repository;
import java.util.List;
import com.example.javaapi.model.Order;

public interface OrderRepository {
        List<Order> getOrders();
}
