package io.github.takzhanov.otus.spring.hw03.config;

import jakarta.annotation.PostConstruct;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties implements RepoProperties, LocalizationProperties {
    private String fileName = "questions.csv";

    private Locale locale = Locale.US;

    @PostConstruct
    private void initLocaleContextHolder() {
        // потому что при вызове конверторов на базе принтеров конвершн сервис передает им локаль отсюда
        LocaleContextHolder.setLocale(locale);
    }
}