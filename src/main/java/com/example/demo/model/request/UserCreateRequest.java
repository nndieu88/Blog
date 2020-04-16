package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @Size(min = 6, max = 20)
//    @JsonProperty("full_name")
    private String name;

    @Email
    @NotEmpty
    @NotNull
    private String email;

    @Size(min = 6, max = 12)
    private String password;
}
