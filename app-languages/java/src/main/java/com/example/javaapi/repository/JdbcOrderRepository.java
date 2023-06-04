package com.example.javaapi.repository;

import com.example.javaapi.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Order> getOrders(){
        return jdbcTemplate.query("SELECT * from orders LIMIT 10", BeanPropertyRowMapper.newInstance(Order.class));
    }
}
