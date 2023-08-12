package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalizedMessageService implements MessageService {
    private final MessageSource messageSource;
    private final AppProperties appProperties;

    @Override
    public String getMessage(String key) {
        return getMessage(key, new Object[]{});
    }

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, appProperties.getLocale());
    }
}
