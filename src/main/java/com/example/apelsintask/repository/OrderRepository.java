package com.example.apelsintask.repository;

import com.example.apelsintask.entity.Category;
import com.example.apelsintask.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
