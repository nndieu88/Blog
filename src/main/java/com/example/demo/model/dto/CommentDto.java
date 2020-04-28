package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;

    private String content;

    private Boolean likes;

    private Date dateCreated;

    private Long userID;

    private String user;

    private Long postID;

    private String title;
}
