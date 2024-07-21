package com.practice.microservice.spring_boot_postgresql_rabbitmq;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService mockedService;

    private final Long testId=1L;
    private final String testDataName="Test Data";
    private final Date testDate = Date.from(Instant.now());
    private final Double testCount = 0.0;
    private final String testStatus = "A";
    Order order = new Order(testId,testDataName,testDate,testCount,testStatus);

    @Test
    public void testCreate_Success() {
        // Mock repository behavior for saving
        Order entityToSave = new Order(order);
        when(orderRepository.save(entityToSave)).thenReturn(entityToSave);

        // Call service method to create
        orderService.createOrder(entityToSave);

        // Verify repository interaction
        verify(orderRepository).save(entityToSave);
    }

    @Test
    public void testGetById_Success() {
        // Mock repository behavior for finding by ID
        Order expectedEntity = new Order(order);
        when(orderRepository.findById(testId)).thenReturn(Optional.of(expectedEntity));

        // Call service method to get by ID
        Optional<Order> retrievedEntity = orderService.getOrderById(testId);

        // Assert retrieved entity
        Assertions.assertNotNull(retrievedEntity);
        Assertions.assertEquals(expectedEntity.getId(), retrievedEntity.get().getId());
        Assertions.assertEquals(expectedEntity.getCustomerName(), retrievedEntity.get().getCustomerName());
    }

    @Test
    public void testGetById_NotFound() {
        // Mock repository behavior for not finding by ID
        when(orderRepository.findById(testId)).thenReturn(Optional.empty());

        // Call service method to get by ID (expecting not found)
        Optional<Order> retrievedEntity = orderService.getOrderById(testId);

        // Assert entity not found
        assertNull(retrievedEntity);
    }

    @Test
    public void testUpdateById_Success() {
        // Mock repository behavior for finding and saving
        Order existingEntity = new Order(order);
        existingEntity.setId(testId);
        String updatedName = "Updated Data";
        existingEntity.setCustomerName(updatedName);
        when(orderRepository.findById(testId)).thenReturn(Optional.of(existingEntity));
        when(orderRepository.save(existingEntity)).thenReturn(existingEntity);

        // Prepare updated entity
        Order updatedEntity = new Order(order);
        updatedEntity.setId(testId);

        // Call service method to update
        orderService.updateOrder(updatedEntity);

        // Verify repository interaction
        verify(orderRepository).findById(testId);
        verify(orderRepository).save(existingEntity);
    }

    @Test // Test for expected exception
    public void testUpdateById_NotFound() {
        // Mock repository behavior for not finding by ID
        when(orderRepository.findById(testId)).thenReturn(Optional.empty());

        // Prepare updated entity
        Order updatedEntity = new Order(order);
        updatedEntity.setId(testId);

        // Call service method to update (expecting not found exception)
        orderService.updateOrder(updatedEntity);
    }

    @Test
    public void testDeleteById_Success() {
        // Mock repository behavior for finding
        Order entityToDelete = new Order(order);
        entityToDelete.setId(testId);
        when(orderRepository.findById(testId)).thenReturn(Optional.of(entityToDelete));

        // Call service method to delete
        orderService.deleteOrder(testId);

        // Verify repository interaction
        verify(orderRepository).deleteById(testId);
    }

    @Test // Test for expected exception
    public void testDeleteById_NotFound() {
        // Mock repository behavior for not finding by ID
        when(orderRepository.findById(testId)).thenReturn(Optional.empty());

        // Call service method to delete (expecting not found exception)
        orderService.deleteOrder(testId);
    }

}
