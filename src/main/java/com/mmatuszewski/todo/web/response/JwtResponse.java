package com.mmatuszewski.todo.web.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtResponse {

    private static final String TYPE= "Bearer";

    @NonNull
    private String token;
    private String type = TYPE;

}
