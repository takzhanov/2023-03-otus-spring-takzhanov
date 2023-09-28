package io.github.takzhanov.otus.spring.hw04shell.runner;

import io.github.takzhanov.otus.spring.hw04shell.service.InteractiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppRunner implements CommandLineRunner {
    private final InteractiveService interactiveService;

    @Override
    public void run(String... args) {
        interactiveService.runQuizLoop();
    }
}
