package com.example.demo.service;

import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.request.PostCreateRequest;
import com.example.demo.model.request.PostUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    public PostDto getOne(Long id);

    public PostDto getOneImportant(String metaCategory);

    public Paging getAll(int page);

    public List<PostDto> getThreeListPost(Integer id);

    public List<PostDto> getFiveListPost(Integer id);

    public Paging getAllPostByCategory(String name, int page);

    public Paging searchFTS(int page, String searchKey);

    public PostDto getPostbyMetaTitle(String metaTitle);

    public List<PostDto> getPostbyMetaCate(String metaCate);

    public PostDto createPost(PostCreateRequest postCreateRequest);

    public void updatePost(PostUpdateRequest postUpdateRequest, Long id);

    public void deletePost(Long id);
}
