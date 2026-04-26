package com.priya.generalstore.repository;

import com.priya.generalstore.model.StoreOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<StoreOrder, Integer> {
}