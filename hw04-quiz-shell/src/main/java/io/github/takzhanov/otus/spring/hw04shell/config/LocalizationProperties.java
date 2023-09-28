package io.github.takzhanov.otus.spring.hw04shell.config;

import java.util.Locale;

public interface LocalizationProperties {
    Locale getLocale();

    void setLocale(Locale locale);
}
