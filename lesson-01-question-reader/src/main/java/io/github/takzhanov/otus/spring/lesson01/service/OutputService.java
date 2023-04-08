package io.github.takzhanov.otus.spring.lesson01.service;

public interface OutputService {
    void println(String s);

    default void println() {
        println("");
    }
}
