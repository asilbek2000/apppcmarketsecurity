package com.example.apelsintask.repository;

import com.example.apelsintask.entity.Category;
import com.example.apelsintask.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
