package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.hw03.domain.Question;
import io.github.takzhanov.otus.spring.hw03.domain.User;
import io.github.takzhanov.otus.spring.hw03.domain.UserAnswer;
import io.github.takzhanov.otus.spring.hw03.domain.UserResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionRepository questionRepository;

    private final FormatterService formatterService;

    private final IOFacade ioFacade;

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

    private void showResult(UserResult result) {
        ioFacade.printlnMsg("msg.score",
                formatterService.format(result.getUser()),
                formatterService.format(result.getScore()));
    }
}
