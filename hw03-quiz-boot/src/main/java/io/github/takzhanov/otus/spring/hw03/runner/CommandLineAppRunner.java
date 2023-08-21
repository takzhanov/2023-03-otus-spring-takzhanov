package io.github.takzhanov.otus.spring.hw03.runner;

import io.github.takzhanov.otus.spring.hw03.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppRunner implements CommandLineRunner {
    private final QuizService quizService;

    @Override
    public void run(String... args) {
        quizService.runQuiz();
    }
}
