package io.github.takzhanov.otus.spring.lesson02.utils;

import java.io.OutputStream;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SystemPrintStreamSupplier implements Supplier<OutputStream> {
    @Override
    public OutputStream get() {
        return System.out;
    }
}
