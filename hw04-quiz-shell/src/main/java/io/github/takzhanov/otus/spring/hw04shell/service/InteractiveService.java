package io.github.takzhanov.otus.spring.hw04shell.service;

import io.github.takzhanov.otus.spring.hw04shell.domain.User;

public interface InteractiveService {
    void runQuizLoop();

    void runSingleQuiz(User user);
}
