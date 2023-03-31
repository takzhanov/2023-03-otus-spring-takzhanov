package io.github.takzhanov.otus.spring.lesson02.domain;

import java.util.HashMap;
import java.util.Map;

public class UserResult {
    private final Map<Question, UserAnswer> userAnswers = new HashMap<>();

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
