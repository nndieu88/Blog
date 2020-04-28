package com.example.demo.service;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.request.CategoryCreateRequest;
import com.example.demo.model.request.CategoryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public CategoryDto getOne(Long id);

    public Paging getAll(int page);

    public List<CategoryDto> getListCategory();

    public Paging getCategoryFTS(int page, String searchKey);

    public CategoryDto createCategory(CategoryCreateRequest categoryCreateRequest);

    public void updateCategory(CategoryUpdateRequest categoryUpdateRequest, Long id);

    public void deleteCategory(Long id);
}
