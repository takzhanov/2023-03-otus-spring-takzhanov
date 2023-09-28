package io.github.takzhanov.otus.spring.hw03.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IOServiceImpl implements IOService {
    private final PrintStream printStream;

    private final Scanner scanner;

    public IOServiceImpl(@Value("#{T(System).out}") PrintStream printStream,
                         @Value("#{T(System).in}") InputStream inputStream) {

        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void print(String s) {
        printStream.print(s);
    }

    @Override
    public void println(String s) {
        printStream.println(s);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
