package com.mmatuszewski.todo.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Login {
    @NotBlank
    @Size(min=3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 200)
    private String password;
}
