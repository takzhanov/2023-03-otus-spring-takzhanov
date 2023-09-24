package io.github.takzhanov.otus.spring.hw03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IOFacadeImpl implements IOFacade {
    private final IOService ioService;

    private final MessageService messageService;

    @Override
    public void printMsg(String msgKey, Object... args) {
        final String msg = messageService.getMessage(msgKey, args);
        ioService.print(msg);
    }

    @Override
    public void println(String s) {
        ioService.println(s);
    }

    @Override
    public String readLine(String msgKey) {
        printMsg(msgKey);
        return ioService.readLine();
    }
}
