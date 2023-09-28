package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import io.github.takzhanov.otus.spring.hw03.domain.Question;
import io.github.takzhanov.otus.spring.hw03.domain.Score;
import io.github.takzhanov.otus.spring.hw03.domain.UserAnswer;
import io.github.takzhanov.otus.spring.hw03.domain.UserResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionRepository questionRepository;

    @Override
    public List<Question> prepareQuestion() {
        var questions = questionRepository.findAll();
        var questionsForQuiz = questions.subList(0, Math.min(questions.size(), 5));
        return questionsForQuiz;
    }

    @Override
    public Score processResult(UserResult userResult) {
        final long countScore = userResult.getUserAnswers().entrySet().stream()
                .filter(entry -> isAnswerCorrect(entry.getKey(), entry.getValue()))
                .count();
        return new Score(countScore);
    }

    private boolean isAnswerCorrect(Question question, UserAnswer userAnswer) {
        return question.answers().stream()
                .filter(Answer::isCorrect)
                .anyMatch(answer -> answer.text().equalsIgnoreCase(userAnswer.text()));
    }
}
