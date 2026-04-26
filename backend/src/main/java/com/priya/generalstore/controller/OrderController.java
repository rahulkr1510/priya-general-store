package com.priya.generalstore.controller;

import com.priya.generalstore.model.Product;
import com.priya.generalstore.model.StoreOrder;
import com.priya.generalstore.repository.OrderRepository;
import com.priya.generalstore.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://13.218.245.88:5173")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/orders")
    public StoreOrder createOrder(@RequestBody StoreOrder order) {
        Product product = productRepository.findById(order.getProductId()).orElse(null);

        if (product == null) {
            return null;
        }

        int total = product.getPrice() * order.getQuantity();

        order.setTotalAmount(total);
        order.setStatus("CREATED");

        return orderRepository.save(order);
    }

    @GetMapping("/orders")
    public List<StoreOrder> getOrders() {
        return orderRepository.findAll();
    }
}