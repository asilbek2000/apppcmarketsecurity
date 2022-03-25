package com.example.apelsintask.repository;

import com.example.apelsintask.entity.Category;
import com.example.apelsintask.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByNameIgnoreCase(String name);


//    @Query()

}
