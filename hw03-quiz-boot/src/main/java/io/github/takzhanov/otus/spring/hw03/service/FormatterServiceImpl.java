package io.github.takzhanov.otus.spring.hw03.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormatterServiceImpl implements FormatterService {
    private final DefaultConversionService conversionService = new DefaultConversionService();

    private final List<Converter<?, ?>> converters;

    @PostConstruct
    public void init() {
        converters.forEach(conversionService::addConverter);
    }

    @Override
    public String format(Object entity) {
        return conversionService.convert(entity, String.class);
    }
}
