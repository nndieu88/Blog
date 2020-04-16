package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @Size(min = 6, max = 20)
//    @JsonProperty("full_name")
    private String name;

    @NotNull
    @NotEmpty
    private String address;

    @Pattern(regexp = "(09|01[2|6|8|9])+([0-9]{8})\\\\b")
    private String phone;

    private String avatar;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @Size(min = 6, max = 12)
    private String password;
}
