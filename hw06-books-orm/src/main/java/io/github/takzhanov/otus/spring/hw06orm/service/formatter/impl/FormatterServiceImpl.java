package io.github.takzhanov.otus.spring.hw06orm.service.formatter.impl;

import io.github.takzhanov.otus.spring.hw06orm.service.formatter.FormatterService;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormatterServiceImpl implements FormatterService {
    private final ConversionService conversionService;

    @Override
    public String format(Object object) {
        return conversionService.convert(object, String.class);
    }

    @Override
    public String format(Collection<?> elems) {
        if (null == elems || elems.isEmpty()) {
            return "[]";
        }
        return elems.stream()
                .map(el -> conversionService.convert(el, String.class))
                .collect(Collectors.joining(",\n"));
    }
}
