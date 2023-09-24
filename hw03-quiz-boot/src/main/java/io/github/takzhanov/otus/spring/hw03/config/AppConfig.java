package io.github.takzhanov.otus.spring.hw03.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.Printer;
import org.springframework.format.support.FormattingConversionService;

@Configuration
public class AppConfig {

    @Bean
    public ConversionService formattingConversionService(List<Printer<?>> printers) {
        var formattingConversionService = new FormattingConversionService();
        printers.forEach(formattingConversionService::addPrinter);
        return formattingConversionService;
    }

}
