package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

@DisplayName("Тестирование CsvQuestionDao")
@Execution(ExecutionMode.SAME_THREAD)
@SpringBootTest(classes = CsvQuestionDao.class)
class CsvQuestionDaoTest {
    @MockBean
    private TestFileNameProvider fileNameProvider;

    @Autowired
    CsvQuestionDao dao;

    @Test
    @DisplayName("Check method FindAll")
    void findAll() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");
        Assertions.assertNotNull(dao.findAll());
    }

    @Test
    @DisplayName("Get QuestionReadException")
    void getQuestionReadException() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("null.csv");
        Assertions.assertThrowsExactly(QuestionReadException.class, dao::findAll);
    }
}