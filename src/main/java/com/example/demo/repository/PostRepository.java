package com.example.demo.repository;

import com.example.demo.entity.Post;
import org.hibernate.search.annotations.SortableField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findAll(Pageable pageable);

    public Post findPostByTitle(String title);

    public Post findByMetaTitle(String metaTitle);

    @Query(value = "select * from post p where p.meta_category like :cate and p.is_important like 'important' order by date_created DESC limit 1", nativeQuery = true)
    public Post findByMetaCategoryAndIsImportant(@Param("cate") String metaCategory);

    @Query(value = "select * from post p where p.meta_category like :name order by date_created DESC limit 5", nativeQuery = true)
    public List<Post> findListPostByMetaCategory(@Param("name") String metaCate);

    @Query(value = "select * from post p where p.meta_category like :name order by date_created DESC", nativeQuery = true)
    public Page<Post> findAllByMetaCategory(@Param("name") String metaCate, Pageable pageable);

    @Query(value = "select * from post p where p.title like :name%", nativeQuery = true)
    public Page<Post> findAllPostByTitle(@Param("name") String name, Pageable pageable);

    @Query(value = "select * from post p where p.category_id = ? order by date_created DESC limit 3", nativeQuery = true)
    public List<Post> findThreePost(@Param("id") Integer id);

    @Query(value = "select * from post p where p.category_id = ? order by date_created DESC limit 5", nativeQuery = true)
    public List<Post> findFivePost(@Param("id") Integer id);

}
