package com.mmatuszewski.todo.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateTask {

    @NotBlank
    private String id;

    @NotBlank
    @Size(min=3, max = 5)
    private String status;
}
