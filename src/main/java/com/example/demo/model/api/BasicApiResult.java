package com.example.demo.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasicApiResult {

    private boolean isSuccess;

    private String message;

    private Object data;
}
