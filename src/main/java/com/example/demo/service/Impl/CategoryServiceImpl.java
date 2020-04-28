package com.example.demo.service.Impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.CommentDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.mapper.CategoryMapper;
import com.example.demo.model.mapper.CommentMapper;
import com.example.demo.model.mapper.PostMapper;
import com.example.demo.model.request.CategoryCreateRequest;
import com.example.demo.model.request.CategoryUpdateRequest;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;

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
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page, 10, Sort.by("dateCreated").descending()));
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
    public Paging getCategoryFTS(int page, String searchKey) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Category.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .wildcard()
                        .onFields("categoryName")
                        .matching("*" + searchKey + "*")
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Category.class);

        Paging paging = new Paging();
        page = (page < 0 ? 0 : page);
        page++;
        int limit = 10;
        int totalElement = jpaQuery.getResultSize();

        int totalPage = (totalElement % limit == 0 ? (totalElement / limit) : (totalElement / limit + 1));
        boolean hasNext = (page == totalPage || totalPage == 0) ? false : true;
        boolean hasPrev = (totalPage == 0 || page == 1) ? false : true;

        jpaQuery.setFirstResult((page - 1) * limit)
                .setMaxResults(limit);

        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Object category : jpaQuery.getResultList()) {
            categoryDtos.add(CategoryMapper.toCategoryDto((Category) category));
        }

        paging.setContent(categoryDtos);
        paging.setHasNext(hasNext);
        paging.setHasPrev(hasPrev);
        paging.setCurrentPage(page);
        totalPage = (totalPage == 0 ? 1 : totalPage);
        paging.setTotalPage(totalPage);
        paging.setElement(totalElement);
        return paging;
    }

    @Override
    public CategoryDto createCategory(CategoryCreateRequest categoryCreateRequest) {
        Category category = categoryRepository.findCategoryByCategoryName(categoryCreateRequest.getCategoryName());
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
