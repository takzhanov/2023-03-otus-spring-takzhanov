package io.github.takzhanov.otus.spring.lesson02.service;

import io.github.takzhanov.otus.spring.lesson02.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import io.github.takzhanov.otus.spring.lesson02.domain.UserAnswer;
import io.github.takzhanov.otus.spring.lesson02.domain.UserResult;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DemoQuizService implements QuizService {
    private final QuestionRepository questionRepository;
    private final QuestionPrintService questionPrintService;
    private final InputService inputService;
    private final OutputService outputService;

    @Override
    public void runQuiz() {
        do {
            askUserData();
            var questions = questionRepository.findAll();
//            Collections.shuffle(questions);
            var userResult = questionLoop(questions.subList(0, Math.min(questions.size(), 5)));
            showResult(userResult);
        } while (askContinue());
    }

    private boolean askContinue() {
        outputService.println("Do you want to continue? (yes/no)");
        return inputService.readLine().toLowerCase().startsWith("y");
    }

    private void askUserData() {
        outputService.println("Enter your name:");
        String userName = inputService.readLine();
        outputService.println("Welcome to the quiz, " + userName + "!");
    }

    private UserResult questionLoop(List<Question> questions) {
        UserResult result = new UserResult();
        for (var question : questions) {
            UserAnswer userAnswer = askQuestion(question);
            result.add(question, userAnswer);
        }
        return result;
    }

    private UserAnswer askQuestion(Question question) {
        questionPrintService.printQuestion(question);
        return readUserInput();
    }

    private UserAnswer readUserInput() {
        outputService.println("Enter your answer:");
        String userAnswer = inputService.readLine();
        return new UserAnswer(userAnswer);
    }

    private void showResult(UserResult result) {
        outputService.println("Your score is: " + result.getScore());
    }
}
