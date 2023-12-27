package ru.otus.hw.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.LocaleConfig;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.TestResult;

import java.util.Collections;

@Service
public class ResultServiceImpl implements ResultService {
    private final TestConfig testConfig;

    private final IOService ioService;

    private final MessageSource messageSource;

    private final LocaleConfig localeConfig;

    public ResultServiceImpl(TestConfig testConfig,
                             IOService ioService,
                             MessageSource messageSource,
                             LocaleConfig localeConfig) {
        this.testConfig = testConfig;
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.localeConfig = localeConfig;
    }

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");
        ioService.printLine(messageSource.getMessage("ResultService.test.results",
                Collections.emptyList().toArray(), localeConfig.getLocale()));
        ioService.printLine(messageSource.getMessage("ResultService.student",
                new String[]{testResult.getStudent().getFullName()}, localeConfig.getLocale()));
        ioService.printLine(messageSource.getMessage("ResultService.answered.questions.count",
                new Integer[]{testResult.getAnsweredQuestions().size()}, localeConfig.getLocale()));
        ioService.printLine(messageSource.getMessage("ResultService.right.answers.count",
                new Integer[]{testResult.getRightAnswersCount()}, localeConfig.getLocale()));

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLine(messageSource.getMessage("ResultService.passed.test",
                    Collections.emptyList().toArray(), localeConfig.getLocale()));
            return;
        }
        ioService.printLine(messageSource.getMessage("ResultService.fail.test",
                Collections.emptyList().toArray(), localeConfig.getLocale()));
    }
}
