package com.example.apelsintask.service;

import com.example.apelsintask.dto.ApiResponse;
import com.example.apelsintask.dto.PaymentDTO;
import com.example.apelsintask.entity.Invoice;
import com.example.apelsintask.entity.Payment;
import com.example.apelsintask.repository.InvoiceRepository;
import com.example.apelsintask.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    final PaymentRepository paymentRepository;
    final InvoiceRepository invoiceRepository;


    public ApiResponse add(PaymentDTO dto) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(dto.getInvoiceId());
        Invoice invoice = optionalInvoice.get();
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(dto.getAmount()));
        payment.setInvoice(invoice);

        paymentRepository.save(payment);
        return new ApiResponse("To'landi", true);
    }


}
