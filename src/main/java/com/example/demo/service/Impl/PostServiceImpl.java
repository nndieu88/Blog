package com.example.demo.service.Impl;

import com.example.demo.entity.Post;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.mapper.PostMapper;
import com.example.demo.model.request.PostCreateRequest;
import com.example.demo.model.request.PostUpdateRequest;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;
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
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;


    @Override
    public PostDto getOne(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw new NotFoundException("Not found post");
        }
        return PostMapper.toPostDto(post.get());
    }

    @Override
    public PostDto getOneImportant(String metaCategory) {
        Post post = postRepository.findByMetaCategoryAndIsImportant(metaCategory);
        if (post == null) {
            throw new NotFoundException("Not found");
        }
        return PostMapper.toPostDto(post);
    }

    @Override
    public Paging getAll(int page) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, 10, Sort.by("dateCreated").descending()));
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts.getContent()) {
            postDtos.add(PostMapper.toPostDto(post));
        }

        Paging paging = new Paging();
        paging.setContent(postDtos);
        paging.setHasNext(posts.hasNext());
        paging.setHasPrev(posts.hasPrevious());
        paging.setCurrentPage(page + 1);
        int totalPage = (posts.getTotalPages() == 0 ? 1 : posts.getTotalPages());
        paging.setTotalPage(totalPage);
        return paging;
    }

    @Override
    public List<PostDto> getThreeListPost(Integer id) {
        List<Post> posts = postRepository.findThreePost(id);
        if (posts == null) {
            throw new NotFoundException("This Post does not exist!");
        }
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(PostMapper.toPostDto(post));
        }
        return postDtos;
    }

    @Override
    public List<PostDto> getFiveListPost(Integer id) {
        List<Post> posts = postRepository.findFivePost(id);
        if (posts == null) {
            throw new NotFoundException("This Post does not exist!");
        }
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(PostMapper.toPostDto(post));
        }
        return postDtos;
    }

    @Override
    public Paging getAllPostByCategory(String name, int page) {
        Page<Post> posts = postRepository.findAllByMetaCategory(name, PageRequest.of(page, 10, Sort.by("date_created").descending()));
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts.getContent()) {
            postDtos.add(PostMapper.toPostDto(post));
        }
        Paging paging = new Paging();
        paging.setContent(postDtos);
        paging.setHasNext(posts.hasNext());
        paging.setHasPrev(posts.hasPrevious());
        paging.setCurrentPage(page + 1);
        int totalPage = (posts.getTotalPages() == 0 ? 1 : posts.getTotalPages());
        paging.setTotalPage(totalPage);
        paging.setElement(posts.getTotalElements());
        return paging;
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
                        .buildQueryBuilder().forEntity(Post.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .wildcard()
                        .onFields("title", "content", "cate")
                        .matching("*" + searchKey + "*")
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Post.class);

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

        List<PostDto> postDtos = new ArrayList<>();
        for (Object post : jpaQuery.getResultList()) {
            postDtos.add(PostMapper.toPostDto((Post) post));
        }

        paging.setContent(postDtos);
        paging.setHasNext(hasNext);
        paging.setHasPrev(hasPrev);
        paging.setCurrentPage(page);
        totalPage = (totalPage == 0 ? 1 : totalPage);
        paging.setTotalPage(totalPage);
        paging.setElement(totalElement);
        return paging;
    }

    @Override
    public PostDto getPostbyMetaTitle(String metaTitle) {
        Post post = postRepository.findByMetaTitle(metaTitle);
        if (post == null) {
            throw new NotFoundException("This Post does not exist!");
        }
        return PostMapper.toPostDto(post);
    }

    @Override
    public List<PostDto> getPostbyMetaCate(String metaCate) {
        List<Post> posts = postRepository.findListPostByMetaCategory(metaCate);
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(PostMapper.toPostDto(post));
        }
        return postDtos;
    }

    @Override
    public PostDto createPost(PostCreateRequest postCreateRequest) {
        Post post = postRepository.findPostByTitle(postCreateRequest.getTitle());
        if (post != null) {
            throw new DuplicateRecordException("title already is in use");
        }
        try {
            post = PostMapper.toPost(postCreateRequest);
            post.setCategory(categoryRepository.getOne(postCreateRequest.getCategoryID()));
            post.setMetaCategory(categoryRepository.getOne(postCreateRequest.getCategoryID()).getMetaCategory());
            post.setCate(categoryRepository.getOne(postCreateRequest.getCategoryID()).getCategoryName());
            postRepository.save(post);
        } catch (Exception ex) {
            throw new InternalServerException("Can not create post");
        }
        return new PostDto();
    }

    @Override
    public void updatePost(PostUpdateRequest postUpdateRequest, Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw new NotFoundException("Not found post");
        }
        try {
            Post updatePost = PostMapper.toPost(postUpdateRequest, id, post.get().getDateCreated());
            updatePost.setCategory(categoryRepository.getOne(postUpdateRequest.getCategoryID()));
            updatePost.setMetaCategory(categoryRepository.getOne(postUpdateRequest.getCategoryID()).getMetaCategory());
            updatePost.setCate(categoryRepository.getOne(postUpdateRequest.getCategoryID()).getCategoryName());
            postRepository.save(updatePost);
        } catch (Exception ex) {
            throw new InternalServerException("Can not update post");
        }
    }

    @Override
    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw new NotFoundException("Not found post");
        }
        try {
            postRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Can not delete post");
        }
    }
}
