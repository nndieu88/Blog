package com.example.demo.model.mapper;

import com.example.demo.entity.Post;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.request.PostCreateRequest;
import com.example.demo.model.request.PostUpdateRequest;

import java.util.Date;

public class PostMapper {
    public static PostDto toPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setMetaTitle(post.getMetaTitle());
        postDto.setDateCreated(post.getDateCreated());
        postDto.setDateUpdated(post.getDateUpdated());
        postDto.setImage(post.getImage());
        postDto.setUserID(post.getUser().getId());
        postDto.setUserName(post.getUser().getUserName());
        postDto.setCategoryID(post.getCategory().getId());
        postDto.setCategoryName(post.getCategory().getCategoryName());
        postDto.setMetaCategory(post.getMetaCategory());
        return postDto;
    }

    public static Post toPost(PostCreateRequest postCreateRequest) {
        Post post = new Post();
        post.setTitle(postCreateRequest.getTitle());
        post.setContent(postCreateRequest.getContent());
        post.setMetaTitle(ConvertStringToUrl.convert(postCreateRequest.getTitle()));
        post.setMetaCategory(ConvertStringToUrl.convert(postCreateRequest.g));
        post.setDateCreated(new Date());
        post.setDateUpdated(new Date());
        post.setImage(postCreateRequest.getImage());
        return post;
    }

    public static Post toPost(PostUpdateRequest postUpdateRequest, Long id, Date date) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(postUpdateRequest.getTitle());
        post.setContent(postUpdateRequest.getContent());
        post.setMetaTitle(ConvertStringToUrl.convert(postUpdateRequest.getTitle()));
        post.setDateCreated(date);
        post.setDateUpdated(new Date());
        post.setImage(postUpdateRequest.getImage());
        return post;
    }
}
