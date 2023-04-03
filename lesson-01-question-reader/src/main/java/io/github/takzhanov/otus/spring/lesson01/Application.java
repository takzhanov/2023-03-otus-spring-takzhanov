package io.github.takzhanov.otus.spring.lesson01;

import io.github.takzhanov.otus.spring.lesson01.service.Quiz;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (var context = new ClassPathXmlApplicationContext("applicationCtx.xml")) {
            var quiz = context.getBean(Quiz.class);
            quiz.runQuiz();
        }
    }
}

