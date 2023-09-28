package io.github.takzhanov.otus.spring.hw04shell.service;

public interface IOFacade {
    void println(String s);

    default void println() {
        println("");
    }

    String readLine(String msgKey);

    void printMsg(String msgKey, Object... args);

    default void printlnMsg(String key, Object... args) {
        printMsg(key, args);
        println();
    }
}
