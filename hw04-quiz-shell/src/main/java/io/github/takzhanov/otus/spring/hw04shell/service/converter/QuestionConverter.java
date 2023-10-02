package io.github.takzhanov.otus.spring.hw04shell.service.converter;

import io.github.takzhanov.otus.spring.hw04shell.domain.Question;
import io.github.takzhanov.otus.spring.hw04shell.service.MessageService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionConverter implements Converter<Question, String> {
    private final MessageService messageService;

    private final AnswerConverter answerConverter;

    @Override
    public String convert(Question question) {
        var questionLine = messageService.getMessage("msg.question", question.text());
        var answerLines = question.answers().stream()
                .map(answerConverter::convert)
                .collect(Collectors.joining("\n"));
        return questionLine + "\n" + answerLines;
    }
}
