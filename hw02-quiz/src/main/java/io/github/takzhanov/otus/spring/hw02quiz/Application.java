package io.github.takzhanov.otus.spring.hw02quiz;

import io.github.takzhanov.otus.spring.hw02quiz.service.QuizService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@Configuration
@PropertySource("application.properties")
public class Application {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(Application.class)) {
            var quizService = context.getBean(QuizService.class);
            quizService.runQuiz();
        }
    }
}