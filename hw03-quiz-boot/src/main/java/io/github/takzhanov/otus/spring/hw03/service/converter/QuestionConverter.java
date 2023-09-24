package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import io.github.takzhanov.otus.spring.hw03.domain.Question;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionConverter implements Printer<Question> {
    private final MessageSource messageSource;

    private final Printer<Answer> answerPrinter;

    @Override
    public String print(Question question, Locale locale) {
        var questionLine = messageSource.getMessage("msg.question",
                new Object[]{question.text()},
                locale);
        var answerLines = question.answers().stream()
                .map(a -> answerPrinter.print(a, locale))
                .collect(Collectors.joining("\n"));
        return questionLine + "\n" + answerLines;
    }
}
