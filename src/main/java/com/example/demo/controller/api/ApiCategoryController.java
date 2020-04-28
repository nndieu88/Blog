package com.example.demo.controller.api;

import com.example.demo.model.api.BasicApiResult;
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
        BasicApiResult result = new BasicApiResult();
        try {
            CategoryDto categoryDto = categoryService.getOne(id);
            result.setSuccess(true);
            result.setMessage("Successful");
            result.setData(categoryDto);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryCreateRequest category) {
        BasicApiResult result = new BasicApiResult();
        try {
            CategoryDto category1 = categoryService.createCategory(category);

            result.setSuccess(true);
            result.setMessage("Successful");
            result.setData(category1);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryUpdateRequest category, @PathVariable Long id) {
        BasicApiResult result = new BasicApiResult();
        try {
            categoryService.updateCategory(category, id);
            result.setSuccess(true);
            result.setMessage("Successful");
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        BasicApiResult result = new BasicApiResult();
        try {
            categoryService.deleteCategory(id);
            result.setSuccess(true);
            result.setMessage("Successful");
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
