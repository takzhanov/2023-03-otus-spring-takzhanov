package io.github.takzhanov.otus.spring.hw04shell.config;

import java.util.Locale;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties implements RepoProperties, LocalizationProperties {
    private String fileName = "questions.csv";

    private Locale locale = Locale.US;
}