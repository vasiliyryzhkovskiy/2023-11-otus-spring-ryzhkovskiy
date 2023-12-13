package ru.otus.hw.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;


@ExtendWith(MockitoExtension.class)
class GetQuestionReadExceptionTest {

    @Mock
    private TestFileNameProvider fileNameProvider;

    @BeforeEach
    void setUp() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("null.csv");
    }

    @Test
    @DisplayName("Get QuestionReadException")
    void findAll() {
        CsvQuestionDao dao = new CsvQuestionDao(fileNameProvider);
        Assertions.assertThrowsExactly(QuestionReadException.class, dao::findAll);
    }
}