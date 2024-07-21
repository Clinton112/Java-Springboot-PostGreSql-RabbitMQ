package com.practice.microservice.spring_boot_postgresql_rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MessageProducer messageProducer;

    public void createOrder(Order order) {
        if (order == null){
            throw new RuntimeException("Order is empty");
        }
        messageProducer.sendMessage("Order Created");
        orderRepository.save(order);
    }

    public List<Order> getOrders(){
        messageProducer.sendMessage("All Order");
        return orderRepository.findAll();
    }
    public Optional<Order> getOrderById(Long id){
       Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()){
            throw new RuntimeException("Order for this {id} not found "+id);
        }
        messageProducer.sendMessage("Order By Id");
        return order;
    }
    public Order updateOrder(Order order){
        Optional<Order> order1 = orderRepository.findById(order.getId());
        if (order1.isEmpty()){
            throw new RuntimeException("Order for this {id} not found "+order.getId());
        }
        messageProducer.sendMessage("Update Order");
        return orderRepository.saveAndFlush(order);
    }
    public void deleteOrder(Long id){
        Optional<Order> order1 = orderRepository.findById(id);
        if (order1.isEmpty()){
            throw new RuntimeException("Order for this {id} not found "+id);
        }
        messageProducer.sendMessage("Delete Order");
        orderRepository.deleteById(id);
    }
}
