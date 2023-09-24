package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.Score;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreConverter implements Printer<Score> {
    private final MessageSource messageSource;

    @Override
    public String print(Score score, Locale locale) {
        return messageSource.getMessage("msg.scoreFormat",
                new Object[]{score.value()},
                locale);
    }
}
