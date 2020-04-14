package com.example.demo.model.mapper;

import com.example.demo.entity.Category;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.request.CategoryCreateRequest;
import com.example.demo.model.request.CategoryUpdateRequest;

import java.util.Date;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setMetaCategory(category.getMetaCategory());
        categoryDto.setDateCreated(category.getDateCreated());
        categoryDto.setDateUpdated(category.getDateUpdated());
        return categoryDto;
    }

    public static Category toCategory(CategoryCreateRequest categoryCreateRequest) {
        Category category = new Category();
        category.setCategoryName(categoryCreateRequest.getCategoryName());
        category.setMetaCategory(ConvertStringToUrl.convert(categoryCreateRequest.getCategoryName()));
        category.setDateCreated(new Date());
        category.setDateUpdated(new Date());
        return category;
    }

    public static Category toCategory(CategoryUpdateRequest categoryUpdateRequest, Long id, Date date) {
        Category category = new Category();
        category.setId(id);
        category.setCategoryName(categoryUpdateRequest.getCategoryName());
        category.setMetaCategory(ConvertStringToUrl.convert(categoryUpdateRequest.getCategoryName()));
        category.setDateCreated(date);
        category.setDateUpdated(new Date());
        return category;
    }
}
