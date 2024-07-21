package com.practice.microservice.spring_boot_postgresql_rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/orders")
    public ResponseEntity<Order> addOrder(@RequestBody Order order){
        try {
            service.createOrder(order);
            return new ResponseEntity<>(order,HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(){
        try {
            return new ResponseEntity<>(service.getOrders(),HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        try {
            Optional<Order> order = service.getOrderById(id);
            return order.map(value -> new ResponseEntity<>(value,HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order){
        try {
            Optional<Order> existingOrder = Optional.ofNullable(service.updateOrder(order));
            if (existingOrder.isPresent()){
                return new ResponseEntity<>(order,HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        try {
            if (service.getOrderById(id).isPresent()){
                service.deleteOrder(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
