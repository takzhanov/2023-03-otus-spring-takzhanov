package io.github.takzhanov.otus.spring.lesson02.service;

import io.github.takzhanov.otus.spring.lesson02.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import io.github.takzhanov.otus.spring.lesson02.domain.User;
import io.github.takzhanov.otus.spring.lesson02.domain.UserAnswer;
import io.github.takzhanov.otus.spring.lesson02.domain.UserResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionRepository questionRepository;
    private final QuestionPrintService questionPrintService;
    private final InputService inputService;
    private final OutputService outputService;

    @Override
    public void runQuiz() {
        var questions = questionRepository.findAll();
        var questionsForQuiz = questions.subList(0, Math.min(questions.size(), 5));
        do {
            var user = askUserData();
            var userResult = askQuestions(user, questionsForQuiz);
            showResult(userResult);
        } while (askContinue());
    }

    private boolean askContinue() {
        outputService.println("Do you want to continue? (yes/no)");
        return inputService.readLine().toLowerCase().startsWith("y");
    }

    private User askUserData() {
        outputService.println("Enter your first name:");
        var firstName = inputService.readLine();
        outputService.println("Enter your last name:");
        var lastName = inputService.readLine();
        var user = new User(firstName, lastName);
        outputService.println("Welcome to the quiz, " + user + "!");
        return user;
    }

    private UserResult askQuestions(User user, List<Question> questions) {
        var result = new UserResult(user);
        for (var question : questions) {
            var userAnswer = askQuestion(question);
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
        var userAnswer = inputService.readLine();
        return new UserAnswer(userAnswer);
    }

    private void showResult(UserResult result) {
        outputService.println(result.getUser() + ", your score is: " + result.getScore());
    }
}
