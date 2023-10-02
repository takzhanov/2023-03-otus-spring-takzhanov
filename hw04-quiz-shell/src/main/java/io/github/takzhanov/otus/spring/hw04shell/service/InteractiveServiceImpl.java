package io.github.takzhanov.otus.spring.hw04shell.service;

import io.github.takzhanov.otus.spring.hw04shell.domain.Question;
import io.github.takzhanov.otus.spring.hw04shell.domain.Score;
import io.github.takzhanov.otus.spring.hw04shell.domain.User;
import io.github.takzhanov.otus.spring.hw04shell.domain.UserAnswer;
import io.github.takzhanov.otus.spring.hw04shell.domain.UserResult;
import io.github.takzhanov.otus.spring.hw04shell.service.converter.FormatterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InteractiveServiceImpl implements InteractiveService {
    private final FormatterService formatterService;

    private final IOFacade ioFacade;

    private final QuizService quizService;

    @Override
    public void runQuizLoop() {
        do {
            runSingleQuiz(askUserData(), quizService.prepareQuestion());
        } while (askContinue());
    }

    @Override
    public void runSingleQuiz(User user) {
        runSingleQuiz(user, quizService.prepareQuestion());
    }

    private void runSingleQuiz(User user, List<Question> questionsForQuiz) {
        var userResult = askQuestions(user, questionsForQuiz);
        var score = quizService.processResult(userResult);
        showResult(user, score);
    }

    private boolean askContinue() {
        return ioFacade.readLine("msg.continue?").toLowerCase().startsWith("y");
    }

    private User askUserData() {
        var firstName = ioFacade.readLine("msg.firstName?");
        var lastName = ioFacade.readLine("msg.lastName?");
        var user = new User(firstName, lastName);
        ioFacade.println();
        ioFacade.printlnMsg("msg.welcome", formatterService.format(user));
        ioFacade.println();
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
        var formattedQuestion = formatterService.format(question);
        ioFacade.println(formattedQuestion);
        ioFacade.println();
        return readUserInput();
    }

    private UserAnswer readUserInput() {
        var userAnswer = ioFacade.readLine("msg.answer?");
        ioFacade.println();
        return new UserAnswer(userAnswer);
    }

    private void showResult(User user, Score score) {
        ioFacade.printlnMsg("msg.score",
                formatterService.format(user),
                formatterService.format(score));
    }
}
