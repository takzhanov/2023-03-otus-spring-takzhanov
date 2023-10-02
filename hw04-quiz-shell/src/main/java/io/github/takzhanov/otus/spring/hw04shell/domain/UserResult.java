package io.github.takzhanov.otus.spring.hw04shell.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class UserResult {
    @Getter
    private final User user;

    @Getter
    private final Map<Question, UserAnswer> userAnswers;

    public UserResult(User user) {
        this.user = user;
        this.userAnswers = new HashMap<>();
    }

    public void add(Question question, UserAnswer userAnswer) {
        userAnswers.put(question, userAnswer);
    }
}
