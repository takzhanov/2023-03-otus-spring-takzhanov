package io.github.takzhanov.otus.spring.hw04shell.service;

public interface MessageService {
    String getMessage(String key);

    String getMessage(String key, Object... args);
}
