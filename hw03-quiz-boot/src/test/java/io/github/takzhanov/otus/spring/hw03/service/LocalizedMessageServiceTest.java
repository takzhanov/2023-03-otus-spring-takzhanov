package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.config.AppProperties;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class LocalizedMessageServiceTest {
    @MockBean
    private CommandLineRunner disabledClr;

    @MockBean
    private AppProperties mockLocalizationProperties;

    @Autowired
    private LocalizedMessageService messageService;

    @Test
    void getMessage_nullLocale() {
        when(mockLocalizationProperties.getLocale()).thenReturn(null);
        assertEquals("Do you want to continue? (yes/no)", messageService.getMessage("msg.continue?"));
    }

    @Test
    void getMessage_enLocale() {
        when(mockLocalizationProperties.getLocale()).thenReturn(new Locale("en"));
        assertEquals("Do you want to continue? (yes/no)", messageService.getMessage("msg.continue?"));
    }

    @Test
    void getMessage_ruLocale() {
        when(mockLocalizationProperties.getLocale()).thenReturn(new Locale("ru"));
        assertEquals("Вы хотите продолжить? (да/нет) (y/n)", messageService.getMessage("msg.continue?"));
    }

    @Test
    void getMessage_ruRuLocale() {
        when(mockLocalizationProperties.getLocale()).thenReturn(Locale.forLanguageTag("ru-RU"));
        assertEquals("Вы хотите продолжить? (да/нет) (y/n)", messageService.getMessage("msg.continue?"));
    }

    @Test
    void getMessage_wrongLocale() {
        when(mockLocalizationProperties.getLocale()).thenReturn(new Locale("wrong"));
        assertEquals("Do you want to continue? (yes/no)", messageService.getMessage("msg.continue?"));
    }
}