package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {

    @NotEmpty(message = "Title is required")
    @NotNull(message = "Title must be not empty")
    private String title;

    @NotEmpty(message = "Content is required")
    @NotNull(message = "Content must be not empty")
    private String content;

    private String image;

    private Long categoryID;
}
