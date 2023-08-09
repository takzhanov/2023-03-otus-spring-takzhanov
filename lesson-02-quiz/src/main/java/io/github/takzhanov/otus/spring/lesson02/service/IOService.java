package io.github.takzhanov.otus.spring.lesson02.service;

public interface IOService {
    void println(String s);

    default void println() {
        println("");
    }

    String readLine();
}
