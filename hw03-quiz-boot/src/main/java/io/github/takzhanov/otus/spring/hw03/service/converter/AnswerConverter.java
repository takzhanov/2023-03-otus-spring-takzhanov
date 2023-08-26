package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import io.github.takzhanov.otus.spring.hw03.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerConverter implements Converter<Answer, String> {
    private final MessageService messageService;

    @Override
    public String convert(Answer answer) {
        return messageService.getMessage("msg.answer", answer.text());
    }
}
