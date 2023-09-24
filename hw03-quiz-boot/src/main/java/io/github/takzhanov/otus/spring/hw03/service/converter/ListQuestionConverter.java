package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.Question;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListQuestionConverter implements Printer<List<Question>> {
    private final Printer<Question> questionPrinter;

    @Override
    public String print(List<Question> questions, Locale locale) {
        return questions.stream()
                .map(q -> questionPrinter.print(q, locale))
                .collect(Collectors.joining("\n\n"));
    }
}
