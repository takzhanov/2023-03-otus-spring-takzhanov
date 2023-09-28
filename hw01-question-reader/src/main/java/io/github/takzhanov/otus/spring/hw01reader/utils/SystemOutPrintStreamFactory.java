package io.github.takzhanov.otus.spring.hw01reader.utils;

import java.io.PrintStream;
import java.util.function.Supplier;

public class SystemOutPrintStreamFactory implements Supplier<PrintStream> {
    @Override
    public PrintStream get() {
        return System.out;
    }
}
