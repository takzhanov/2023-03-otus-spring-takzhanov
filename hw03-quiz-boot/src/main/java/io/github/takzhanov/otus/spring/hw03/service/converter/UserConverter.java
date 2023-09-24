package io.github.takzhanov.otus.spring.hw03.service.converter;

import io.github.takzhanov.otus.spring.hw03.domain.User;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter implements Printer<User> {
    private final MessageSource messageSource;

    @Override
    public String print(User user, Locale locale) {
        return messageSource.getMessage("msg.user",
                new Object[]{user.firstName(), user.lastName()},
                locale);
    }
}
