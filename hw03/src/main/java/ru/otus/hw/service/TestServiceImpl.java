package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    public TestServiceImpl(LocalizedIOService ioService,
                           QuestionDao questionDao) {
        this.ioService = ioService;
        this.questionDao = questionDao;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");

        ioService.printLineLocalized("TestService.answer.the.questions");
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
            ioService.printFormattedLineLocalized("TestService.answer",
                    new String[]{"# %s %s".formatted(answerId, answer.text())});
            answerId++;
        }
        int studentAnswer = ioService
                .readIntForRangeWithPromptLocalized(0, answerId - 1,
                        "TestService.your.answer.is",
                        "TestService.incorrect.answer");
        return answerList.get(studentAnswer).isCorrect();
    }
}