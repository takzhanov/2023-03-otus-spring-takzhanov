package io.github.takzhanov.otus.spring.hw03.service;

public interface IOService {
    void println(String s);

    default void println() {
        println("");
    }

    String readLine();
}
