package io.github.takzhanov.otus.spring.lesson01.utils;

import java.io.PrintStream;
import java.util.function.Supplier;

public class SystemOutPrintStreamFactory implements Supplier<PrintStream> {
    @Override
    public PrintStream get() {
        return System.out;
    }
}
