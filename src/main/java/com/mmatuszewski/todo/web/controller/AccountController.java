package com.mmatuszewski.todo.web.controller;

import com.mmatuszewski.todo.domain.model.Todo;
import com.mmatuszewski.todo.web.response.ResponseTransfer;
import com.mmatuszewski.todo.web.dto.NewTask;
import com.mmatuszewski.todo.web.dto.UpdateTask;
import com.mmatuszewski.todo.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
public class AccountController
{
    public static final String TODO_TAG = "TODO";
    public static final String DONE_TAG = "DONE";

    @Autowired
    private AccountService accountService;


    /**
     * Get user's to do tasks.
     *
     * @param user
     * @return List<Todo>
     */
    @GetMapping(path = "/todo")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Todo> getTodoForUser(@AuthenticationPrincipal UserDetails user)
    {
        return getTaskForUser(user.getUsername(), TODO_TAG);
    }

    /**
     * Get user's tasks which are done.
     *
     * @param user
     * @return List<Todo>
     */
    @GetMapping(path = "/done")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Todo> getDoneForUser(@AuthenticationPrincipal UserDetails user)
    {
        return getTaskForUser(user.getUsername(), DONE_TAG);
    }


    /**
     * Perform adding task for User.
     *
     * @param todo
     * @param user
     * @return ResponseTransfer
     */
    @PostMapping(path = "/todo")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseTransfer addTodoForUser(@RequestBody NewTask todo, @AuthenticationPrincipal UserDetails user)
    {
        accountService.addTodo(user.getUsername(), todo);
        return new ResponseTransfer("New todo for "+ user.getUsername() + " created!!!");
    }


    /**
     * Perform updating status of a task for User.
     *
     * @param todo
     * @param user
     * @return ResponseTransfer
     */
    @PutMapping(path = "/todo")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseTransfer updateTodoForUser(@RequestBody UpdateTask todo, @AuthenticationPrincipal UserDetails user)
    {
        accountService.updateStatusTodo(user.getUsername(), todo);
        return new ResponseTransfer("Updated task status!!!");
    }


    private List<Todo> getTaskForUser(String username, String status)
    {
        List<Todo> result = accountService.getAllTodosForUser(username, status);

        if(result.size() == 0)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No task for User!!!");

        return result;
    }
}
