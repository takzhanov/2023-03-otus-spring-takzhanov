package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerConverter implements Printer<Answer> {
    private final MessageSource messageSource;

    @Override
    public String print(Answer answer, Locale locale) {
        return messageSource.getMessage("msg.answer",
                new Object[]{answer.text()},
                locale);
    }
}
