package com.example.apelsintask.service;

import com.example.apelsintask.dto.ApiResponse;
import com.example.apelsintask.entity.Category;
import com.example.apelsintask.entity.Product;
import com.example.apelsintask.repository.CategoryRepository;
import com.example.apelsintask.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;

    public ApiResponse add(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) return new ApiResponse("NOT", false);
        categoryRepository.save(category);
        return new ApiResponse("Save", true);
    }

    public ApiResponse edit(Integer id, Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName()))
            return new ApiResponse("Bunday nom bor", false);
        Category byId = categoryRepository.getById(id);
        byId.setName(category.getName());
        categoryRepository.save(byId);
        return new ApiResponse("Edit!", true);
    }

    public ApiResponse delete(Integer id) {
        //realda o'chirmayman
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.get();
        category.setActive(false);
        categoryRepository.save(category);
        return new ApiResponse("Delete", true);
        //xoxlasang botqoqqa bot o'chirib
    }

    public ApiResponse getCategoryProduct(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(product -> new ApiResponse("Mana", true, product.getCategory())).orElseGet(() -> new ApiResponse("Not found!", false));
    }
}
