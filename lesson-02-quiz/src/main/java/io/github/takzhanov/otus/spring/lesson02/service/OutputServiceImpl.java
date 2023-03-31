package io.github.takzhanov.otus.spring.lesson02.service;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OutputServiceImpl implements OutputService {
    private final Supplier<OutputStream> outputStreamFactory;

    @Override
    public void println(String s) {
        var printStream = new PrintStream(outputStreamFactory.get());
        printStream.println(s);
    }
}
