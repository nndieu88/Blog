package com.example.demo.controller.api;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.request.CategoryCreateRequest;
import com.example.demo.model.request.CategoryUpdateRequest;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("admins/categories")
@RestController
public class ApiCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getOne(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryCreateRequest category) {
        CategoryDto category1 = categoryService.createCategory(category);
        return ResponseEntity.ok(category1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryUpdateRequest category, @PathVariable Long id) {
        categoryService.updateCategory(category, id);
        return ResponseEntity.ok("successfull");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }
}
