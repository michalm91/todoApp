package com.mmatuszewski.todo.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewTask {

    @NotBlank
    @Size(min=3, max = 250)
    private String description;

    @NotBlank
    @Size(min=3, max = 5)
    private String status;
}
