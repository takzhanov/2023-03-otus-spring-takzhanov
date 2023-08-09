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
    private final FormatterService formatterService;
    private final IOService ioService;

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
        ioService.println("Do you want to continue? (yes/no)");
        return ioService.readLine().toLowerCase().startsWith("y");
    }

    private User askUserData() {
        ioService.println("Enter your first name:");
        var firstName = ioService.readLine();
        ioService.println("Enter your last name:");
        var lastName = ioService.readLine();
        var user = new User(firstName, lastName);
        ioService.println();
        ioService.println("Welcome to the quiz, %s!".formatted(formatterService.formatUser(user)));
        ioService.println();
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
        var formattedQuestion = formatterService.formatQuestion(question);
        ioService.println(formattedQuestion);
        ioService.println();
        return readUserInput();
    }

    private UserAnswer readUserInput() {
        ioService.println("Enter your answer:");
        var userAnswer = ioService.readLine();
        ioService.println();
        return new UserAnswer(userAnswer);
    }

    private void showResult(UserResult result) {
        ioService.println("%s, your score is: %s".formatted(
                formatterService.formatUser(result.getUser()),
                formatterService.formatScore(result.getScore())));
    }
}
