package com.example.javaapi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.javaapi.helpers.Out;
import com.example.javaapi.model.Order;
import com.example.javaapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.javaapi.helpers.StressGarbageCollectorSort.stressGarbageCollectorSort;

@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping()
    public ResponseEntity<Out> getOrders() {
        try {

            List<Order> orders = new ArrayList<Order>(orderRepository.getOrders());

            List<Integer> input = Arrays.asList(
                    9, 13, 7, 25, 18, 3, 11, 30, 14, 6,
                    22, 17, 4, 28, 12, 19, 8, 15, 1, 10,
                    27, 5, 21, 16, 2, 24, 20, 23, 29, 26,
                    35, 39, 33, 51, 44, 31, 47, 36, 43, 32,
                    48, 38, 45, 34, 50, 37, 42, 40, 49, 41
            );

            List<Integer> sorted = stressGarbageCollectorSort(input);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }



            return new ResponseEntity<>(new Out(orders,sorted), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}