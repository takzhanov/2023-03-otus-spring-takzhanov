package io.github.takzhanov.otus.spring.hw04shell.service.converter;

import io.github.takzhanov.otus.spring.hw04shell.domain.User;
import io.github.takzhanov.otus.spring.hw04shell.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter implements Converter<User, String> {
    private final MessageService messageService;

    @Override
    public String convert(User user) {
        return messageService.getMessage("msg.user", user.firstName(), user.lastName());
    }
}
