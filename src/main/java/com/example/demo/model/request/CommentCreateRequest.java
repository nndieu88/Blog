package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {

    private String content;

    private Boolean likes;

    private Long postID;
}
