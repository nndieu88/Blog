package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Paging {

    private List<?> content;

    private boolean hasNext;

    private boolean hasPrev;

    private int currentPage;

    private int totalPage;
}
