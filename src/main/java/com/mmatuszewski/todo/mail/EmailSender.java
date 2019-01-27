package com.mmatuszewski.todo.mail;

import com.mmatuszewski.todo.domain.model.Todo;
import com.mmatuszewski.todo.domain.model.User;
import com.mmatuszewski.todo.domain.repository.UserRepository;
import com.mmatuszewski.todo.web.controller.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailSender {

    private static final String SUBJECT = "TODO list";
    private static final String DESCRIPTION = "Tasks todo: \n";

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;


    @Scheduled(cron = "0 0 7 * * ?")
    public void sender()
    {
        List<User> users = userRepository.findAll();
        users.forEach(user -> {

            List<Todo> activeTodos = user.getTodos().stream()
                    .filter(todo -> todo.getStatus().equals(AccountController.TODO_TAG))
                    .collect(Collectors.toList());

            if(activeTodos.isEmpty()) return;

            StringBuffer description = new StringBuffer(DESCRIPTION);

            activeTodos.forEach(todo -> {
                description.append(todo.getDescription());
                description.append("\n");
            });

            emailService.sendMessage(user.getEmail(), SUBJECT, description.toString());
        });
    }
}
