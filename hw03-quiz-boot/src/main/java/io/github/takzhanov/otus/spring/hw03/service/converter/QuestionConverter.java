package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import io.github.takzhanov.otus.spring.hw03.domain.Question;
import io.github.takzhanov.otus.spring.hw03.service.MessageService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionConverter implements Converter<Question, String> {
    private final MessageService messageService;

    private final Converter<Answer, String> answerStringConverter;

    @Override
    public String convert(Question question) {
        var questionLine = messageService.getMessage("msg.question", question.text());
        var answerLines = question.answers().stream()
                .map(answerStringConverter::convert)
                .collect(Collectors.joining("\n"));
        return questionLine + "\n" + answerLines;
    }
}
