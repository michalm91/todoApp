package com.mmatuszewski.todo.mail;


public interface EmailService {

        void sendMessage(String to, String subject, String text);
}
