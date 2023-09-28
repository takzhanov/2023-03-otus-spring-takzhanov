package io.github.takzhanov.otus.spring.hw03.runner;

import io.github.takzhanov.otus.spring.hw03.service.InteractiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppRunner implements CommandLineRunner {
    private final InteractiveService interactiveService;

    @Override
    public void run(String... args) {
        interactiveService.runQuiz();
    }
}
