package io.github.takzhanov.otus.spring.hw02quiz.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class UserResult {
    @Getter
    private final User user;

    private final Map<Question, UserAnswer> userAnswers;

    public UserResult(User user) {
        this.user = user;
        this.userAnswers = new HashMap<>();
    }

    public void add(Question question, UserAnswer userAnswer) {
        userAnswers.put(question, userAnswer);
    }

    public long getScore() {
        return userAnswers.entrySet().stream()
                .filter(entry -> isAnswerCorrect(entry.getKey(), entry.getValue()))
                .count();
    }

    private boolean isAnswerCorrect(Question question, UserAnswer userAnswer) {
        return question.answers().stream()
                .filter(Answer::isCorrect)
                .anyMatch(answer -> answer.text().equalsIgnoreCase(userAnswer.text()));
    }

}
