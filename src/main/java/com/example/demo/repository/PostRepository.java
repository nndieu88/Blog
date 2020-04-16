package com.example.demo.repository;

import com.example.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> getAll(Pageable pageable);

    public Post findByMetaTitle(String metaTitle);

    @Query(value = "select * from post p where p.category_id = ?", nativeQuery = true)
    public Page<Post> findAllPostByCategory(@Param("id") Integer id, Pageable pageable);

    @Query(value = "select * from post p where p.title like :name%", nativeQuery = true)
    public Page<Post> findAllPostByTitle(@Param("name") String name, Pageable pageable);
}
