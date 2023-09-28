package io.github.takzhanov.otus.spring.hw01reader.service;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OutputServiceImpl implements OutputService {
    private final Supplier<OutputStream> outputStreamFactory;

    @Override
    public void println(String s) {
        var printStream = new PrintStream(outputStreamFactory.get());
        printStream.println(s);
    }
}
