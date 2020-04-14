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
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private String metaTitle;

    private Date dateCreated;

    private Date dateUpdated;

    private String image;

    private Long userID;

    private String userName;

    private Long categoryID;

    private String categoryName;

    private String metaCategory;
}
