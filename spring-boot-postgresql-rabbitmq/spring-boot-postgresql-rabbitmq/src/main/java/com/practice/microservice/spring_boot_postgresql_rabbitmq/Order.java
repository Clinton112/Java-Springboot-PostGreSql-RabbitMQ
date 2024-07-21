package com.practice.microservice.spring_boot_postgresql_rabbitmq;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;

import javax.annotation.processing.Generated;
import java.util.Date;

@Entity
public class Order {

    @Id
    private Long id;
    private String customerName;
    private Date orderData;
    private Double totalAmount;
    private String status;

    public Order() {
    }

    public Order(Long id, String customerName, Date orderData, Double totalAmount, String status) {
        this.id = id;
        this.customerName = customerName;
        this.orderData = orderData;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Order(Order order) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderData() {
        return orderData;
    }

    public void setOrderData(Date orderData) {
        this.orderData = orderData;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", orderData=" + orderData +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
