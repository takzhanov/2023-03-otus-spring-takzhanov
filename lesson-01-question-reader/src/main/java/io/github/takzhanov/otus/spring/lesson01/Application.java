package io.github.takzhanov.otus.spring.lesson01;

import io.github.takzhanov.otus.spring.lesson01.service.QuestionPrinterService;
import io.github.takzhanov.otus.spring.lesson01.service.QuestionReaderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("applicationCtx.xml");
        var questionReader = context.getBean(QuestionReaderService.class);
        var questionPrinter = context.getBean(QuestionPrinterService.class);

        questionPrinter.printQuestions(questionReader.loadQuestions());

        context.close();
    }
}

