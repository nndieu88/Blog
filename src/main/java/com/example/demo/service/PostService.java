package com.example.demo.service;

import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.request.PostCreateRequest;
import com.example.demo.model.request.PostUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    public PostDto getOne(Long id);

    public Paging getAll(int page);

    public Paging getAllPostByCategory(Integer id, int page);

    public PostDto getPostbyMetaTitle(String metaTitle);

    public Paging getAllPostByTitle(String name, int page);

    public PostDto createPost(PostCreateRequest postCreateRequest);

    public void updatePost(PostUpdateRequest postUpdateRequest, Long id);

    public void deletePost(Long id);
}
