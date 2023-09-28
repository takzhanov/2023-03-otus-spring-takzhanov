package io.github.takzhanov.otus.spring.hw01reader.service;

public interface OutputService {
    void println(String s);

    default void println() {
        println("");
    }
}
