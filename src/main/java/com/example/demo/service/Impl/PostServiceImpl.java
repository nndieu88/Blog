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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public PostDto getOne(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw new NotFoundException("Not found post");
        }
        return PostMapper.toPostDto(post.get());
    }

    @Override
    public Paging getAll(int page) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, 3, Sort.by("dateCreated").descending()));
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
    public Paging getAllPostByCategory(Integer id, int page) {
        Page<Post> posts = postRepository.findAllPostByCategory(id, PageRequest.of(page, 3, Sort.by("dateCreated").descending()));
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
    public PostDto getPostbyMetaTitle(String metaTitle) {
        Post post = postRepository.findByMetaTitle(metaTitle);
        if (post == null) {
            throw new NotFoundException("This Post does not exist!");
        }
        return PostMapper.toPostDto(post);
    }

    @Override
    public Paging getAllPostByTitle(String name, int page) {
        Page<Post> posts = postRepository.findAllPostByTitle(name, PageRequest.of(page, 6, Sort.by("date_created").descending()));
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
    public PostDto createPost(PostCreateRequest postCreateRequest) {
        Post post = postRepository.findPostByTitle(postCreateRequest.getTitle());
        if (post != null) {
            throw new DuplicateRecordException("title already is in use");
        }
        try {
            post = PostMapper.toPost(postCreateRequest);
            post.setCategory(categoryRepository.getOne(postCreateRequest.getCategoryID()));
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
