package io.github.takzhanov.otus.spring.hw03.dao;

import io.github.takzhanov.otus.spring.hw03.config.AppProperties;
import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import io.github.takzhanov.otus.spring.hw03.domain.Question;
import io.github.takzhanov.otus.spring.hw03.exception.QuestionReadException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("CsvQuestionRepository")
public class CsvQuestionRepositoryTest {
    @Test
    @DisplayName("should read correct csv file successfully")
    public void findAllForCorrectFileTest() {
        var mockAppProperties = mock(AppProperties.class);
        var questionRepository = new CsvQuestionRepository(mockAppProperties);
        when(mockAppProperties.getFileName()).thenReturn("testQuestions.csv");

        var actualQuestionList = questionRepository.findAll();
        var expectedQuestionList = List.of(
                new Question("Q1", List.of(
                        new Answer("A1_1", true),
                        new Answer("A1_2", false),
                        new Answer("A1_3", false))),
                new Question("Q2", List.of(
                        new Answer("A2_1", false),
                        new Answer("A2_2", false))),
                new Question("Q3", List.of(
                        new Answer("A3_1", true),
                        new Answer("A3_2", true))),
                new Question("Q4",
                        emptyList()),
                new Question("Q5", List.of(
                        new Answer("A5_1", false),
                        new Answer("A5_2", false),
                        new Answer("A5_3", true))));

        assertIterableEquals(expectedQuestionList, actualQuestionList);
    }

    @Test
    @DisplayName("should throw specific exception when read bad file")
    public void findAllForBadFileTest() {
        var mockAppProperties = mock(AppProperties.class);
        var questionRepository = new CsvQuestionRepository(mockAppProperties);
        when(mockAppProperties.getFileName()).thenReturn("empty.csv");

        assertThrows(QuestionReadException.class, questionRepository::findAll);
    }
}
