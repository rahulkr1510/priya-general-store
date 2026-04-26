package com.priya.generalstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StoreOrder {

    @Id
    private int id;

    private int userId;
    private int productId;
    private int quantity;
    private int totalAmount;
    private String status;

    public StoreOrder() {
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public int getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTotalAmount(int totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(String status) { this.status = status; }
}
