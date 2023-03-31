package io.github.takzhanov.otus.spring.lesson02.service;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InputServiceImpl implements InputService {
    private final Supplier<InputStream> inputStreamFactory;

    @Override
    public String readLine() {
        var scanner = new Scanner(inputStreamFactory.get());
        return scanner.nextLine();
    }
}
