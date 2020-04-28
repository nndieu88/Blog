package com.example.demo.repository;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    public Page<Comment> findAll(Pageable pageable);

    @Query(value = "select * from comment c where c.post_id = ? order by date_created DESC limit 15", nativeQuery = true)
    public List<Comment> findListComment(@Param("id") Long id);

}
