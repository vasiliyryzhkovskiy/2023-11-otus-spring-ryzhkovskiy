package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("=========== START ==============");
        ioService.printFormattedLine("Please answer the questions below%n");

        int questionCounter = 1;
        // Получить вопросы из дао и вывести их с вариантами ответов
        for (Question question : csvQuestionDao.findAll()) {
            ioService.printLine("====== QUESTION # %s ======".formatted(questionCounter));
            ioService.printLine(question.text());

            if (null != question.answers()) {
                for (Answer answer : question.answers()) {
                    ioService.printLine(answer.text());
                }
            } else {
                ioService.printLine("Answers is empty for this question");
            }
            questionCounter++;
        }
        ioService.printLine("=========== END ==============");
    }
}
