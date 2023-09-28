package io.github.takzhanov.otus.spring.hw02quiz.service;

public interface IOService {
    void println(String s);

    default void println() {
        println("");
    }

    String readLine();
}
