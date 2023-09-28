package io.github.takzhanov.otus.spring.hw03.service;

public interface MessageService {
    String getMessage(String key);

    String getMessage(String key, Object... args);
}
