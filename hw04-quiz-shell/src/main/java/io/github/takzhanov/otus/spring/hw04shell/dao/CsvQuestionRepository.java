package io.github.takzhanov.otus.spring.hw04shell.dao;

import io.github.takzhanov.otus.spring.hw04shell.config.RepoProperties;
import io.github.takzhanov.otus.spring.hw04shell.domain.Answer;
import io.github.takzhanov.otus.spring.hw04shell.domain.Question;
import io.github.takzhanov.otus.spring.hw04shell.exception.QuestionReadException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CsvQuestionRepository implements QuestionRepository {
    private final RepoProperties repoProperties;

    @Override
    public List<Question> findAll() {
        var lines = readLinesFromFile();
        return parseQuestions(lines);
    }

    private List<String> readLinesFromFile() {
        var fileName = repoProperties.getFileName();
        List<String> lines = new ArrayList<>();

        try (var inputStream = new ClassPathResource(fileName).getInputStream();
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new QuestionReadException("Trouble with reading %s".formatted(fileName), e);
        }

        return lines;
    }

    private List<Question> parseQuestions(List<String> csvLines) {
        try {
            return csvLines.stream()
                    .map(this::parseCsvLine)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new QuestionReadException("Incorrect question file format", ex);
        }
    }

    private Question parseCsvLine(String csvLine) {
        String[] fields = csvLine.split(",");
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

        return new Question(questionText, answers);
    }
}
