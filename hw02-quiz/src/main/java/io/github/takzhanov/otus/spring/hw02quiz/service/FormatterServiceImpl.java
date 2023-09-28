package io.github.takzhanov.otus.spring.hw02quiz.service;

import io.github.takzhanov.otus.spring.hw02quiz.domain.Answer;
import io.github.takzhanov.otus.spring.hw02quiz.domain.Question;
import io.github.takzhanov.otus.spring.hw02quiz.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormatterServiceImpl implements FormatterService {
    @Override
    public String formatQuestions(List<Question> questions) {
        return questions.stream()
                .map(this::formatQuestion)
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public String formatQuestion(Question question) {
        var questionLine = "Q: %s".formatted(question.text());
        var answerLines = question.answers().stream()
                .map(this::formatAnswer)
                .collect(Collectors.joining("\n"));
        return questionLine + "\n" + answerLines;
    }

    private String formatAnswer(Answer answer) {
        return "A: %s".formatted(answer.text());
    }

    @Override
    public String formatUser(User user) {
        return "%s %s".formatted(user.firstName(), user.lastName());
    }

    @Override
    public String formatScore(long score) {
        return "%d points".formatted(score);
    }
}

