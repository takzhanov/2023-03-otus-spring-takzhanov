package io.github.takzhanov.otus.spring.hw04shell.shell;

import io.github.takzhanov.otus.spring.hw04shell.domain.User;
import io.github.takzhanov.otus.spring.hw04shell.service.InteractiveService;
import io.github.takzhanov.otus.spring.hw04shell.service.MessageService;
import io.github.takzhanov.otus.spring.hw04shell.service.converter.FormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class QuizCommands {
    private final InteractiveService interactiveService;

    private final FormatterService formatterService;

    private final MessageService messageService;

    private User user;

    @ShellMethodAvailability("loginAvailability")
    @ShellMethod("Login with first name and last name")
    public String login(String firstName, String lastName) {
        user = new User(firstName, lastName);
        return messageService.getMessage("msg.logged", formatterService.format(user));
    }

    private Availability loginAvailability() {
        var unavailableMsg = messageService.getMessage("msg.haveToLogoutFirst", formatterService.format(user));
        return user == null
                ? Availability.available()
                : Availability.unavailable(unavailableMsg);
    }

    @ShellMethodAvailability("startQuizAvailability")
    @ShellMethod("Logout")
    public String logout() {
        user = null;
        return messageService.getMessage("msg.bye");
    }


    @ShellMethodAvailability("startQuizAvailability")
    @ShellMethod("Start the quiz (available after login)")
    public void startQuiz() {
        interactiveService.runSingleQuiz(user);
    }

    private Availability startQuizAvailability() {
        return user != null
                ? Availability.available()
                : Availability.unavailable(messageService.getMessage("msg.haveToLoginFirst"));
    }
}
