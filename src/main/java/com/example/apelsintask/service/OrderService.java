package com.example.apelsintask.service;

import com.example.apelsintask.dto.ApiResponse;
import com.example.apelsintask.dto.DetailDTO;
import com.example.apelsintask.dto.DetailOrder;
import com.example.apelsintask.dto.OrderDTO;
import com.example.apelsintask.entity.*;
import com.example.apelsintask.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final DetailRepository detailRepository;
    final ProductRepository productRepository;
    final InvoiceRepository invoiceRepository;

    public ApiResponse add(OrderDTO dto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(dto.getCustId());

        if (!optionalCustomer.isPresent()) return new ApiResponse("Customer not found!", false);

        Order order = new Order();
        order.setCustomer(optionalCustomer.get());
        Order save = orderRepository.save(order);
//        order.setDate(new Date(LocalDate.now().get(ChronoField.EPOCH_DAY)));
        double totalSum = 0;

        for (DetailDTO detailDTO : dto.getDetailDTOList()) {
            Optional<Product> optionalProduct = productRepository.findById(detailDTO.getProductId());
            if (!optionalProduct.isPresent()) continue;

            Product product = optionalProduct.get();

            Detail detail = new Detail();
            detail.setOrder(save);
            detail.setQuantity((short) detailDTO.getQuantity());
            detail.setProduct(optionalProduct.get());

            totalSum += (product.getPrice().doubleValue() * detail.getQuantity());
            detailRepository.save(detail);
        }

        Date date = new Date(System.currentTimeMillis()+(3*86400 * 1000));
        Invoice invoice = new Invoice();
        invoice.setOrder(save);
        invoice.setAmount(BigDecimal.valueOf(totalSum));
        invoice.setDue(date);
        invoice.setIssued(new Date(System.currentTimeMillis()));
        Invoice invoice1 = invoiceRepository.save(invoice);
        return new ApiResponse("Add order", true, invoice1.getId());
    }

    public ApiResponse getOrderProduct(UUID id) {

        List<DetailOrder> collect = detailRepository.findAllByOrder_Id(id).stream().
                map(this::getDetailOrder).collect(Collectors.toList());

        return new ApiResponse("mana", true, collect);
    }

    private DetailOrder getDetailOrder(Detail detail) {
        return new DetailOrder(
                detail.getProduct().getName(),
                detail.getQuantity());
    }

    public ApiResponse edit(UUID id, OrderDTO orderDTO) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        Order order = orderOptional.get();

        List<Detail> allByOrder_id = detailRepository.findAllByOrder_Id(order.getId());

        //hammasini o'chirdik
        detailRepository.deleteAll(allByOrder_id);

        Optional<Invoice> invoice = invoiceRepository.findByOrder_Id(order.getId());

        Invoice edit = invoice.get();

        double totalSum = 0;
        for (DetailDTO detailDTO : orderDTO.getDetailDTOList()) {
            Optional<Product> optionalProduct = productRepository.findById(detailDTO.getProductId());
            Product product = optionalProduct.get();

            Detail detail = new Detail();
            detail.setOrder(order);
            detail.setQuantity((short) detailDTO.getQuantity());
            detail.setProduct(product);
            totalSum += product.getPrice().doubleValue() * detailDTO.getQuantity();

            detailRepository.save(detail);
        }
        edit.setAmount(BigDecimal.valueOf(totalSum));
        edit.setIssued(new Date(LocalDate.now().get(ChronoField.EPOCH_DAY)));
        edit.setDue(new Date(LocalDate.now().plusDays(3).getLong(ChronoField.EPOCH_DAY)));
        invoiceRepository.save(edit);
        orderRepository.save(order);
        return new ApiResponse("Edit", true);

    }

    public ApiResponse delete(UUID id) {

        //realda o'chirmayman
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.get();
        order.setActive(false);
        orderRepository.save(order);
        return new ApiResponse("Delete", true);
        //xoxlasang botqoqqa bot o'chirib

    }
}
