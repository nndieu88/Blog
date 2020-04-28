package com.example.demo.model.mapper;

import com.example.demo.entity.Comment;
import com.example.demo.model.dto.CommentDto;
import com.example.demo.model.request.CommentCreateRequest;

import java.util.Date;

public class    CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setLikes(comment.getLikes());
        commentDto.setDateCreated(comment.getDateCreated());
        commentDto.setUserID(comment.getUser().getId());
        commentDto.setUser(comment.getUser().getUserName());
        commentDto.setPostID(comment.getPost().getId());
        commentDto.setTitle(comment.getPost().getTitle());
        return commentDto;
    }

    public static Comment toComment(CommentCreateRequest commentCreateRequest) {
        Comment comment = new Comment();
        comment.setContent(commentCreateRequest.getContent());
        comment.setLikes(commentCreateRequest.getLikes());
        comment.setDateCreated(new Date());
        return comment;
    }
}
