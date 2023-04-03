package io.github.takzhanov.otus.spring.lesson01.dao;

import io.github.takzhanov.otus.spring.lesson01.domain.Answer;
import io.github.takzhanov.otus.spring.lesson01.domain.Question;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;

@RequiredArgsConstructor
public class CsvQuestionRepository implements QuestionRepository {
    private final String fileName;

    @Override
    public List<Question> findAll() {
        return parseQuestions(readLinesFromFile());
    }

    private List<String> readLinesFromFile() {
        List<String> lines = new ArrayList<>();

        try (var inputStream = new ClassPathResource(fileName).getInputStream();
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Trouble with reading %s".formatted(fileName), e);
        }

        return lines;
    }

    private List<Question> parseQuestions(List<String> lines) {
        List<Question> questions = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            String questionText = fields[0];
            List<Answer> answers = new ArrayList<>();

            for (int i = 1; i < fields.length; i++) {
                String answerText = fields[i];
                if (answerText.startsWith("*")) {
                    answers.add(new Answer(answerText.substring(1), true));
                } else {
                    answers.add(new Answer(answerText, false));
                }
            }

            questions.add(new Question(questionText, answers));
        }

        return questions;
    }

}
