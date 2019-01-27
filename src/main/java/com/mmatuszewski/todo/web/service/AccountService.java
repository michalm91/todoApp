package com.mmatuszewski.todo.web.service;

import com.mmatuszewski.todo.domain.model.Todo;
import com.mmatuszewski.todo.domain.model.User;
import com.mmatuszewski.todo.domain.repository.TodoRepository;
import com.mmatuszewski.todo.domain.repository.UserRepository;
import com.mmatuszewski.todo.web.dto.NewTask;
import com.mmatuszewski.todo.web.dto.UpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;


    /**
     * Find all tasks for user by given status
     *
     * @param username, status
     * @return List<Todo>
     */
    public List<Todo> getAllTodosForUser(String username, String status)
    {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return Collections.emptyList();

        return user.get().getTodos()
                .stream()
                .filter(todo -> todo.getStatus().equals(status))
                .collect(Collectors.toList());

    }


    /**
     * Add new task for User.
     * If user not found - throw ResponseStatusException
     *
     * @param username, task
     */
    public void addTodo(String username, NewTask task)
    {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User doesn't exist!!!");

        todoRepository.save(new Todo(user.get().getId(), task.getDescription(), task.getStatus()));
    }


    /**
     * Update task status for User.
     * If user not found - throw ResponseStatusException
     * If task for User not found - throw ResponseStatusException
     * Persist updated task.
     *
     * @param username, updateTask
     */
    public void updateStatusTodo(String username, UpdateTask updateTask)
    {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot update todo!!!");;

        User prepareUser = user.get();
        List<Todo> userTodos = prepareUser.getTodos();

        Optional<Todo> todoToUpdate = userTodos.stream()
                .filter(todo -> todo.getId().equals(Long.valueOf(updateTask.getId())))
                .findAny();

        if(todoToUpdate.isEmpty()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot update todo!!!");

        Todo prepareTodo = todoToUpdate.get();
        prepareTodo.setStatus(updateTask.getStatus());

        userRepository.save(prepareUser);
    }
}
