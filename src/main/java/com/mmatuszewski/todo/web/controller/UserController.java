package com.mmatuszewski.todo.web.controller;

import com.mmatuszewski.todo.web.response.ResponseTransfer;
import com.mmatuszewski.todo.web.dto.Login;
import com.mmatuszewski.todo.web.dto.Register;
import com.mmatuszewski.todo.web.response.JwtResponse;
import com.mmatuszewski.todo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/auth")
public class UserController
{
    @Autowired
    private UserService userService;

    /**
     * Perform user registration.
     *
     * @param registerRequest
     * @return ResponseTransfer
     */
    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseTransfer signup(@Valid @RequestBody Register registerRequest) {

        userService.addUser(registerRequest);
        return new ResponseTransfer("User registered!!!");
    }

    /**
     * Perform user login.
     *
     * @param loginRequest
     * @return JwtResponse
     */
    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JwtResponse login(@Valid @RequestBody Login loginRequest) {

        return userService.authenticateUser(loginRequest);
    }

}
