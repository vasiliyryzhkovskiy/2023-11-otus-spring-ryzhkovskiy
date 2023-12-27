package ru.otus.hw.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.LocaleConfig;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.Collections;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final IOService ioService;

    private final QuestionDao questionDao;

    private final MessageSource messageSource;

    private final LocaleConfig localeConfig;

    public TestServiceImpl(IOService ioService,
                           QuestionDao questionDao,
                           MessageSource messageSource,
                           LocaleConfig localeConfig) {
        this.ioService = ioService;
        this.questionDao = questionDao;
        this.messageSource = messageSource;
        this.localeConfig = localeConfig;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");

        ioService.printLine(messageSource
                .getMessage("TestService.answer.the.questions",
                        Collections.emptyList().toArray(), localeConfig.getLocale()));
        ioService.printFormattedLine("%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            testResult.applyAnswer(question, getStatusStudentAnswer(question));
        }
        return testResult;
    }

    /**
     * Задать вопрос, получить ответ
     */
    private boolean getStatusStudentAnswer(Question question) {
        ioService.printLine(question.text());
        ioService.printLine("");

        int answerId = 0;
        List<Answer> answerList = question.answers();
        for (var answer : answerList) {
            ioService.printLine(messageSource
                    .getMessage("TestService.answer",
                            new String[]{"# %s %s".formatted(answerId, answer.text())},
                            localeConfig.getLocale()));
            answerId++;
        }
        int studentAnswer = ioService
                .readIntForRangeWithPrompt(0, answerId - 1,
                        messageSource
                                .getMessage("TestService.your.answer.is",
                                        Collections.emptyList().toArray(), localeConfig.getLocale()),
                        messageSource
                                .getMessage("TestService.incorrect.answer",
                                        Collections.emptyList().toArray(), localeConfig.getLocale()));
        return answerList.get(studentAnswer).isCorrect();
    }
}