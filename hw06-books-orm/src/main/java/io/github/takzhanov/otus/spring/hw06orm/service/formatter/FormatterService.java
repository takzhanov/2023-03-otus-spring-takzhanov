package io.github.takzhanov.otus.spring.hw06orm.service.formatter;

import java.util.Collection;

public interface FormatterService {
    String format(Object o);

    String format(Collection<?> elems);
}
