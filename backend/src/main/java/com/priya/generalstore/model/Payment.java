package com.priya.generalstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    private int id;

    private int orderId;
    private int amount;
    private String paymentMode;
    private String status;

    public Payment() {
    }

    public int getId() { return id; }
    public int getOrderId() { return orderId; }
    public int getAmount() { return amount; }
    public String getPaymentMode() { return paymentMode; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public void setStatus(String status) { this.status = status; }
}