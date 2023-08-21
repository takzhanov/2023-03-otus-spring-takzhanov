package io.github.takzhanov.otus.spring.hw03.service;

public interface IOFacade {
    void println(String s);

    default void println() {
        println("");
    }

    String readLine();

    void printlnMsg(String msgKey, Object... params);
}
