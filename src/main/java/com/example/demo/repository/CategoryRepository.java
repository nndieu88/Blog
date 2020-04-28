package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Page<Category> findAll(Pageable pageable);

    public Category findCategoryByCategoryName(String name);

    public Category findCategoryByMetaCategory(String metaName);
}
