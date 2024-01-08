package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @Mock
    private TestFileNameProvider fileNameProvider;

    @Test
    @DisplayName("Check method FindAll")
    void findAll() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");
        CsvQuestionDao dao = new CsvQuestionDao(fileNameProvider);
        Assertions.assertNotNull(dao.findAll());
    }

    @Test
    @DisplayName("Get QuestionReadException")
    void getQuestionReadException() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("null.csv");
        CsvQuestionDao dao = new CsvQuestionDao(fileNameProvider);
        Assertions.assertThrowsExactly(QuestionReadException.class, dao::findAll);
    }
}