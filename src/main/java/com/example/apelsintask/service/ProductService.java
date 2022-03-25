package com.example.apelsintask.service;

import com.example.apelsintask.dto.ApiResponse;
import com.example.apelsintask.dto.ProductDTO;
import com.example.apelsintask.entity.Category;
import com.example.apelsintask.entity.Product;
import com.example.apelsintask.repository.CategoryRepository;
import com.example.apelsintask.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;


    public ApiResponse add(ProductDTO dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCatId());
        if (!optionalCategory.isPresent()) return new ApiResponse("Xatolik!", false);

        if (productRepository.existsByNameIgnoreCase(dto.getName())) return new ApiResponse("Name bor!", false);

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        product.setPhoto(dto.getPhotoURl());
        product.setCategory(optionalCategory.get());
        productRepository.save(product);
        return new ApiResponse("Save", true);
    }

    public ApiResponse edit(Integer id, ProductDTO dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCatId());
        if (!optionalCategory.isPresent()) return new ApiResponse("Xatolik!", false);
        if (productRepository.existsByNameIgnoreCase(dto.getName()))
            return new ApiResponse("Bunday nom bor", false);
        Product byId = productRepository.getById(id);
        byId.setCategory(optionalCategory.get());
        byId.setName(dto.getName());
        byId.setPrice(BigDecimal.valueOf(dto.getPrice()));
        byId.setPhoto(dto.getPhotoURl());
        byId.setDescription(dto.getDescription());
        productRepository.save(byId);
        return new ApiResponse("Edit!", true);
    }

    public ApiResponse delete(Integer id) {
        //realda o'chirmayman
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.get();
        product.setActive(false);
        productRepository.save(product);
        return new ApiResponse("Delete", true);
        //xoxlasang botqoqqa bot o'chirib
    }
}
