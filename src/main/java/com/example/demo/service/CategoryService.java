package com.example.demo.service;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.request.CategoryCreateRequest;
import com.example.demo.model.request.CategoryUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public CategoryDto getOne(Long id);

    public Paging getAll(int page);

    List<CategoryDto> getListCategory();

    public CategoryDto createCategory(CategoryCreateRequest categoryCreateRequest);

    public void updateCategory(CategoryUpdateRequest categoryUpdateRequest, Long id);

    public void deleteCategory(Long id);
}
