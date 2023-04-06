package io.github.takzhanov.otus.spring.lesson01.service;

import java.io.OutputStream;
import java.io.PrintStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OutputServiceImpl implements OutputService {
    private final OutputStream outputStream;

    @Override
    public void println(String s) {
        var printStream = new PrintStream(outputStream);
        printStream.println(s);
    }
}
