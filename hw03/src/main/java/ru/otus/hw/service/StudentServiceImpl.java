package ru.otus.hw.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.LocaleConfig;
import ru.otus.hw.domain.Student;

import java.util.Collections;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    private final MessageSource messageSource;

    private final LocaleConfig localeConfig;

    public StudentServiceImpl(MessageSource messageSource, IOService ioService, LocaleConfig localeConfig) {
        this.messageSource = messageSource;
        this.ioService = ioService;
        this.localeConfig = localeConfig;
    }

    @Override
    public Student determineCurrentStudent() {
        var firstNameMessageLocalized = messageSource
                .getMessage("StudentService.input.first.name",
                        Collections.emptyList().toArray(), localeConfig.getLocale());
        var lastNameMessageLocalized = messageSource
                .getMessage("StudentService.input.last.name",
                        Collections.emptyList().toArray(), localeConfig.getLocale());

        var firstName = ioService.readStringWithPrompt(firstNameMessageLocalized);
        var lastName = ioService.readStringWithPrompt(lastNameMessageLocalized);
        return new Student(firstName, lastName);
    }
}
