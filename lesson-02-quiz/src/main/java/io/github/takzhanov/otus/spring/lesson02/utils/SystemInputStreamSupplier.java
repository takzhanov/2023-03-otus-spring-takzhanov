package io.github.takzhanov.otus.spring.lesson02.utils;

import java.io.InputStream;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SystemInputStreamSupplier implements Supplier<InputStream> {
    @Override
    public InputStream get() {
        return System.in;
    }
}
