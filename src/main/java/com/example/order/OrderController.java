package com.example.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{username}/getOrder/{id}")
    public Order getOrder(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        try {
            return orderService.getOrder(username, id);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }

    @GetMapping("/{username}/getOrders/")
    public List<Order> getOrders(@PathVariable("username") String username) {
        return orderService.getAllOrders(username);
    }

    @PostMapping("/{username}/addOrder")
    public void addOrder(@PathVariable("username") String username, @RequestBody Order order) {
        try {
            orderService.addOrder(username, order);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @PutMapping("/{username}/updateOrder")
    public void updateOrder(@PathVariable("username") String username, @RequestBody Order order) {
        try {
            orderService.updateOrder(username, order);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @DeleteMapping("/{username}/deleteOrder")
    public void deleteOrder(@PathVariable("username") String username, @RequestBody Order order) {
        try {
            orderService.deleteOrder(username, order);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
