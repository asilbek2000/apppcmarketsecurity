package com.example.apelsintask.controller;

import com.example.apelsintask.dto.ApiResponse;
import com.example.apelsintask.dto.PaymentDTO;
import com.example.apelsintask.entity.Payment;
import com.example.apelsintask.repository.PaymentRepository;
import com.example.apelsintask.repository.PaymentRepository;
import com.example.apelsintask.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    final PaymentRepository paymentRepository;
    final PaymentService paymentService;


    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(paymentRepository.findAll());
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable UUID id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        return paymentOptional.<HttpEntity<?>>map(payment -> ResponseEntity.ok().body(payment)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Payment()));
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/details") //?payment_details_id=12345
    public HttpEntity<?> getDetailsId(@RequestParam("payment_details_id") UUID id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        return paymentOptional.<HttpEntity<?>>map(payment -> ResponseEntity.ok().body(payment)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Payment()));
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody PaymentDTO payment) {
        ApiResponse response = paymentService.add(payment);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

//    @DeleteMapping("/{id}")
//    public HttpEntity<?> delete(@PathVariable Integer id) {
//        ApiResponse response = paymentService.delete(id);
//        return ResponseEntity.status(response.isSuccess() ? 204 : 404).body(response);
//    }

}
