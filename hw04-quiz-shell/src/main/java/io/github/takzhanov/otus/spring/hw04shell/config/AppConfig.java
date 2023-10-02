package io.github.takzhanov.otus.spring.hw04shell.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;

@Configuration
public class AppConfig {

    @Bean
    public ConversionService formattingConversionService(List<Converter<?, ?>> printers) {
        var formattingConversionService = new FormattingConversionService();
        printers.forEach(formattingConversionService::addConverter);
        return formattingConversionService;
    }

}
