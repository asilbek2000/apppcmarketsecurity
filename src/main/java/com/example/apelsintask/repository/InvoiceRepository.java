package com.example.apelsintask.repository;

import com.example.apelsintask.entity.Category;
import com.example.apelsintask.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    Optional<Invoice> findByOrder_Id(UUID id);
}
