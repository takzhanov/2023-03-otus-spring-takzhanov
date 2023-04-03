package io.github.takzhanov.otus.spring.lesson01.service;

import io.github.takzhanov.otus.spring.lesson01.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.lesson01.dao.CsvQuestionRepository;
import io.github.takzhanov.otus.spring.lesson01.domain.Answer;
import io.github.takzhanov.otus.spring.lesson01.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CsvQuestionRepositoryTest {
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setUp() {
        questionRepository = new CsvQuestionRepository("testQuestions.csv");
    }

    @Test
    public void loadQuestionsTest() {
        List<Question> questions = questionRepository.findAll();
        assertNotNull(questions, "Questions list should not be null");
        assertEquals(5, questions.size(), "There should be 5 questions in the list");

        assertEquals(new Question("Q1", List.of(
                new Answer("A1_1", true),
                new Answer("A1_2", false),
                new Answer("A1_3", false))), questions.get(0));

        assertEquals(new Question("Q2", List.of(
                new Answer("A2_1", false),
                new Answer("A2_2", false))), questions.get(1));

        assertEquals(new Question("Q3", List.of(
                new Answer("A3_1", true),
                new Answer("A3_2", true))), questions.get(2));

        assertEquals(new Question("Q4",
                Collections.emptyList()), questions.get(3));

        assertEquals(new Question("Q5", List.of(
                new Answer("A5_1", false),
                new Answer("A5_2", false),
                new Answer("A5_3", true))), questions.get(4));
    }
}
