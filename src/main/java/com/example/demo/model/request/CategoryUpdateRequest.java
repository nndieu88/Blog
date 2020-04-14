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
public class CategoryUpdateRequest {

    @NotEmpty(message = "Name is required")
    @NotNull(message = "Name must be not empty")
    private String categoryName;
}
