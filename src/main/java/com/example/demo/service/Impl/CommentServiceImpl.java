package com.example.demo.service.Impl;

import com.example.demo.entity.Comment;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.CommentDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.mapper.CommentMapper;
import com.example.demo.model.request.CommentCreateRequest;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CommentService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Paging getAll(int page) {
        Page<Comment> comments = commentRepository.findAll(PageRequest.of(page, 10, Sort.by("dateCreated").descending()));
        List<CommentDto> listComment = new ArrayList<>();
        for (Comment comment : comments.getContent()) {
            listComment.add(CommentMapper.toCommentDto(comment));
        }

        Paging paging = new Paging();
        paging.setContent(listComment);
        paging.setHasNext(comments.hasNext());
        paging.setHasPrev(comments.hasPrevious());
        paging.setCurrentPage(page + 1);
        paging.setTotalPage(comments.getTotalPages() == 0 ? 1 : comments.getTotalPages());
        return paging;
    }

    @Override
    public List<CommentDto> getListComment(Long id) {
        List<Comment> comments = commentRepository.findListComment(id);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(CommentMapper.toCommentDto(comment));
        }
        return commentDtos;
    }

    @Override
    public Paging searchFTS(int page, String searchKey) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Comment.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .wildcard()
                        .onFields("content")
                        .matching("*" + searchKey + "*")
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Comment.class);

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

        List<CommentDto> commentDtos = new ArrayList<>();
        for (Object comment : jpaQuery.getResultList()) {
            commentDtos.add(CommentMapper.toCommentDto((Comment) comment));
        }

        paging.setContent(commentDtos);
        paging.setHasNext(hasNext);
        paging.setHasPrev(hasPrev);
        paging.setCurrentPage(page);
        totalPage = (totalPage == 0 ? 1 : totalPage);
        paging.setTotalPage(totalPage);
        paging.setElement(totalElement);
        return paging;
    }

    @Override
    public CommentDto createComment(CommentCreateRequest commentCreateRequest) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = CommentMapper.toComment(commentCreateRequest);
        comment.setPost(postRepository.getOne(commentCreateRequest.getPostID()));
        comment.setUser(user.getUser());
        try {
            commentRepository.save(comment);
        } catch (Exception ex) {
            throw new InternalServerException("Can not save comment");
        }
        return new CommentDto();
    }

    @Override
    public void deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment == null) {
            throw new NotFoundException("Not found!");
        }
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerException("Can not delete");
        }
    }
}
