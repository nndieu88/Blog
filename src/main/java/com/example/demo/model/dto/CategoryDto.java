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
public class CategoryDto {

    private Long id;

    private String categoryName;

    private String metaCategory;

    private Date dateCreated;

    private Date dateUpdated;


}
