package io.github.takzhanov.otus.spring.hw01reader;

import io.github.takzhanov.otus.spring.hw01reader.service.QuizService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (var context = new ClassPathXmlApplicationContext("applicationCtx.xml")) {
            var quizService = context.getBean(QuizService.class);
            quizService.runQuiz();
        }
    }
}

