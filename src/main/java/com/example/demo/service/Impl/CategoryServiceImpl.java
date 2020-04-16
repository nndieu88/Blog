package com.example.demo.service.Impl;

import com.example.demo.entity.Category;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.mapper.CategoryMapper;
import com.example.demo.model.mapper.PostMapper;
import com.example.demo.model.request.CategoryCreateRequest;
import com.example.demo.model.request.CategoryUpdateRequest;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto getOne(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new NotFoundException("Not found category");
        }
        return CategoryMapper.toCategoryDto(category.get());
    }

    @Override
    public Paging getAll(int page) {
        Page<Category> categories = categoryRepository.getAll(PageRequest.of(page, 3, Sort.by("dateCreated").descending()));
        List<CategoryDto> listCategories = new ArrayList<>();
        for (Category category : categories.getContent()) {
            listCategories.add(CategoryMapper.toCategoryDto(category));
        }

        Paging paging = new Paging();
        paging.setContent(listCategories);
        paging.setHasNext(categories.hasNext());
        paging.setHasPrev(categories.hasPrevious());
        paging.setCurrentPage(page + 1);
        paging.setTotalPage(categories.getTotalPages() == 0 ? 1 : categories.getTotalPages());
        return paging;
    }

    @Override
    public List<CategoryDto> getListCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(CategoryMapper.toCategoryDto(category));
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto createCategory(CategoryCreateRequest categoryCreateRequest) {
        Category category = categoryRepository.findAllByCategoryName(categoryCreateRequest.getCategoryName());
        if (category != null) {
            throw new DuplicateRecordException("category already is in use");
        }
        try {
            categoryRepository.save(CategoryMapper.toCategory(categoryCreateRequest));
        } catch (Exception e) {
            throw new InternalServerException("Can not save category");
        }
        return new CategoryDto();
    }

    @Override
    public void updateCategory(CategoryUpdateRequest categoryUpdateRequest, Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new NotFoundException("Not found category");
        }
        try {
            categoryRepository.save(CategoryMapper.toCategory(categoryUpdateRequest, id, category.get().getDateCreated()));
        } catch (Exception ex) {
            throw new InternalServerException("Can not update category");
        }
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new NotFoundException("Not found category");
        }
        try {
            categoryRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Can not delete category");
        }
    }
}
