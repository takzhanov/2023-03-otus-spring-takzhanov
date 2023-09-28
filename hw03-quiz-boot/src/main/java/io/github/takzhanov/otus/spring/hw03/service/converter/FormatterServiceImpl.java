package io.github.takzhanov.otus.spring.hw03.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormatterServiceImpl implements FormatterService {
    private final ConversionService conversionService;

    @Override
    public String format(Object entity) {
        return conversionService.convert(entity, String.class);
    }
}
