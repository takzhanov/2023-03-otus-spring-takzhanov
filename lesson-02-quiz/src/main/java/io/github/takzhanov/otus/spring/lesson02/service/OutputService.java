package io.github.takzhanov.otus.spring.lesson02.service;

public interface OutputService {
    void println(String s);

    default void println() {
        println("");
    }
}
