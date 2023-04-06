package io.github.takzhanov.otus.spring.lesson01.service;

import io.github.takzhanov.otus.spring.lesson01.dao.CsvQuestionRepository;
import io.github.takzhanov.otus.spring.lesson01.domain.Answer;
import io.github.takzhanov.otus.spring.lesson01.domain.Question;
import io.github.takzhanov.otus.spring.lesson01.exceptions.CsvReadException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CsvQuestionRepository")
public class CsvQuestionRepositoryTest {
    @Test
    @DisplayName("should read correct csv file successfully")
    public void findAllForCorrectFileTest() {
        var questionRepository = new CsvQuestionRepository("testQuestions.csv");

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
                emptyList()), questions.get(3));

        assertEquals(new Question("Q5", List.of(
                new Answer("A5_1", false),
                new Answer("A5_2", false),
                new Answer("A5_3", true))), questions.get(4));
    }

    @Test
    @DisplayName("should throw specific exception when read bad file")
    public void findAllForBadFileTest() {
        var questionRepository = new CsvQuestionRepository("empty.csv");

        assertThrows(CsvReadException.class, questionRepository::findAll);
    }
}
