package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import io.github.takzhanov.otus.spring.hw03.domain.Question;
import io.github.takzhanov.otus.spring.hw03.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormatterServiceImpl implements FormatterService {
    private final MessageService messageService;

    @Override
    public String formatQuestions(List<Question> questions) {
        return questions.stream()
                .map(this::formatQuestion)
                .collect(Collectors.joining("\n\n"));
    }

    @Override
    public String formatQuestion(Question question) {
        var questionLine = messageService.getMessage("msg.question", question.text());
        var answerLines = question.answers().stream()
                .map(this::formatAnswer)
                .collect(Collectors.joining("\n"));
        return questionLine + "\n" + answerLines;
    }

    private String formatAnswer(Answer answer) {
        return messageService.getMessage("msg.answer", answer.text());
    }

    @Override
    public String formatUser(User user) {
        return messageService.getMessage("msg.user", user.firstName(), user.lastName());
    }

    @Override
    public String formatScore(long score) {
        return messageService.getMessage("msg.scoreFormat", score);
    }
}
