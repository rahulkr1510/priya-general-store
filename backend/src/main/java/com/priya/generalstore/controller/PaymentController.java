package com.priya.generalstore.controller;

import com.priya.generalstore.model.Payment;
import com.priya.generalstore.model.StoreOrder;
import com.priya.generalstore.repository.OrderRepository;
import com.priya.generalstore.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://13.218.245.88:5173")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentController(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/payments")
    public Payment makePayment(@RequestBody Payment payment) {

        payment.setStatus("SUCCESS");

        StoreOrder order = orderRepository.findById(payment.getOrderId()).orElse(null);

        if (order != null) {
            order.setStatus("PAID");
            orderRepository.save(order);
        }

        return paymentRepository.save(payment);
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }
}