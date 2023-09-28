package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.Score;
import io.github.takzhanov.otus.spring.hw03.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreConverter implements Converter<Score, String> {
    private final MessageService messageService;

    @Override
    public String convert(Score score) {
        return messageService.getMessage("msg.scoreFormat", score.value());
    }
}
