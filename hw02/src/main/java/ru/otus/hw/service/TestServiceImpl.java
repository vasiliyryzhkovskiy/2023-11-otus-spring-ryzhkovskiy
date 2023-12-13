package ru.otus.hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final IOService ioService;

    private final QuestionDao questionDao;

    public TestServiceImpl(@Autowired IOService ioService, @Autowired QuestionDao questionDao) {
        this.ioService = ioService;
        this.questionDao = questionDao;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = false; // Задать вопрос, получить ответ
            ioService.printLine("");
            ioService.printLine(question.text());
            ioService.printLine("");

            int answerId = 0;
            List<Answer> answerList = question.answers();
            for (var answer : answerList) {
                ioService.printLine(" Answer # " + answerId + " " + answer.text());
                answerId++;
            }
            int studentAnswer = ioService.readIntForRangeWithPrompt(0, answerId - 1, "Your answer is ?", "Incorrect studentAnswer");

            isAnswerValid = answerList.get(studentAnswer).isCorrect();
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
