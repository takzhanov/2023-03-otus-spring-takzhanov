package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.Question;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListQuestionConverter implements Converter<List<Question>, String> {
    private final QuestionConverter questionConverter;

    @Override
    public String convert(List<Question> questions) {
        return questions.stream()
                .map(questionConverter::convert)
                .collect(Collectors.joining("\n\n"));
    }
}
