package io.github.takzhanov.otus.spring.hw06orm.config;

import io.github.takzhanov.otus.spring.hw06orm.exception.AbstractEntityNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.exception.ConstraintException;
import io.github.takzhanov.otus.spring.hw06orm.exception.NotImplementedException;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;

@Configuration
public class AppConfig {

    @Bean
    public ConversionService conversionService(List<Converter<?, ?>> converters) {
        var conversionService = new FormattingConversionService();
        converters.forEach(conversionService::addConverter);
        return conversionService;
    }

    @Bean
    CommandExceptionResolver shellCommandExceptionResolver() {
        return ex -> {
            var errMsg = "Error 42";
            if (ex instanceof AbstractEntityNotFoundException
                || ex instanceof ConstraintException
                || ex instanceof NotImplementedException) {

                errMsg = ex.getMessage();
            } else {
                ex.printStackTrace();
            }
            return CommandHandlingResult.of("Error: " + errMsg + "\n");
        };
    }
}
