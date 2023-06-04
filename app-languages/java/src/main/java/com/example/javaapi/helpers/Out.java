package com.example.javaapi.helpers;

import com.example.javaapi.model.Order;
import java.util.List;

public class Out {
    public List<Order> query;
    public List<Integer> sorted;

    public Out(List<Order> query, List<Integer> sorted){
        this.query = query;
        this.sorted = sorted;
    }
}