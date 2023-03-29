package io.github.takzhanov.otus.spring.lesson01.service;

import io.github.takzhanov.otus.spring.lesson01.domain.Answer;
import io.github.takzhanov.otus.spring.lesson01.domain.Question;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class QuestionReaderService {
    private final String fileName;

    private List<String> readLinesFromFile() {
        List<String> lines = new ArrayList<>();

        try (var inputStream = new ClassPathResource(fileName).getInputStream();
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public List<Question> loadQuestions() {
        return parseQuestions(readLinesFromFile());
    }

}
