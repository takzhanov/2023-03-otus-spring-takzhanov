package io.github.takzhanov.otus.spring.hw04shell.service;

import io.github.takzhanov.otus.spring.hw04shell.config.LocalizationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalizedMessageService implements MessageService {
    private final MessageSource messageSource;

    private final LocalizationProperties localizationProperties;

    @Override
    public String getMessage(String key) {
        return getMessage(key, new Object[]{});
    }

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, localizationProperties.getLocale());
    }
}
