package com.example.apelsintask.controller;

import com.example.apelsintask.dto.ApiResponse;
import com.example.apelsintask.entity.Category;
import com.example.apelsintask.exception.BadRequestException;
import com.example.apelsintask.exception.ResourceNotFoundException;
import com.example.apelsintask.repository.CategoryRepository;
import com.example.apelsintask.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    final CategoryRepository categoryRepository;
    final CategoryService categoryService;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable String id) {
        for (char c : id.toCharArray()) {
            if (!Character.isDigit(c))
                return new HttpEntity<BadRequestException>(new BadRequestException("Xato"));
        }
        Optional<Category> categoryOptional = categoryRepository.findById(Integer.valueOf(id));
        return categoryOptional.<HttpEntity<?>>map(category ->
                ResponseEntity.ok().body(category)).orElseThrow(
                () -> new ResourceNotFoundException("category", "id", String.valueOf(id)));
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody Category category) {
        ApiResponse response = categoryService.add(category);

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Category category) {
        ApiResponse response = categoryService.edit(id, category);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @GetMapping
    public HttpEntity<?> getCategoryProduct(@RequestParam("product_id") Integer id) {
        ApiResponse response = categoryService.getCategoryProduct(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);

    }
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse response = categoryService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 204 : 404).body(response);
    }

}
